package uniba.di.itps.ciceroneapp.main;

import android.content.Context;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public interface InterfaceMain {
    interface MvpView{}
    interface Presenter{
        void initAttivita();
    }
}
