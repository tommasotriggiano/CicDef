package uniba.di.itps.ciceroneapp.GestioneAttività;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import uniba.di.itps.ciceroneapp.GestioneAttività.detailEventCreated.AdapterRichiedenti;
import uniba.di.itps.ciceroneapp.GestioneAttività.detailEventCreated.DetailEvent;
import uniba.di.itps.ciceroneapp.GestioneAttività.myEventCreatedView.RecyclerViewMyEventAdapter;
import uniba.di.itps.ciceroneapp.GestioneAttività.myEventRequestedView.RequestedAdapter;
import uniba.di.itps.ciceroneapp.data.DataFetch;
import uniba.di.itps.ciceroneapp.gestioneRichieste.search.DetailEventRequested.GuestAdapter;
import uniba.di.itps.ciceroneapp.model.Event;
import uniba.di.itps.ciceroneapp.model.EventInterface;
import uniba.di.itps.ciceroneapp.model.Guest;
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
    private ArrayList<Map<String,Object>> events;
    private ArrayList<Map<String,Object>> ric;
    private ArrayList<Map<String,Object>> richiedenti = new ArrayList<>();
    private ArrayList<String> partecipanti = new ArrayList<>();
    private RecyclerViewMyEventAdapter adapter;
    private RequestedAdapter ra;
    private Map<String,Object> richiesta = new HashMap<>();
    private EventInterface eventInterface = new Event();
    private RequestInterface requestInterface = new Request();

    public PresenterGestioneAttività(Context context){
        mcontext = context;
        events = new ArrayList<>();
        ric = new ArrayList<>();
        user = FirebaseAuth.getInstance().getCurrentUser();
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
    public void initRecyclerViewCreate(RecyclerView recyclerView,String stato) {
        Query created = FirebaseFirestore.getInstance().collection(DataFetch.EVENTI)
                .whereEqualTo("idCicerone",user.getUid()).whereEqualTo(Event.STATO_EVENTO,stato);
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
    public void onBindHolder(InterfaceGestioneAttività.MvpView mvpView, int i, ArrayList<Map<String,Object>> events) {
        mvpView.setTextTitolo((String)events.get(i).get(Event.TITOLO));
        mvpView.setTextData((String)events.get(i).get(Event.DATAEVENTO));
        mvpView.setTextPartecipanti(String.valueOf(events.get(i).get(Event.MAX_PARTECIPANTI)));
        if(events.get(i).get(Event.FOTO) != null){
        mvpView.setImmatività((String)events.get(i).get(Event.FOTO));}

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
        mvpView.setTextTitolo((String)request.get(Event.TITOLO));
        mvpView.setTextCategoria((String)request.get(Event.CATEGORIA));
        mvpView.setTextLuogo((String)request.get(Event.LUOGO_INCONTRO));
        mvpView.setTextLingua((String)request.get(Event.LINGUA));
        mvpView.setTextData((String)request.get(Event.DATAEVENTO));
        mvpView.setTextDescrizione((String)request.get(Event.DESCRIZIONE));
        mvpView.setTextIndirizzo((String)request.get(Event.INDIRIZZO));
        mvpView.setTextOrario((String)request.get(Event.ORARIO_INIZIO));
        mvpView.setTextPrezzo(String.valueOf(request.get(Event.PREZZO)),(String)request.get(Event.VALUTA));
        String[] partsEnd = request.get(Event.ORARIO_INIZIO).toString().split(":");
        String[] partStart =  request.get(Event.ORARIO_INIZIO).toString().split(":");
        int durata = Integer.valueOf(partsEnd[0]) - Integer.valueOf(partStart[0]);
        mvpView.setTextDurata(String.valueOf(durata));
        if(request.get(Event.FOTO) != null){
            mvpView.setImmatività((String)request.get(Event.FOTO));
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
        String idGlobetrotter = (String)richiedenti.get(position).get(Request.ID_GLOBETROTTER);
        String stato = (String)richiedenti.get(position).get(Request.STATO_RICHIESTA);
        String statoEvento = (String)richiedenti.get(position).get("statoAttivita");
        if(richiedenti.get(position).get(Request.OSPITI) != null){
        ArrayList<Map<String,Object>> guests = (ArrayList<Map<String,Object>>) richiedenti.get(position).get(Request.OSPITI);
        ArrayList<Guest> guestArrayList = new ArrayList<>();

        for(Map<String,Object> g : guests){
           Guest gOBj = new Guest((String)g.get("nome"),(String)g.get("cognome"));
            if(g.get("email") != null) {
                gOBj.setEmail((String)g.get("email"));
            }
            guestArrayList.add(gOBj);
        }

        holder.guests.setAdapter(new GuestAdapter(mcontext,guestArrayList));}

        if(stato.equals(Request.STATO_CONFERMATA) && statoEvento.equals("IN CORSO")){
            holder.accetta.setVisibility(View.INVISIBLE);
            holder.rifiuta.setText("cancella");
        }
        else if(stato.equals(Request.STATO_CONFERMATA) && statoEvento.equals("PASSATO")){
            holder.accetta.setVisibility(View.INVISIBLE);
            holder.rifiuta.setVisibility(View.INVISIBLE);
        }
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
                }

            }
        });
            }


    @Override
    public void initRecyclerViewRichiedenti(RecyclerView richieste,Intent receive,String stato) {
        Map<String,Object> att = (Map<String, Object>) receive.getSerializableExtra("evento");
        String idAtt = att.get(Event.IDEVENTO).toString();
        Query richiedentiQuery = FirebaseFirestore.getInstance().collection(DataFetch.RICHIESTE).whereEqualTo(Request.ID_ATTIVITA,idAtt).whereEqualTo(Request.ID_CICERONE,FirebaseAuth.getInstance().getCurrentUser().getUid())
                .whereEqualTo(Request.STATO_RICHIESTA,stato);
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
        String stato = (String)richiesta.get(position).get(Request.STATO_RICHIESTA);
        if(stato.equals(Request.STATO_CONFERMATA)){
            eventInterface.deletePartecipants(idAttivita,idPartecipante);
        }
        if(requestInterface.updateStatoToDatabase(idAttivita+"-"+idPartecipante,rifiutato)){
            Toast.makeText(mcontext,"Successo",Toast.LENGTH_SHORT).show();
        }



    }


}



