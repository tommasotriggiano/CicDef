package uniba.di.itps.ciceroneapp.model;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

import uniba.di.itps.ciceroneapp.data.DataFetch;

public class Request implements RequestInterface {

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
        FirebaseFirestore.getInstance().collection(DataFetch.RICHIESTE).document().set(this).addOnSuccessListener(aVoid -> {

        });
        return true;
    }
}
