package uniba.di.itps.ciceroneapp.GestioneAttività;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import uniba.di.itps.ciceroneapp.GestioneAttività.myEventCreatedView.RecyclerViewMyEventAdapter;
import uniba.di.itps.ciceroneapp.GestioneAttività.myEventRequestedView.RequestedAdapter;
import uniba.di.itps.ciceroneapp.data.DataFetch;
import uniba.di.itps.ciceroneapp.gestioneRichieste.search.DettaglioAttivita;
import uniba.di.itps.ciceroneapp.gestioneRichieste.search.GestioneRichiesteInterfaccia;
import uniba.di.itps.ciceroneapp.model.Event;
import uniba.di.itps.ciceroneapp.model.Request;
import uniba.di.itps.ciceroneapp.model.Stage;

/**
 * Created by tommaso on 27/05/2019.
 */

public class PresenterGestioneAttività  implements InterfaceGestioneAttività.Presenter{
    private Context mcontext;
    private FirebaseUser user;
    private ArrayList<Map<String,Object>> events = new ArrayList<>();
    private ArrayList<Map<String,Object>> ric = new ArrayList<>();
    private RecyclerViewMyEventAdapter adapter;
    private RequestedAdapter ra;
    private Map<String,Object> richiesta = new HashMap<>();

    public PresenterGestioneAttività(Context context){
        mcontext = context;
    }
    public PresenterGestioneAttività(Context context, FirebaseFirestore db, FirebaseUser user){
        mcontext = context;
        this.user = user;
        events = new ArrayList<>();
        ric = new ArrayList<>();
    }


    @Override
    public void addFragment(Fragment fragment,InterfaceGestioneAttività.MvpView mView) {
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

        int num = b.getInt("numPartecipanti");
        double prezzo = b.getDouble("price");
        String valutes = b.getString("valutes");

        ArrayList<Stage> stage;
        stage = (ArrayList<Stage>) b.getSerializable("tappe");
        //Creazione Evento
        Event event = new Event(title,description,categoria,num,data,oraIncontro,oraInizio,prezzo,valutes,stage, user.getUid());
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
        event.setId(event.getIdCicerone() +"-"+event.getDateEvento()+"-"+event.getOrarioInizio());
        event.setStato(Event.STATO_IN_CORSO);
        //aggiunge l'oggetto al database
        event.createEventToDatabase();
    }



    @Override
    public void initRecyclerViewCreate(RecyclerView recyclerView) {
        Query created = FirebaseFirestore.getInstance().collection(DataFetch.EVENTI)
                .whereEqualTo("idCicerone",user.getUid()).whereEqualTo(Event.STATO_EVENTO,Event.STATO_IN_CORSO);
        created.get().addOnSuccessListener(queryDocumentSnapshots -> {
            for(DocumentSnapshot d : queryDocumentSnapshots.getDocuments()){
                Event event = d.toObject(Event.class);
                events.add(event.toMap());
            }
            adapter = new RecyclerViewMyEventAdapter(mcontext,events);
            recyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();

        });
        events.clear();
    }

    @Override
    public void initRecyclerViewRichieste(RecyclerView recyclerView) {
        Query requested = FirebaseFirestore.getInstance().collection(DataFetch.RICHIESTE).whereEqualTo(Request.ID_GLOBETROTTER, FirebaseAuth.getInstance().getCurrentUser().getUid());
        requested.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for(DocumentSnapshot dc :queryDocumentSnapshots.getDocuments()){
                    richiesta = dc.getData();
                    ric.add(richiesta);
                }
                ra = new RequestedAdapter(mcontext,ric);
                recyclerView.setAdapter(ra);
                ra.notifyDataSetChanged();
            }
        });
        ric.clear();
    }

    @Override
    public void onBindHolder(InterfaceGestioneAttività.MvpView mvpView, int i, ArrayList<Map<String,Object>> events) {
        mvpView.setTextTitolo(events.get(i).get(Event.TITOLO).toString());
        mvpView.setTextData(events.get(i).get(Event.DATAEVENTO).toString());
        mvpView.setTextPartecipanti(String.valueOf(events.get(i).get(Event.MAX_PARTECIPANTI)));
        if(events.get(i).get(Event.FOTO) != null){
        mvpView.setImmatività(events.get(i).get(Event.FOTO).toString());}

    }

    @Override
    public void sendEventDetail(int position,ArrayList<Map<String,Object>> events) {
        Intent goToDetail = new Intent(mcontext, DettaglioAttivita.class);
        goToDetail.putExtra("evento", (Serializable) events.get(position));
        goToDetail.putExtra("create",true);
        mcontext.startActivity(goToDetail);
    }

    @Override
    public void deleteEvent(Intent receive, GestioneRichiesteInterfaccia.MvpView mvpView) {
        Event event = (Event) receive.getSerializableExtra("evento");
        if(event.delete()){
            mvpView.goToEvent();
        };
    }

    @Override
    public void onBindHolderR(InterfaceGestioneAttività.MvpView mvpView, int i, ArrayList<Map<String, Object>> requests) {
        String idAttivita = requests.get(i).get(Request.ID_ATTIVITA).toString();
        FirebaseFirestore.getInstance().collection(DataFetch.EVENTI).document(idAttivita).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Event event = documentSnapshot.toObject(Event.class);
                mvpView.setTextTitolo(event.getTitolo());
                mvpView.setTextData(event.getDateEvento());
                mvpView.setTextOrario(event.getOrarioIncontro());
                mvpView.setTextLuogo(event.getLuogo());
                mvpView.setTextIndirizzo(event.getIndirizzo());
                if(event.getFoto() != null){
                mvpView.setImmatività(event.getFoto());}
            }
        });
        mvpView.setTextStato(requests.get(i).get(Request.STATO_RICHIESTA).toString());
    }


}
