package uniba.di.itps.ciceroneapp.model;

public class Feedback implements FeedbackInterface {


    public final static String COMMENTO = "commento";
    public final static String TITOLO = "titolo";
    public final static String RATING = "rating";
    public final static String ID_UTENTE = "utente";

    private String commento;
    private String titolo;
    private int rating;
    private String utente;

    public Feedback(String commento, String titole, int rating, String utente) {
        this.commento = commento;
        this.titolo = titole;
        this.rating = rating;
        this.utente = utente;
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
        return utente;
    }

    public void setUtente(String utente) {
        this.utente = utente;
    }
}
