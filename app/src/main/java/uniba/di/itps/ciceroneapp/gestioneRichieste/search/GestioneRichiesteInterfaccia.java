package uniba.di.itps.ciceroneapp.gestioneRichieste.search;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

import uniba.di.itps.ciceroneapp.model.Event;

public interface GestioneRichiesteInterfaccia {
    interface MvpView{
        void enableButton(Intent receive);
        void showCategories(TextView category);
        void setTextTitolo(String string);
        void setTextCategoria(String string);
        void setTextLuogo(String string);
        void setTextDurata(String string);
        void setTextLingua(String string);
        void setTextData(String string);
        void setTextNomeC(String string);
        void setTextCognomeC(String string);
        void setTextOraInizio(String string);
        void setTextDescrizione(String string);
        void setTextIndirizzo(String string);
        void setTextPrezzo(String prezzo, String valuta);
        void setImmagineProfilo(String fotoprofilo);
        void setImmagineAttività(String img);
        void setNMaxPartecipanti(String nMaxPartecipanti);
        void goToGuests();
        void goToEvent();
    }
    interface Presenter{
        void sendEventDetail(int position,ArrayList<Event> events);
        void setEventDetail(Intent intent,GestioneRichiesteInterfaccia.MvpView mvpView);
        void initRecyclerViewCerca(RecyclerView recyclerView, String city, String data, String categoria);
        void createRequestToDatabase(Intent receive,GestioneRichiesteInterfaccia.MvpView mvpView);

        void onBindHolder(GestioneRichiesteInterfaccia.MvpView mvpView, int i, ArrayList<Event> events);

    }
}
