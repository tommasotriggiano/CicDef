package uniba.di.itps.ciceroneapp.model;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import uniba.di.itps.ciceroneapp.data.DataFetch;

public class Request implements RequestInterface, Serializable {

    public final static String ID_CICERONE = "idCicerone";
    public final static String ID_ATTIVITA = "idAttivita";
    public final static String ID_GLOBETROTTER = "idGlobetrotter";
    public final static String STATO_RICHIESTA = "stato";
    public final static String OSPITI = "ospiti";
    public final static String STATO_RIFIUTATA = "RIFIUTATA";
    public final static String STATO_IN_ATTESA = "IN ATTESA";
    public final static String STATO_CONFERMATA = "CONFERMATA";


    String idCicerone;
    String idAttivita;
    String idGlobetrotter;
    String stato;
    List<Guest> ospiti;


    public Request(String idCicerone, String idAttivita, String idGlobetrotter, String stato) {
        this.idCicerone = idCicerone;
        this.idAttivita = idAttivita;
        this.idGlobetrotter = idGlobetrotter;
        this.stato = stato;
    }
   public Request(){}

    public String getIdCicerone() {
        return idCicerone;
    }

    public void setIdCicerone(String idCicerone) {
        this.idCicerone = idCicerone;
    }

    public String getIdAttivita() {
        return idAttivita;
    }

    public void setIdAttivita(String idAttivita) {
        this.idAttivita = idAttivita;
    }

    public String getIdGlobetrotter() {
        return idGlobetrotter;
    }

    public void setIdGlobetrotter(String idGlobetrotter) {
        this.idGlobetrotter = idGlobetrotter;
    }

    public String getStato() {
        return stato;
    }

    public void setStato(String stato) {
        this.stato = stato;
    }

    public List<Guest> getOspiti() {
        return ospiti;
    }

    public void setOspiti(List<Guest> ospiti) {
        this.ospiti = ospiti;
    }

    @Override
    public boolean addRequestToDatabase(){
        FirebaseFirestore.getInstance().collection(DataFetch.RICHIESTE).document(this.getIdAttivita()+"-"+this.getIdGlobetrotter()).set(this).addOnSuccessListener(aVoid -> {

        });
        return true;
    }

    @Override
    public boolean updateStatoToDatabase(String id,String stato) {
        FirebaseFirestore.getInstance().collection(DataFetch.RICHIESTE).document(id).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Request r = documentSnapshot.toObject(Request.class);
                r.setStato(stato);
                FirebaseFirestore.getInstance().collection(DataFetch.RICHIESTE).document(id).update(r.toMap());
            }
        });
        return true;
    }



    @Override
    public Map<String,Object> toMap() {
        Map<String,Object> request = new HashMap<>();
        request.put("idAttivita",this.idAttivita);
        request.put("idCicerone",this.idCicerone);
        request.put("idGlobetrotter",this.idGlobetrotter);
        request.put("stato",this.stato);
        request.put("ospiti",this.ospiti);
        return request;

    }

    @Override
    public void addStatoEvent(String id, String stato) {
        FirebaseFirestore.getInstance().collection(DataFetch.RICHIESTE).whereEqualTo(Request.ID_ATTIVITA,id).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for(DocumentSnapshot ds : queryDocumentSnapshots.getDocuments()){
                    if(ds.exists()){
                        Map<String,Object> request = ds.getData();
                        request.put("statoAttivita",stato);
                        FirebaseFirestore.getInstance().collection(DataFetch.RICHIESTE).document(ds.getId()).update(request);
                    }
                }
            }
        });
    }
}
