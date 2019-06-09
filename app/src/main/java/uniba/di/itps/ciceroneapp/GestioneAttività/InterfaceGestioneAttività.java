package uniba.di.itps.ciceroneapp.GestioneAttività;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.TextView;
import java.util.ArrayList;

import uniba.di.itps.ciceroneapp.model.Stage;


public interface InterfaceGestioneAttività {
    interface MvpView  {
        void setFragment(Fragment fragment);
        void hideBottomNavigation();
        void showBottomNavigation();

        void showDialogDate(TextView date);

        void setFotoEvento(String string);

        void setTitolo(String string);

        void setCart_badge(String string);

        void setDate(String string);

        void setnIscritti(String string);

        void showDialogDate(TextView date,boolean birth);
    }
    interface Presenter{
        void addFragment(Fragment fragment);
        boolean setArguument(Fragment fragment, Bundle bundle);
        void addStage(ArrayList<Stage> stage,String name,String description);
        void createEvent(Bundle b);
        int getEventiRowsCount();
        void onBindEventiRowsViewAtPosition(int position,MvpView rowView);
        void showMyEventCreated();


    }
}
