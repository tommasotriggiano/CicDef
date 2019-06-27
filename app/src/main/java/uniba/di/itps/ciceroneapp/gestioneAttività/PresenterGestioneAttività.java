package uniba.di.itps.ciceroneapp.GestioneAttività;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import uniba.di.itps.ciceroneapp.GestioneAttività.detailEventCreated.AdapterRichiedenti;
import uniba.di.itps.ciceroneapp.GestioneAttività.detailEventCreated.DetailEvent;
import uniba.di.itps.ciceroneapp.GestioneAttività.myEventCreatedView.RecyclerViewMyEventAdapter;
import uniba.di.itps.ciceroneapp.GestioneAttività.myEventRequestedView.RequestedAdapter;
import uniba.di.itps.ciceroneapp.data.DataFetch;
import uniba.di.itps.ciceroneapp.model.Event;
import uniba.di.itps.ciceroneapp.model.EventInterface;
import uniba.di.itps.ciceroneapp.model.Request;
import uniba.di.itps.ciceroneapp.model.RequestInterface;
import uniba.di.itps.ciceroneapp.model.Stage;
import uniba.di.itps.ciceroneapp.model.User;

/**
 * Created by tommaso on 27/05/2019.
 */

public class PresenterGestioneAttività  implements InterfaceGestioneAttività.Presenter{
    private Context mcontext;
    private FirebaseUser user;
    private ArrayList<Map<String,Object>> events = new ArrayList<>();
    private ArrayList<Map<String,Object>> ric = new ArrayList<>();
    private ArrayList<Map<String,Object>> richiedenti = new ArrayList<>();
    private ArrayList<String> partecipanti = new ArrayList<>();
    private RecyclerViewMyEventAdapter adapter;
    private RequestedAdapter ra;
    private AdapterRichiedenti richiedentiAdapter;
    private Map<String,Object> richiesta = new HashMap<>();
    private EventInterface eventInterface = new Event();
    RequestInterface requestInterface = new Request();

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
                    richiesta =  dc.getData();
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
        Intent goToDetail = new Intent(mcontext, DetailEvent.class);
        goToDetail.putExtra("evento", (Serializable) events.get(position));
        mcontext.startActivity(goToDetail);
    }

    @Override
    public void deleteEvent(Intent receive, InterfaceGestioneAttività.MvpView mvpView) {
        Map<String,Object> requests= (Map<String, Object>) receive.getSerializableExtra("evento");
        String id = String.valueOf(requests.get(Event.IDEVENTO));
        Event event = new Event();
        if(event.delete(id)){
            mvpView.goToEvent();
        };
    }

    @Override
    public void setEventDetailC(Intent receive, InterfaceGestioneAttività.MvpView mvpView) {
        Map<String,Object> request = (Map<String, Object>) receive.getSerializableExtra("evento");
        mvpView.setTextTitolo(request.get(Event.TITOLO).toString());
        mvpView.setTextCategoria(request.get(Event.CATEGORIA).toString());
        mvpView.setTextLuogo(request.get(Event.LUOGO_INCONTRO).toString());
        mvpView.setTextLingua(request.get(Event.LINGUA).toString());
        mvpView.setTextData(request.get(Event.DATAEVENTO).toString());
        mvpView.setTextDescrizione(request.get(Event.DESCRIZIONE).toString());
        mvpView.setTextIndirizzo(request.get(Event.INDIRIZZO).toString());
        mvpView.setTextOrario(request.get(Event.ORARIO_INIZIO).toString());
        mvpView.setTextPrezzo(String.valueOf(request.get(Event.PREZZO)),request.get(Event.VALUTA).toString());
        String[] partsEnd = request.get(Event.ORARIO_INIZIO).toString().split(":");
        String[] partStart =  request.get(Event.ORARIO_INIZIO).toString().split(":");
        int durata = Integer.valueOf(partsEnd[0]) - Integer.valueOf(partStart[0]);
        mvpView.setTextDurata(String.valueOf(durata));
        if(request.get(Event.FOTO) != null){
            mvpView.setImmatività(request.get(Event.FOTO).toString());
        }
    }



    @Override
    public void goToModify(Intent receive) {
    }

    @Override
    public void gotoViewRequest(InterfaceGestioneAttività.MvpView mvpView) {
        mvpView.goToRequest();


    }

    @Override
    public void setHolderRichiedenti(AdapterRichiedenti.Holder holder, int position,ArrayList<Map<String,Object>> richiedenti) {
        String idGlobetrotter = richiedenti.get(position).get(Request.ID_GLOBETROTTER).toString();
        FirebaseFirestore.getInstance().collection(DataFetch.UTENTI).document(idGlobetrotter).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                User user = documentSnapshot.toObject(User.class);
                holder.nome.setText(user.getNome());
                holder.cognome.setText(user.getCognome());
                if(user.getFotoprofilo() != null){
                Picasso.get().load(user.getFotoprofilo()).into(holder.imageProfile);}
                holder.email.setText(user.getEmail());
                if(holder.telefono != null){
                    holder.telefono.setText(user.getTelefono());
                }
                else{
                    holder.telefono.setText("Telefono non inserito");
                };

            }
        });
            }


    @Override
    public void initRecyclerViewRichiedenti(RecyclerView richieste,Intent receive) {
        Map<String,Object> att = (Map<String, Object>) receive.getSerializableExtra("evento");
        String idAtt = att.get(Event.IDEVENTO).toString();
        Query richiedentiQuery = FirebaseFirestore.getInstance().collection(DataFetch.RICHIESTE).whereEqualTo(Request.ID_ATTIVITA,idAtt).whereEqualTo(Request.ID_CICERONE,FirebaseAuth.getInstance().getCurrentUser().getUid())
                .whereEqualTo(Request.STATO_RICHIESTA,Request.STATO_IN_ATTESA);
        richiedentiQuery.get().addOnSuccessListener(queryDocumentSnapshots -> {
            for(DocumentSnapshot dc : queryDocumentSnapshots.getDocuments()){
                Map<String,Object> richiesta =  dc.getData();
                richiedenti.add(richiesta);
            }
            AdapterRichiedenti adapterRichiedenti = new AdapterRichiedenti(mcontext,richiedenti);
            richieste.setAdapter(adapterRichiedenti);
            adapterRichiedenti.notifyDataSetChanged();
        });
    }

    @Override
    public void accettaRichiesta(int position,ArrayList<Map<String,Object>> richiesta) {

        String idPartecipante = (String) richiesta.get(position).get(Request.ID_GLOBETROTTER);
        String idAttivita = (String) richiesta.get(position).get(Request.ID_ATTIVITA);

        boolean resultP = eventInterface.addPartecipants(idAttivita,idPartecipante);

        if(requestInterface.updateStatoToDatabase(idAttivita+"-"+idPartecipante,Request.STATO_CONFERMATA) && resultP){
            Toast.makeText(mcontext,"Successo",Toast.LENGTH_SHORT).show();

        };





    }

    @Override
    public void rifiutaRichiesta(int position,ArrayList<Map<String,Object>> richiesta) {
        String rifiutato = Request.STATO_RIFIUTATA;
        String idPartecipante = (String) richiesta.get(position).get(Request.ID_GLOBETROTTER);
        String idAttivita = (String) richiesta.get(position).get(Request.ID_ATTIVITA);
        requestInterface.updateStatoToDatabase(idAttivita+""+idPartecipante,rifiutato);



    }


}



