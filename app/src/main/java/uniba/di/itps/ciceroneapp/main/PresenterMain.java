package uniba.di.itps.ciceroneapp.main;

import android.annotation.SuppressLint;
import android.content.Context;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import uniba.di.itps.ciceroneapp.model.Event;

public class PresenterMain implements InterfaceMain.Presenter {
    private Context ctx;
    private FirebaseFirestore db;

    PresenterMain(Context ctx, FirebaseFirestore db) {
        this.ctx = ctx;
        this.db = db;
    }

    @Override
    public void initAttivita() {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date currentDate = new Date();
        String date = sdf.format(currentDate);


        db.collection("Eventi").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for(DocumentSnapshot ds : queryDocumentSnapshots.getDocuments()){
                    Event event = ds.toObject(Event.class);
                    try {
                        if(sdf.parse(event.getDateEvento()).before(sdf.parse(date))){
                            event.setStato("PASSATO");
                            db.collection("Eventi").document(ds.getId()).update(event.toMap());
                        }
                       else if (sdf.parse(event.getDateEvento()).equals(sdf.parse(date))||sdf.parse(event.getDateEvento()).after(sdf.parse(date))){
                            event.setStato("IN CORSO");
                            db.collection("Eventi").document(ds.getId()).update(event.toMap());
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

    }
}
