package uniba.di.itps.ciceroneapp.gestioneRichieste.search;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;

import uniba.di.itps.ciceroneapp.R;
import uniba.di.itps.ciceroneapp.data.DataFetch;
import uniba.di.itps.ciceroneapp.gestioneRichieste.search.DetailEventRequested.DetailEventRequest;
import uniba.di.itps.ciceroneapp.model.Event;
import uniba.di.itps.ciceroneapp.model.Request;
import uniba.di.itps.ciceroneapp.model.User;

public class GestioneRichiestePresenter implements  GestioneRichiesteInterfaccia.Presenter{

    private Context context;
    private ArrayList<Map<String,Object>> events;
    private ArrayList<Map<String,Object>> request;
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
        mvpView.setTextTitolo(request.get(Event.TITOLO).toString());
        mvpView.setTextCategoria(request.get(Event.CATEGORIA).toString());
        mvpView.setTextLuogo(request.get(Event.LUOGO_INCONTRO).toString());
        mvpView.setTextLingua(request.get(Event.LINGUA).toString());
        mvpView.setTextData(request.get(Event.DATAEVENTO).toString());
        mvpView.setTextDescrizione(request.get(Event.DESCRIZIONE).toString());
        mvpView.setTextIndirizzo(request.get(Event.INDIRIZZO).toString());
        mvpView.setTextOraInizio(request.get(Event.ORARIO_INIZIO).toString());
        mvpView.setTextPrezzo(String.valueOf(request.get(Event.PREZZO)),request.get(Event.VALUTA).toString());
        String[] partsEnd = request.get(Event.ORARIO_INIZIO).toString().split(":");
        String[] partStart =  request.get(Event.ORARIO_INIZIO).toString().split(":");
        int durata = Integer.valueOf(partsEnd[0]) - Integer.valueOf(partStart[0]);
        mvpView.setTextDurata(String.valueOf(durata));
        if(request.get(Event.FOTO) != null){
            mvpView.setImmagineAttività(request.get(Event.FOTO).toString());
        }
        FirebaseFirestore.getInstance().collection(DataFetch.UTENTI).document(request.get(Event.IDCICERONE).toString()).get().addOnSuccessListener(documentSnapshot -> {
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
        String idAttivita = request.get(Request.ID_ATTIVITA).toString();
        String status = request.get(Request.STATO_RICHIESTA).toString();
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
                    whereEqualTo(Event.CATEGORIA,categoria);}

        //se è stato sceltro solo il campo data esegui le query solo sul campo data
        else if(!(data.equals(context.getResources().getString(R.string.Date))) && (categoria.equals(context.getResources().getString(R.string.category1)))){
            cities = FirebaseFirestore.getInstance().collection(DataFetch.EVENTI).
                    whereEqualTo(Event.LUOGO_INCONTRO,city).
                    whereEqualTo(Event.DATAEVENTO,data);}

        //se è stato scelto il campo categoria esegui ale query solo sul campo categoria
        else if((data.equals(context.getResources().getString(R.string.Date))) && !(categoria.equals(context.getResources().getString(R.string.category1)))){
            cities = FirebaseFirestore.getInstance().collection(DataFetch.EVENTI).
                    whereEqualTo(Event.LUOGO_INCONTRO,city).
                    whereEqualTo(Event.CATEGORIA,categoria);}
        else{
            cities = FirebaseFirestore.getInstance().collection(DataFetch.EVENTI).whereEqualTo(Event.LUOGO_INCONTRO,city);}

        cities.get().addOnSuccessListener(queryDocumentSnapshots -> {
            for(DocumentChange dc : queryDocumentSnapshots.getDocumentChanges()){
                DocumentSnapshot document = dc.getDocument();
                Event event = document.toObject(Event.class);
                switch(dc.getType()){
                    case ADDED:
                        if(!(event.getIdCicerone().equals(FirebaseAuth.getInstance()
                                .getCurrentUser().getUid())) && event.getStato().equals(Event.STATO_IN_CORSO)){
                            events.add(event.toMap());
                        }
                        break;
                    case MODIFIED:
                        if(!(event.getIdCicerone().equals(FirebaseAuth.getInstance().getCurrentUser()
                                .getUid())) && event.getStato().equals(Event.STATO_IN_CORSO)){
                            events.set(dc.getNewIndex(),event.toMap());

                        }
                        break;

                    case REMOVED:
                        if(!(event.getIdCicerone().equals(FirebaseAuth.getInstance().getCurrentUser()
                                .getUid())) && event.getStato().equals(Event.STATO_IN_CORSO)){
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
    public void createRequestToDatabase(Intent receive,GestioneRichiesteInterfaccia.MvpView mvpView) {
        Map<String,Object> event = (Map<String, Object>) receive.getSerializableExtra("evento");
        String status = Request.STATO_IN_ATTESA;
        Request request = new Request(String.valueOf(event.get(Event.IDCICERONE)),String.valueOf(event.get(Event.IDEVENTO)), FirebaseAuth.getInstance()
                .getCurrentUser().getUid(),status);
        if(request.addRequestToDatabase()){
            mvpView.goToEvent();
        };

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
                Event event = documentSnapshot.toObject(Event.class);
                mvpView.setTextTitolo(event.getTitolo());
                mvpView.setTextData(event.getDateEvento());
                mvpView.setTextOraInizio(event.getOrarioIncontro());
                mvpView.setTextLuogo(event.getLuogo());
                mvpView.setTextIndirizzo(event.getIndirizzo());
                if(event.getFoto() != null){
                    mvpView.setImmagineAttività(event.getFoto());}
            }
        });
        mvpView.setTextStato(requests.get(i).get(Request.STATO_RICHIESTA).toString());
    }}
