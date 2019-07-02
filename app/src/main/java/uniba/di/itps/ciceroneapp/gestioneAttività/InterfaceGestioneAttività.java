package uniba.di.itps.ciceroneapp.GestioneAttività;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Map;

import uniba.di.itps.ciceroneapp.GestioneAttività.detailEventCreated.AdapterRichiedenti;
import uniba.di.itps.ciceroneapp.gestioneRichieste.search.GestioneRichiesteInterfaccia;
import uniba.di.itps.ciceroneapp.model.Stage;


public interface InterfaceGestioneAttività {
    interface MvpView  {
        void setFragment(Fragment fragment);
        void hideBottomNavigation();
        void showBottomNavigation();
        void showDialogDate(TextView date,boolean birth);
        void setTextTitolo(String titolo);
        void setTextPartecipanti(String partecipanti);
        void setTextDescrizione(String string);
        void setTextPrezzo(String prezzo, String valuta);
        void setTextDurata(String string);
        void setTextLingua(String string);
        void setTextData(String data);
        void setImmatività(String url);
        void setTextOrario(String orarioIncontro);
        void setTextCategoria(String string);
        void setTextLuogo(String luogo);
        void setTextIndirizzo(String indirizzo);
        void setTextStato(String stato);
        void goToEvent();
        void goToRequest();
        void enableButton(Intent receive);
    }
    interface Presenter{
        void addFragment(Fragment fragment,MvpView mvpView);
        boolean setArguument(Fragment fragment, Bundle bundle);
        void addStage(ArrayList<Stage> stage,String name,String description);
        void createEvent(Bundle b);
        void initRecyclerViewCreate(RecyclerView recyclerView,String stato);
        void onBindHolder(InterfaceGestioneAttività.MvpView mvpView, int i, ArrayList<Map<String,Object>> events);
        void sendEventDetail(int position,ArrayList<Map<String,Object>> events);
        void deleteEvent(Intent receive, MvpView mvpView);
        void setEventDetailC(Intent receive,MvpView mvpView);
        void goToModify(Intent receive);
        void gotoViewRequest(MvpView view);
        void setHolderRichiedenti(AdapterRichiedenti.Holder holder, int position,ArrayList<Map<String,Object>> richiedenti);
        void initRecyclerViewRichiedenti(RecyclerView richieste,Intent receive,String stato);
        void accettaRichiesta(int position,ArrayList<Map<String,Object>> richiesta);
        void rifiutaRichiesta(int position,ArrayList<Map<String,Object>> richiesta);
    }
}
