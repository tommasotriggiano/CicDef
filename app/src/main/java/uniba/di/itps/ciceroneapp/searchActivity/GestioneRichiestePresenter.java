package uniba.di.itps.ciceroneapp.searchActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;

import uniba.di.itps.ciceroneapp.R;
import uniba.di.itps.ciceroneapp.base.mvp.callback.ICallbackListener;
import uniba.di.itps.ciceroneapp.data.DataFetch;
import uniba.di.itps.ciceroneapp.model.Event;

public class GestioneRichiestePresenter implements  GestioneRichiesteInterfaccia.Presenter{

    private Context context;
    private FirebaseUser user;
    private FirebaseFirestore db;

    GestioneRichiestePresenter(Context context, FirebaseUser user, FirebaseFirestore db) {
        this.context = context;
        this.user = user;
        this.db = db;
    }

    @Override
    public void showCategories(TextView category) {
        String[]categories = context.getResources().getStringArray(R.array.Categories);
        //Crea il dialog Radio Button
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(context.getResources().getString(R.string.category));
        builder.setSingleChoiceItems(R.array.Categories, -1, (dialog, which) -> category.setText(categories[which]));
        builder.setPositiveButton("OK", (dialog, which) -> {});
        builder.setNegativeButton("ANNULLA", (dialog, which) -> {});
        builder.create();
        //mostra il dialog Radio Button
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
            cities = FirebaseFirestore.getInstance().collection(DataFetch.EVENTI).
                    whereEqualTo("luogo",city).
                    whereEqualTo("dateEvento",data);}

        //se è stato scelto il campo categoria esegui ale query solo sul campo categoria
        else if((data.equals(context.getResources().getString(R.string.Date))) && !(categoria.equals(context.getResources().getString(R.string.category1)))){
            cities = FirebaseFirestore.getInstance().collection(DataFetch.EVENTI).
                    whereEqualTo("luogo",city).
                    whereEqualTo("categoria",categoria);}
        else{
            cities = FirebaseFirestore.getInstance().collection(DataFetch.EVENTI).whereEqualTo("luogo",city);}

        cities.get().addOnSuccessListener(queryDocumentSnapshots -> {
            for(DocumentChange dc : queryDocumentSnapshots.getDocumentChanges()){
                DocumentSnapshot document = dc.getDocument();
                Event event = document.toObject(Event.class);
                switch(dc.getType()){
                    case ADDED:
                        if(!(event.getCicerone().equals(user.getUid())) && event.getStato().equals("IN CORSO")){
                            //se non c'è il filtro data
                            events.add(event);
                            listener.onCallback(events);
                            break;
                        }
                    case MODIFIED:
                        if(!(event.getCicerone().equals(user.getUid())) && event.getStato().equals("IN CORSO")){
                            events.set(dc.getNewIndex(),event);
                            listener.onCallback(events);
                            break;
                        }

                    case REMOVED:
                        if(!(event.getCicerone().equals(user.getUid())) && event.getStato().equals("IN CORSO")){
                            events.remove(dc.getOldIndex());
                            listener.onCallback(events);
                        }
                }
            }
        });
        events.clear();

    }
}
