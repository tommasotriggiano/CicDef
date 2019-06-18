package uniba.di.itps.ciceroneapp.GestioneAttività;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Map;

import uniba.di.itps.ciceroneapp.GestioneAttività.myEventCreatedView.MyEventHolder;
import uniba.di.itps.ciceroneapp.GestioneAttività.myEventRequestedView.MyEventRequestedHolder;
import uniba.di.itps.ciceroneapp.gestioneRichieste.search.GestioneRichiesteInterfaccia;
import uniba.di.itps.ciceroneapp.model.Event;
import uniba.di.itps.ciceroneapp.model.Stage;


public interface InterfaceGestioneAttività {
    interface MvpView  {
        void setFragment(Fragment fragment);
        void hideBottomNavigation();
        void showBottomNavigation();
        void showDialogDate(TextView date,boolean birth);
        void setTextTitolo(String titolo);
        void setTextPartecipanti(String partecipanti);
        void setTextData(String data);
        void setImmatività(String url);

    }
    interface Presenter{
        void addFragment(Fragment fragment,MvpView mvpView);
        boolean setArguument(Fragment fragment, Bundle bundle);
        void addStage(ArrayList<Stage> stage,String name,String description);
        void createEvent(Bundle b);
        void initRecyclerViewCreate(RecyclerView recyclerView);

        void initRecyclerViewRichieste(RecyclerView recyclerView);

        void onBindHolder(InterfaceGestioneAttività.MvpView mvpView, int i, ArrayList<Event> events);

        void sendEventDetail(int position,ArrayList<Event> events);

        void deleteEvent(Intent receive, GestioneRichiesteInterfaccia.MvpView mvpView);

        void onBindHolderR(MvpView mvpView, int i, ArrayList<Map<String, Object>> requests);
    }
}
