package uniba.di.itps.ciceroneapp.gestioneRichieste.search;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Map;

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
        void setTextStato(String stato);
        void goToGuests();
        void goToEvent();
    }
    interface Presenter{
        void sendEventDetail(int position, ArrayList<Map<String, Object>> events, boolean b);
        void setEventDetail(Intent intent,MvpView mvpView);
        void initRecyclerViewCerca(RecyclerView recyclerView, String city, String data, String categoria);
        void setAddGuest(Intent intent,MvpView mvpView);
        void createRequestToDatabase(Intent receive,MvpView mvpView);
        void onBindHolder(GestioneRichiesteInterfaccia.MvpView mvpView, int i, ArrayList<Map<String,Object>> events);
        void onBindHolderR(MvpView mvpView, int i, ArrayList<Map<String, Object>> requests);

        void setListViewGuest(ListView listGuest,String nome,String cognome,String email);
    }
}
