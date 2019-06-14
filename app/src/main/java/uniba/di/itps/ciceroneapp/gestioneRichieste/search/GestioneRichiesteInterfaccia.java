package uniba.di.itps.ciceroneapp.gestioneRichieste.search;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import uniba.di.itps.ciceroneapp.model.Event;

public interface GestioneRichiesteInterfaccia {
    interface MvpView{
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
        void setImmagineProfilo(String fotoprofilo);
        void setImmagineAttività(String img);
        void goToGuests();
        void goToEvent();
    }
    interface Presenter{
        void sendEventDetail(Event event);
        void setEventDetail(Intent intent,GestioneRichiesteInterfaccia.MvpView mvpView);
        void initRecyclerViewCerca(RecyclerView recyclerView, String city, String data, String categoria);
        void createRequestToDatabase(Intent receive);
    }
}
