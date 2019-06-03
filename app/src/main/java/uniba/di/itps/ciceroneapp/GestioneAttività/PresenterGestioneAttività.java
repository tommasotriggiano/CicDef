package uniba.di.itps.ciceroneapp.GestioneAttività;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import uniba.di.itps.ciceroneapp.base.BaseFragment;
import uniba.di.itps.ciceroneapp.login.LoginInterface;
import uniba.di.itps.ciceroneapp.model.Event;
import uniba.di.itps.ciceroneapp.model.Stage;

/**
 * Created by tommaso on 27/05/2019.
 */

public class PresenterGestioneAttività  implements InterfaceGestioneAttività.Presenter{
    private InterfaceGestioneAttività.MvpView mView;
    private Context mcontext;
    private FirebaseUser user;
    private  FirebaseFirestore db;

    PresenterGestioneAttività(Context context){
        mView= (InterfaceGestioneAttività.MvpView) context;
        mcontext = context;
    }
    PresenterGestioneAttività(Context context, FirebaseFirestore db, FirebaseUser user){
        mcontext = context;
        this.db = db;
        this.user = user;
    }


    @Override
    public void addFragment(Fragment fragment) {
        mView.setFragment(fragment);
    }

    @Override
    public boolean setArguument(Fragment fragment, Bundle bundle) {
        fragment.setArguments(bundle);
        return true;
    }

    @Override
    public void addStage(ArrayList<Stage>stage,String name, String description) {
        stage.add(new Stage(name,description));

    }

    @Override
    public void createEvent(Bundle b) {
        String url = null;
        String linguasecondaria = null;
        String requirements = null;
        if(b.getString("url") != null)
            url = b.getString("url");
        String title = b.getString("titolo");
        String description = b.getString("descrizione");
        String categoria = b.getString ("categoria");
        String luogo = b.getString("indirizzo");
        String data = b.getString("data");
        String indirizzo = b.getString("indirizzoIncontro");
        String oraIncontro = b.getString("oraIncontro");
        String oraInizio = b.getString("oraInizio");
        String oraFine = b.getString("oraFine");
        String linguaPrimaria = b.getString("linguaPrimaria");
            if(b.getString("linguaSecondaria") != null){
                linguasecondaria = b.getString("linguaSecondaria");
            }
        ArrayList<Stage> stage = (ArrayList<Stage>) b.getSerializable("tappe");
        int num = b.getInt("numPartecipanti");
        double prezzo = b.getDouble("price");
        String valutes = b.getString("valutes");

        //Creazione Evento
        Event event = new Event(title,description,categoria,num,data,oraIncontro,oraInizio,prezzo,valutes,stage,user.getUid().toString());
        if(b.getString("requirementsPartecipanti") != null){
            requirements = b.getString("requirementsPartecipanti");
            event.setRequisiti(requirements);
        }
        if(b.getString("linguaSecondaria") != null){
            linguasecondaria = b.getString("linguaSecondaria");
            event.setLinguaSecondaria(linguasecondaria);
        }
        event.setOrarioFine(oraFine);
        event.setLingua(linguaPrimaria);
        event.setLuogo("PROVA");
        event.setIndirizzo("PROVA");

        db.collection("Eventi").document().set(event).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(mcontext,"SUCCESSO PUTTANA EVA MAIALA",Toast.LENGTH_LONG).show();
            }
        });




    }


}
