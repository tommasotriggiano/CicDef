package uniba.di.itps.ciceroneapp.gestioneRichieste.search;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import java.util.ArrayList;
import uniba.di.itps.ciceroneapp.R;
import uniba.di.itps.ciceroneapp.data.DataFetch;
import uniba.di.itps.ciceroneapp.model.Event;
import uniba.di.itps.ciceroneapp.model.Request;
import uniba.di.itps.ciceroneapp.model.User;

public class GestioneRichiestePresenter implements  GestioneRichiesteInterfaccia.Presenter{

    private Context context;
    private ArrayList<Event> events;
    private AdapterAttivitaRicercate adapter;

    GestioneRichiestePresenter(Context context) {
        this.context = context;
        events = new ArrayList<>();
    }



    @Override
    public void sendEventDetail(int position,ArrayList<Event> events) {
        Intent goToDetail = new Intent(context, DettaglioAttivita.class);
        goToDetail.putExtra("evento", events.get(position));
        goToDetail.putExtra("create",false);
        context.startActivity(goToDetail);
    }

    @Override
    public void setEventDetail(Intent intent,GestioneRichiesteInterfaccia.MvpView mvpView) {
        Event event= (Event) intent.getSerializableExtra("evento");
        mvpView.setTextTitolo(event.getTitolo());
        mvpView.setTextCategoria(event.getCategoria());
        mvpView.setTextLuogo(event.getLuogo());
        mvpView.setTextLingua(event.getLingua());
        mvpView.setTextData(event.getDateEvento());
        mvpView.setTextDescrizione(event.getDescrizione());
        mvpView.setTextIndirizzo(event.getIndirizzo());
        mvpView.setTextOraInizio(event.getOrarioIncontro());
        mvpView.setTextPrezzo(String.valueOf(event.getPrezzo()),event.getValuta());
        String[] partsEnd = event.getOrarioFine().split(":");
        String[] partStart =  event.getOrarioInizio().split(":");
        int durata = Integer.valueOf(partsEnd[0]) - Integer.valueOf(partStart[0]);
        mvpView.setTextDurata(String.valueOf(durata));
        if(event.getFoto() != null){
            mvpView.setImmagineAttività(event.getFoto());
        }
        FirebaseFirestore.getInstance().collection(DataFetch.UTENTI).document(event.getIdCicerone()).get().addOnSuccessListener(documentSnapshot -> {
            if(documentSnapshot.exists()){
                User user = documentSnapshot.toObject(User.class);
                mvpView.setTextNomeC(user.getNome());
                mvpView.setTextCognomeC(user.getCognome());
                    if(user.getFotoprofilo() != null){
                        mvpView.setImmagineProfilo(user.getFotoprofilo());
                        }}
        });

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
                            events.add(event);
                        }
                        break;
                    case MODIFIED:
                        if(!(event.getIdCicerone().equals(FirebaseAuth.getInstance().getCurrentUser()
                                .getUid())) && event.getStato().equals(Event.STATO_IN_CORSO)){
                            events.set(dc.getNewIndex(),event);

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
    public void createRequestToDatabase(Intent receive,GestioneRichiesteInterfaccia.MvpView mvpView) {
        Event event = (Event) receive.getSerializableExtra("evento");
        String status = Event.STATO_PASSATO;
        Request request = new Request(event.getIdCicerone(),event.getId(), FirebaseAuth.getInstance()
                .getCurrentUser().getUid(),status);
        if(request.addRequestToDatabase()){
            mvpView.goToEvent();
        };

    }

    @Override
    public void onBindHolder(GestioneRichiesteInterfaccia.MvpView mvpView, int i,ArrayList<Event> events) {
        mvpView.setTextTitolo(events.get(i).getTitolo());
        mvpView.setTextLingua(events.get(i).getLingua());
        mvpView.setTextCategoria(events.get(i).getCategoria());
        if(events.get(i).getFoto() != null){
            mvpView.setImmagineAttività(events.get(i).getFoto());}
        mvpView.setTextPrezzo(String.valueOf(events.get(i).getPrezzo()),events.get(i).getValuta());
        mvpView.setNMaxPartecipanti(String.valueOf(events.get(i).getnMaxPartecipanti()));
        }


}
