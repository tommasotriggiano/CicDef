package uniba.di.itps.ciceroneapp.gestioneRichieste.search;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import uniba.di.itps.ciceroneapp.GestioneAttività.myEventRequestedView.RequestedAdapter;
import uniba.di.itps.ciceroneapp.R;
import uniba.di.itps.ciceroneapp.data.DataFetch;
import uniba.di.itps.ciceroneapp.gestioneFeedback.CreateFeedback;
import uniba.di.itps.ciceroneapp.gestioneFeedback.ViewMyFeedback;
import uniba.di.itps.ciceroneapp.gestioneRichieste.search.DetailEventRequested.DetailEventRequest;
import uniba.di.itps.ciceroneapp.gestioneRichieste.search.DetailEventRequested.GuestAdapter;
import uniba.di.itps.ciceroneapp.model.Event;
import uniba.di.itps.ciceroneapp.model.Feedback;
import uniba.di.itps.ciceroneapp.model.Guest;
import uniba.di.itps.ciceroneapp.model.Request;
import uniba.di.itps.ciceroneapp.model.User;

public class GestioneRichiestePresenter implements  GestioneRichiesteInterfaccia.Presenter{

    private Context context;
    private ArrayList<Map<String,Object>> events;
    private ArrayList<Map<String,Object>> ric = new ArrayList<>();
    private RequestedAdapter ra;
    private Map<String,Object> richiesta = new HashMap<>();
    private ArrayList<Guest> guest = new ArrayList<>();
    private AdapterAttivitaRicercate adapter;

    public GestioneRichiestePresenter(Context context) {
        this.context = context;
        events = new ArrayList<>();
    }



    @Override
    public void sendEventDetail(int position, ArrayList<Map<String, Object>> events, boolean b) {
        Intent goToDetail = new Intent(context, DetailEventRequest.class);
        goToDetail.putExtra("evento", (Serializable) events.get(position));
        goToDetail.putExtra("search",b);
        context.startActivity(goToDetail);
    }

    @Override
    public void setEventDetail(Intent intent,GestioneRichiesteInterfaccia.MvpView mvpView) {
        Map<String,Object> request= (Map<String, Object>) intent.getSerializableExtra("evento");
        boolean search = intent.getBooleanExtra("search",false);
        if(search){
            FirebaseFirestore.getInstance().collection(DataFetch.RICHIESTE).document(request.get(Event.IDEVENTO).toString()+"-"+FirebaseAuth.getInstance().getCurrentUser().getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    if(documentSnapshot.exists()){
                    Request r = documentSnapshot.toObject(Request.class);
                    mvpView.setTextStato(r.getStato());}
                }
            });
        mvpView.setTextTitolo((String)request.get(Event.TITOLO));
        mvpView.setTextCategoria((String)request.get(Event.CATEGORIA));
        mvpView.setTextLuogo((String)request.get(Event.LUOGO_INCONTRO));
        mvpView.setTextLingua((String)request.get(Event.LINGUA));
        mvpView.setTextData((String)request.get(Event.DATAEVENTO));
        mvpView.setTextDescrizione((String)request.get(Event.DESCRIZIONE));
        mvpView.setTextIndirizzo((String)request.get(Event.INDIRIZZO));
        mvpView.setTextOraInizio((String)request.get(Event.ORARIO_INIZIO));
        mvpView.setTextPrezzo(String.valueOf(request.get(Event.PREZZO)),(String)request.get(Event.VALUTA));
        String[] partsEnd = request.get(Event.ORARIO_INIZIO).toString().split(":");
        String[] partStart =  request.get(Event.ORARIO_INIZIO).toString().split(":");
        int durata = Integer.valueOf(partsEnd[0]) - Integer.valueOf(partStart[0]);
        mvpView.setTextDurata(String.valueOf(durata));
        if(request.get(Event.FOTO) != null){
            mvpView.setImmagineAttività((String)request.get(Event.FOTO));
        }
        FirebaseFirestore.getInstance().collection(DataFetch.UTENTI).document((String)request.get(Event.IDCICERONE)).get().addOnSuccessListener(documentSnapshot -> {
            if(documentSnapshot.exists()){
                User user = documentSnapshot.toObject(User.class);
                mvpView.setTextNomeC(user.getNome());
                mvpView.setTextCognomeC(user.getCognome());
                    if(user.getFotoprofilo() != null){
                        mvpView.setImmagineProfilo(user.getFotoprofilo());
                        }}
        });

    }
    else {
        String idAttivita = (String)request.get(Request.ID_ATTIVITA);
        String status = (String)request.get(Request.STATO_RICHIESTA);
        mvpView.setTextStato(status);
        FirebaseFirestore.getInstance().collection(DataFetch.EVENTI).document(idAttivita).get().addOnSuccessListener(
                documentSnapshot -> {
                    Event event = documentSnapshot.toObject(Event.class);
                    mvpView.setTextTitolo(event.getTitolo());
                    mvpView.setTextCategoria(event.getCategoria());
                    mvpView.setTextLuogo(event.getLuogo());
                    mvpView.setTextLingua(event.getLingua());
                    mvpView.setTextData(event.getDateEvento());
                    mvpView.setTextDescrizione(event.getDescrizione());
                    mvpView.setTextIndirizzo(event.getIndirizzo());
                    mvpView.setTextOraInizio(event.getOrarioInizio());
                    mvpView.setTextPrezzo(String.valueOf(event.getPrezzo()),event.getValuta());
                    String[] partsEnd = event.getOrarioInizio().split(":");
                    String[] partStart =  event.getOrarioInizio().split(":");
                    int durata = Integer.valueOf(partsEnd[0]) - Integer.valueOf(partStart[0]);
                    mvpView.setTextDurata(String.valueOf(durata));
                    if(event.getFoto() != null){
                        mvpView.setImmagineAttività(event.getFoto());
                    }
                    FirebaseFirestore.getInstance().collection(DataFetch.UTENTI).document(event.getIdCicerone()).get().addOnSuccessListener(docSnapshot -> {
                        if(docSnapshot.exists()){
                            User user = docSnapshot.toObject(User.class);
                            mvpView.setTextNomeC(user.getNome());
                            mvpView.setTextCognomeC(user.getCognome());
                            if(user.getFotoprofilo() != null){
                                mvpView.setImmagineProfilo(user.getFotoprofilo());
                            }}
                    });


                }
        );


        }

    }


    @Override
    public void initRecyclerViewCerca(RecyclerView recyclerView,String city, String data, String categoria) {
        Query cities;

        //se sono stati scelti sia i filtri per la data che per la categoria esegui la query anche sugli altri due campi
        if(!(data.equals(context.getResources().getString(R.string.Date))) && !(categoria.equals(context.getResources().getString(R.string.category1)))){
            cities = FirebaseFirestore.getInstance().collection(DataFetch.EVENTI).
                    whereEqualTo(Event.LUOGO_INCONTRO,city).
                    whereEqualTo(Event.DATAEVENTO,data).
                    whereEqualTo(Event.CATEGORIA,categoria).
                    whereEqualTo(Event.STATO_EVENTO,Event.STATO_IN_CORSO);}

        //se è stato sceltro solo il campo data esegui le query solo sul campo data
        else if(!(data.equals(context.getResources().getString(R.string.Date))) && (categoria.equals(context.getResources().getString(R.string.category1)))){
            cities = FirebaseFirestore.getInstance().collection(DataFetch.EVENTI).
                    whereEqualTo(Event.LUOGO_INCONTRO,city).
                    whereEqualTo(Event.DATAEVENTO,data).
                    whereEqualTo(Event.STATO_EVENTO,Event.STATO_IN_CORSO);}

        //se è stato scelto il campo categoria esegui ale query solo sul campo categoria
        else if((data.equals(context.getResources().getString(R.string.Date))) && !(categoria.equals(context.getResources().getString(R.string.category1)))){
            cities = FirebaseFirestore.getInstance().collection(DataFetch.EVENTI).
                    whereEqualTo(Event.LUOGO_INCONTRO,city).
                    whereEqualTo(Event.CATEGORIA,categoria).
                    whereEqualTo(Event.STATO_EVENTO,Event.STATO_IN_CORSO);}
        else{
            cities = FirebaseFirestore.getInstance().collection(DataFetch.EVENTI).whereEqualTo(Event.LUOGO_INCONTRO,city).
                    whereEqualTo(Event.STATO_EVENTO,Event.STATO_IN_CORSO);}

        cities.get().addOnSuccessListener(queryDocumentSnapshots -> {
            for(DocumentChange dc : queryDocumentSnapshots.getDocumentChanges()){
                DocumentSnapshot document = dc.getDocument();
                Event event = document.toObject(Event.class);
                switch(dc.getType()){
                    case ADDED:
                        if(!(event.getIdCicerone().equals(FirebaseAuth.getInstance()
                                .getCurrentUser().getUid()))){
                            events.add(event.toMap());
                        }
                        break;
                    case MODIFIED:
                        if(!(event.getIdCicerone().equals(FirebaseAuth.getInstance().getCurrentUser()
                                .getUid()))){
                            events.set(dc.getNewIndex(),event.toMap());

                        }
                        break;

                    case REMOVED:
                        if(!(event.getIdCicerone().equals(FirebaseAuth.getInstance().getCurrentUser()
                                .getUid()))){
                            events.remove(dc.getOldIndex());
                        }
                        break;
                }
            }
            adapter = new AdapterAttivitaRicercate(context,events);
            recyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        });
        events.clear();

    }

    @Override
    public void initRecyclerViewRichieste(RecyclerView recyclerView, String stato) {

        Query requested = FirebaseFirestore.getInstance().collection(DataFetch.RICHIESTE).
                whereEqualTo(Request.ID_GLOBETROTTER, FirebaseAuth.getInstance().getCurrentUser().getUid()).
                whereEqualTo("statoAttivita",stato);
        requested.get().addOnSuccessListener(queryDocumentSnapshots -> {
            for(DocumentSnapshot dc :queryDocumentSnapshots.getDocuments()){
                richiesta =  dc.getData();
                ric.add(richiesta);
            }
            ra = new RequestedAdapter(context,ric);
            recyclerView.setAdapter(ra);
            ra.notifyDataSetChanged();
        });
        ric.clear();
    }

    @Override
    public void setAddGuest(Intent intent, GestioneRichiesteInterfaccia.MvpView mvpView) {
        Map<String,Object> event= (Map<String, Object>) intent.getSerializableExtra("evento");
        FirebaseFirestore.getInstance().collection(DataFetch.UTENTI).document(FirebaseAuth.getInstance().getCurrentUser().getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                User user = documentSnapshot.toObject(User.class);
                if(user.getFotoprofilo() != null){
                    mvpView.setImmagineProfilo(user.getFotoprofilo());}
                mvpView.setTextNomeC(user.getNome());
                mvpView.setTextCognomeC(user.getCognome());

            }
        });
        mvpView.setTextPrezzo(String.valueOf(event.get(Event.PREZZO)),(String)event.get(Event.VALUTA));
    }

    @Override
    public boolean createRequestToDatabase(Intent receive,GestioneRichiesteInterfaccia.MvpView mvpView) {
        Map<String,Object> event = (Map<String, Object>) receive.getSerializableExtra("evento");
        String status = Request.STATO_IN_ATTESA;
        Request request = new Request(String.valueOf(event.get(Event.IDCICERONE)),String.valueOf(event.get(Event.IDEVENTO)), FirebaseAuth.getInstance()
                .getCurrentUser().getUid(),status);
        if(guest.size() != 0){
            request.setOspiti(guest);
        }
        return request.addRequestToDatabase();

    }

    @Override
    public void onBindHolder(GestioneRichiesteInterfaccia.MvpView mvpView, int i,ArrayList<Map<String,Object>> events) {
        mvpView.setTextTitolo(String.valueOf(events.get(i).get((Event.TITOLO))));
        mvpView.setTextLingua(String.valueOf(events.get(i).get(Event.LINGUA)));
        mvpView.setTextCategoria(String.valueOf(events.get(i).get(Event.CATEGORIA)));
        if(events.get(i).get(Event.FOTO) != null){
            mvpView.setImmagineAttività(String.valueOf(events.get(i).get(Event.FOTO)));}
        mvpView.setTextPrezzo(String.valueOf(events.get(i).get(Event.PREZZO)),String.valueOf(events.get(i).get(Event.VALUTA)));
        mvpView.setNMaxPartecipanti(String.valueOf(events.get(i).get(Event.MAX_PARTECIPANTI)));
        }
    @Override
    public void onBindHolderR(GestioneRichiesteInterfaccia.MvpView mvpView, int i, ArrayList<Map<String, Object>> requests) {
        String idAttivita = requests.get(i).get(Request.ID_ATTIVITA).toString();
        FirebaseFirestore.getInstance().collection(DataFetch.EVENTI).document(idAttivita).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists()){
                Event event = documentSnapshot.toObject(Event.class);
                mvpView.setTextTitolo(event.getTitolo());
                mvpView.setTextData(event.getDateEvento());
                mvpView.setTextOraInizio(event.getOrarioIncontro());
                mvpView.setTextLuogo(event.getLuogo());
                mvpView.setTextIndirizzo(event.getIndirizzo());
                if(event.getFoto() != null){
                    mvpView.setImmagineAttività(event.getFoto());}
            }}
        });
        mvpView.setTextStato((String)requests.get(i).get(Request.STATO_RICHIESTA));
    }

    @Override
    public void setListViewGuest(ListView listGuest,String nome,String cognome,String email) {
        Guest g = new Guest(nome,cognome);
        if(email != null){
            g.setEmail(email);
        }
        guest.add(g);
        listGuest.setAdapter(new GuestAdapter(context,guest));

    }

    @Override
    public void goToFeedback(Intent receive,String stato) {
        Map<String,Object> request = (Map<String, Object>) receive.getSerializableExtra("evento");
        String id = (String) request.get(Request.ID_CICERONE);
        Intent passId = new Intent(context, ViewMyFeedback.class);
        passId.putExtra("id",id);
        passId.putExtra("stato",stato);
        context.startActivity(passId);
    }

    @Override
    public boolean enableButton(Intent receive) {
        HashMap<String,Object> map = (HashMap<String, Object>) receive.getSerializableExtra("evento");
        boolean search = receive.getBooleanExtra("search",false);
        if(!search){
            if(map.get("statoAttivita").equals("PASSATO") && map.get("stato").equals("CONFERMATA")){
                return true;
            }}

        return false;

    }

    @Override
    public void goToCreateFeedBack(GestioneRichiesteInterfaccia.MvpView mvpView,Intent receive) {
        Map<String,Object> request = (Map<String, Object>) receive.getSerializableExtra("evento");
        String id = (String) request.get(Request.ID_CICERONE);
        Intent passId = new Intent(context, CreateFeedback.class);
        passId.putExtra("id",id);
        ((Context)mvpView).startActivity(passId);
    }
}



