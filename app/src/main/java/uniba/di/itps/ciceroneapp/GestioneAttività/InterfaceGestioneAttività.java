package uniba.di.itps.ciceroneapp.GestioneAttività;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.ImageView;
import android.widget.TextView;

import uniba.di.itps.ciceroneapp.base.BaseFragment;



public interface InterfaceGestioneAttività {
    interface MvpView{
        void setFragment(Fragment fragment);
        void hideBottomNavigation();

        void showDialogDate(TextView date);
    }
    interface Presenter{
        void addFragment(Fragment fragment);
        boolean setArguument(Fragment fragment, Bundle bundle);
        boolean getArguument(Bundle b);


    }
}
