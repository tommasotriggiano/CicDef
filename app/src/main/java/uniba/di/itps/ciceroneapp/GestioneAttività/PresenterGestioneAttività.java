package uniba.di.itps.ciceroneapp.GestioneAttività;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import uniba.di.itps.ciceroneapp.GestioneAttività.gestioneMyEvent.RecyclerViewMyEventAdapter;
import uniba.di.itps.ciceroneapp.data.DataFetch;
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
    private ArrayList<Event> events;
    private RecyclerViewMyEventAdapter adapter;

    public PresenterGestioneAttività(Context context){
        mView= (InterfaceGestioneAttività.MvpView) context;
        mcontext = context;
    }
    public PresenterGestioneAttività(Context context, FirebaseFirestore db, FirebaseUser user){
        mcontext = context;
        this.db = db;
        this.user = user;
        events = new ArrayList<>();
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
        String linguasecondaria;
        String requirements;
        String url;

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

        ArrayList<Stage> stage = (ArrayList<Stage>) b.getSerializable("tappe");
        int num = b.getInt("numPartecipanti");
        double prezzo = b.getDouble("price");
        String valutes = b.getString("valutes");
        //String nomeTappa = stage.get(0).getIndirizzo();
        //String descrizioneTappa = stage.get(0).getDescrizione();
        Map<String, Object> map = new HashMap<>();
        map.put("io","tu");
        //Creazione Evento
        Event event = new Event(title,description,categoria,num,data,oraIncontro,oraInizio,prezzo,valutes,map, user.getUid());
        if(b.getString("requirementsPartecipanti") != null){
            requirements = b.getString("requirementsPartecipanti");
            event.setRequisiti(requirements);
        }
        if(b.getString("linguaSecondaria") != null){
            linguasecondaria = b.getString("linguaSecondaria");
            event.setLinguaSecondaria(linguasecondaria);

        }
        if(b.getString("url") != null){
            url = b.getString("url");
            event.setFoto(url);}
        event.setOrarioFine(oraFine);
        event.setLingua(linguaPrimaria);
        event.setLuogo(luogo);
        event.setIndirizzo(indirizzo);

        //aggiunge l'oggetto al database
        event.createEventToDatabase();
    }



    @Override
    public void initRecyclerViewCreate(RecyclerView recyclerView) {
        Query created = FirebaseFirestore.getInstance().collection(DataFetch.EVENTI).whereEqualTo("idCicerone",user.getUid());
        created.get().addOnSuccessListener(queryDocumentSnapshots -> {
            for(DocumentSnapshot d : queryDocumentSnapshots.getDocuments()){
                Event event = d.toObject(Event.class);
                if(event.getStato().equals("IN CORSO")){
                events.add(event);}
            }
            adapter = new RecyclerViewMyEventAdapter(mcontext,events);
            recyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();

        });
        events.clear();
    }

    @Override
    public void initRecyclerViewRichieste(RecyclerView recyclerView) {
        Query requested;
    }


}
