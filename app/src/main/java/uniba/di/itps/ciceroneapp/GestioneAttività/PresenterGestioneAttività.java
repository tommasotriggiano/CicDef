package uniba.di.itps.ciceroneapp.GestioneAttività;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    private List<Event> events;

    PresenterGestioneAttività(Context context){
        mView= (InterfaceGestioneAttività.MvpView) context;
        mcontext = context;
    }
    PresenterGestioneAttività(Context context, FirebaseFirestore db, FirebaseUser user){
        mcontext = context;
        this.db = db;
        this.user = user;
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
        Event event = new Event(title,description,categoria,num,data,oraIncontro,oraInizio,prezzo,valutes,map, "ciao");
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
    public int getEventiRowsCount() {
        return events.size();
    }

    @Override
    public void onBindEventiRowsViewAtPosition(int position, InterfaceGestioneAttività.MvpView rowView) {
        Event event = events.get(position);
        rowView.setFotoEvento(event.getFoto());
        rowView.setDate(event.getDateEvento());
        rowView.setTitolo(event.getTitolo());
        int nDisponibili = event.getnMaxPartecipanti() - event.getPartecipanti().size();
        rowView.setnIscritti(""+event.getnMaxPartecipanti()+"/" + nDisponibili);
    }

    @Override
    public void showMyEventCreated() {

    }


}
