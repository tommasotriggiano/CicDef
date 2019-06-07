package uniba.di.itps.ciceroneapp.manageProfile;

import android.content.Context;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.widget.TextView;

import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import uniba.di.itps.ciceroneapp.base.mvp.callback.IFirebaseCallbackListener;
import uniba.di.itps.ciceroneapp.model.User;

public interface InterfacciaGestioneProfilo {
    interface MvpView{
         void loadProfile();
         void loadPhoto(Context context, CircleImageView profile, InterfacciaGestioneProfilo.Presenter presenter);
         void showBirthDate();


    }
    interface Presenter{
        void addFragment(Fragment nextFragment);
        void readDataProfile(IFirebaseCallbackListener listener);
        void salvaCambiamenti(String foto, String nome, String cognome, String data, String sesso);
    }



}
