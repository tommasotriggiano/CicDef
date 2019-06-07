package uniba.di.itps.ciceroneapp.manageProfile;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Map;

import uniba.di.itps.ciceroneapp.GestioneAttività.InterfaceGestioneAttività;
import uniba.di.itps.ciceroneapp.R;
import uniba.di.itps.ciceroneapp.base.mvp.callback.IFirebaseCallbackListener;
import uniba.di.itps.ciceroneapp.model.User;

public class GestioneProfiloPresenter implements InterfacciaGestioneProfilo.Presenter {
    private InterfacciaGestioneProfilo.MvpView mView;
    private InterfaceGestioneAttività.MvpView navigationView;
    private Context mcontext;
    private FirebaseUser user;
    private  FirebaseFirestore db;

    public GestioneProfiloPresenter(Context mcontext, FirebaseUser user, FirebaseFirestore db) {
        mView=  new ViewProfileFragment();
        navigationView = (InterfaceGestioneAttività.MvpView)mcontext;

        this.mcontext = mcontext;
        this.user = user;
        this.db = db;
    }


    @Override
    public void addFragment(Fragment nextFragment) {
        navigationView.setFragment(nextFragment);

    }

    @Override
    public void readDataProfile(IFirebaseCallbackListener listener) {
        db.collection("utenti").document(user.getUid()).get().addOnSuccessListener(documentSnapshot -> {
        if(documentSnapshot.exists()){
            User user = documentSnapshot.toObject(User.class);
            listener.onCallback(user);
        }
        else{
            Toast.makeText(mcontext,"Errore",Toast.LENGTH_LONG).show();
        }

        });
    }

    @Override
    public void salvaCambiamenti(String foto, String nome, String cognome, String data, String sesso) {
        User userUpdate = new User();
        userUpdate.setUid(user.getUid());
        userUpdate.setEmail(user.getEmail());
        userUpdate.setNome(nome);
        userUpdate.setCognome(cognome);
        userUpdate.setDatanascita(data);
        userUpdate.setSesso(sesso);
        if(foto != null){
            userUpdate.setFotoprofilo(foto);
        }

        db.collection("utenti").document(user.getUid()).update(userUpdate.toMap()).
                addOnSuccessListener(aVoid -> {
                    Toast.makeText(mcontext,mcontext.getResources().getString(R.string.saved),Toast.LENGTH_LONG).show();

        });
    }


}
