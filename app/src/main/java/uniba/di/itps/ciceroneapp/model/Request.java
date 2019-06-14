package uniba.di.itps.ciceroneapp.model;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

import uniba.di.itps.ciceroneapp.data.DataFetch;

public class Request {
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

    public boolean addRequestToDatabase(){
        FirebaseFirestore.getInstance().collection(DataFetch.RICHIESTE).document().set(this).addOnSuccessListener(aVoid -> {

        });
        return true;
    }
}
