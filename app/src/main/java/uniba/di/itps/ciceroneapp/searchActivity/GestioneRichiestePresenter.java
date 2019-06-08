package uniba.di.itps.ciceroneapp.searchActivity;

import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.Context;
import android.content.DialogInterface;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Map;

import uniba.di.itps.ciceroneapp.R;
import uniba.di.itps.ciceroneapp.base.mvp.callback.ICallbackListener;
import uniba.di.itps.ciceroneapp.model.Event;

public class GestioneRichiestePresenter implements  GestioneRichiesteInterfaccia.Presenter{

    private Context context;
    private FirebaseUser user;
    private FirebaseFirestore db;
    private GestioneRichiesteInterfaccia.MvpView mvpView;

    GestioneRichiestePresenter(Context context, FirebaseUser user, FirebaseFirestore db) {
        mvpView = new SearchActivityFragment();
        this.context = context;
        this.user = user;
        this.db = db;
    }

    @Override
    public void showCategories(TextView category) {
        String[]categories = context.getResources().getStringArray(R.array.Categories);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(context.getResources().getString(R.string.category));
        builder.setSingleChoiceItems(R.array.Categories, -1, (dialog, which) -> {
            category.setText(categories[which]);
        });

        builder.setPositiveButton("OK", (dialog, which) -> {

        });
        builder.setNegativeButton("ANNULLA", (dialog, which) -> {

        });
        builder.create();
        builder.show();
    }

    @Override
    public void respondToQuery(ArrayList<Event> events, String city,String data,String categoria, ICallbackListener listener) {
        Query cities;

        //se sono stati scelti sia i filtri per la data che per la categoria esegui la query anche sugli altri due campi
        if(!(data.equals(context.getResources().getString(R.string.Date))) && !(categoria.equals(context.getResources().getString(R.string.category1)))){
            cities = db.collection("Eventi").
                    whereEqualTo("luogo",city).
                    whereEqualTo("dateEvento",data).
                    whereEqualTo("categoria",categoria);}
        //se è stato sceltro solo il campo data esegui le query solo sul campo data
        else if(!(data.equals(context.getResources().getString(R.string.Date))) && (categoria.equals(context.getResources().getString(R.string.category1)))){
            cities = db.collection("Eventi").
                    whereEqualTo("luogo",city).
                    whereEqualTo("dateEvento",data);}

        //se è stato scelto il campo categoria esegui ale query solo sul campo categoria
        else if((data.equals(context.getResources().getString(R.string.Date))) && !(categoria.equals(context.getResources().getString(R.string.category1)))){
            cities = db.collection("Eventi").
                    whereEqualTo("luogo",city).
                    whereEqualTo("categoria",categoria);}
        else{
            cities = db.collection("Eventi").whereEqualTo("luogo",city);}


        cities.get().addOnSuccessListener(queryDocumentSnapshots -> {
            for(DocumentChange dc : queryDocumentSnapshots.getDocumentChanges()){
                DocumentSnapshot document = dc.getDocument();
                Event event = document.toObject(Event.class);
                switch(dc.getType()){
                    case ADDED:
                        if(!(event.getCicerone().equals(user.getUid()))){
                        events.add(event);
                        listener.onCallback(events);
                        break;
                        }
                    case MODIFIED:
                        if(!(event.getCicerone().equals(user.getUid()))){
                        events.set(dc.getNewIndex(),event);
                        listener.onCallback(events);
                        break;}
                    case REMOVED:
                        if(!(event.getCicerone().equals(user.getUid()))){
                            //Bisogna fare qualcosa
                        events.clear();
                        break;}


                }
            }
        });
        events.clear();

    }

    @Override
    public TextWatcher textWatcher() {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mvpView.loadQuery();

            }
        };
    }
}
