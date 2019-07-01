package uniba.di.itps.ciceroneapp.model;

import com.google.firebase.firestore.FirebaseFirestore;

import uniba.di.itps.ciceroneapp.data.DataFetch;

public class Feedback implements FeedbackInterface {


    public final static String COMMENTO = "commento";
    public final static String TITOLO = "titolo";
    public final static String RATING = "rating";
    public final static String ID_UTENTE = "utente";

    private String commento;
    private String titolo;
    private int rating;
    private String id;

    public Feedback(String commento, String titole, int rating, String id) {
        this.commento = commento;
        this.titolo = titole;
        this.rating = rating;
        this.id = id;
    }

    public String getCommento() {
        return commento;
    }

    public void setCommento(String commento) {
        this.commento = commento;
    }

    public String getTitolo() {
        return titolo;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getUtente() {
        return id;
    }

    public void setUtente(String utente) {
        this.id = utente;
    }

    @Override
    public boolean createFeedbackToDatabase(String id) {
        FirebaseFirestore.getInstance().collection(DataFetch.UTENTI).document(id).collection(DataFetch.FEEDBACK).document(id).set(this);
        return true;

    }
}
