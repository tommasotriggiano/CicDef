package uniba.di.itps.ciceroneapp.manageProfile;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import uniba.di.itps.ciceroneapp.GestioneAttività.InterfaceGestioneAttività;
import uniba.di.itps.ciceroneapp.R;
import uniba.di.itps.ciceroneapp.base.mvp.callback.IFirebaseCallbackListener;
import uniba.di.itps.ciceroneapp.data.DataFetch;
import uniba.di.itps.ciceroneapp.gestioneFeedback.ViewMyFeedback;
import uniba.di.itps.ciceroneapp.model.User;

public class GestioneProfiloPresenter implements InterfacciaGestioneProfilo.Presenter {
    private InterfaceGestioneAttività.MvpView navigationView;
    private Context mcontext;
    private FirebaseUser user;
    private  FirebaseFirestore db;

    GestioneProfiloPresenter(Context mcontext, FirebaseUser user, FirebaseFirestore db) {
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
        db.collection(DataFetch.UTENTI).document(user.getUid()).get().addOnSuccessListener(documentSnapshot -> {
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
       if(userUpdate.updateUsertoDatabase()){
           Toast.makeText(mcontext,mcontext.getResources().getString(R.string.saved),Toast.LENGTH_LONG).show();

       }


    }

    @Override
    public void goToStorico(Fragment fragment, String stato) {
        Bundle b = new Bundle();
        b.putString("stato",stato);
        fragment.setArguments(b);
        navigationView.setFragment(fragment);

    }

    @Override
    public void goToMyFeedback() {
        Intent passId = new Intent(mcontext, ViewMyFeedback.class);
        passId.putExtra("id", FirebaseAuth.getInstance().getCurrentUser().getUid());
        mcontext.startActivity(passId);
    }


}
