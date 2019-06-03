package uniba.di.itps.ciceroneapp.GestioneAttività;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import uniba.di.itps.ciceroneapp.base.BaseFragment;
import uniba.di.itps.ciceroneapp.model.Stage;


public interface InterfaceGestioneAttività {
    interface MvpView{
        void setFragment(Fragment fragment);
        void hideBottomNavigation();

        void showDialogDate(TextView date);
    }
    interface Presenter{
        void addFragment(Fragment fragment);
        boolean setArguument(Fragment fragment, Bundle bundle);
        void addStage(ArrayList<Stage> stage,String name,String description);
        void createEvent(Bundle b);


    }
}
