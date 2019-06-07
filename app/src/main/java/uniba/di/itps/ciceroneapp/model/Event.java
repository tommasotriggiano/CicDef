
package uniba.di.itps.ciceroneapp.model;



import java.util.Map;

public class Event {


    private String titolo;
    private String foto;
    private String descrizione;
    private String categoria;
    private int nMaxPartecipanti;
    private String dateEvento;
    private String orarioIncontro;
    private String orarioInizio;
    private String orarioFine;
    private double prezzo;
    private String valuta;
    private Itinerary itinerario;
    private String noteAggiuntive;
    private String idCicerone;
    private String lingua;
    private String linguaSecondaria;
    private Map<String,Object> tappe;
    private String requisiti;
    private String luogo;
    private String indirizzo;

    public Event() {
    } //per firebase.

    public Event(String titolo, String descrizione, String categoria, int nMaxPartecipanti, String dateEvento,
                 String orarioIncontro, String orarioInizio, double prezzo,
                 String valuta, Map<String,Object> tappe, String cicerone) {
        this.titolo = titolo;
        this.descrizione = descrizione;
        this.categoria = categoria;
        this.nMaxPartecipanti = nMaxPartecipanti;
        this.dateEvento = dateEvento;
        this.orarioIncontro = orarioIncontro;
        this.orarioInizio = orarioInizio;
        this.prezzo = prezzo;
        this.valuta = valuta;
        this.tappe= tappe;
        this.idCicerone = cicerone;
    }

    public void setLinguaSecondaria(String linguaSecondaria) {
        this.linguaSecondaria = linguaSecondaria;
    }

    public String getLinguaSecondaria() {
        return linguaSecondaria;
    }
    public String getRequisiti() {
        return requisiti;
    }

    public void setRequisiti(String requisiti) {
        this.requisiti = requisiti;
    }
    public String getTitolo() {
        return titolo;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public int getnMaxPartecipanti() {
        return nMaxPartecipanti;
    }

    public void setnMaxPartecipanti(int nMaxPartecipanti) {
        this.nMaxPartecipanti = nMaxPartecipanti;
    }

    public String getDateEvento() {
        return dateEvento;
    }

    public void setDateEvento(String dateEvento) {
        this.dateEvento = dateEvento;
    }

    public String getOrarioIncontro() {
        return orarioIncontro;
    }

    public void setOrarioIncontro(String orarioIncontro) {
        this.orarioIncontro = orarioIncontro;
    }

    public String getOrarioInizio() {
        return orarioInizio;
    }

    public void setOrarioInizio(String orarioInizio) {
        this.orarioInizio = orarioInizio;
    }

    public String getOrarioFine() {
        return orarioFine;
    }

    public void setOrarioFine(String orarioFine) {
        this.orarioFine = orarioFine;
    }

    public double getPrezzo() {
        return prezzo;
    }

    public void setPrezzo(double prezzo) {
        this.prezzo = prezzo;
    }

    public String getValuta() {
        return valuta;
    }

    public void setValuta(String valuta) {
        this.valuta = valuta;
    }

    public Itinerary getItinerario() {
        return itinerario;
    }

    public void setItinerario(Itinerary itinerario) {
        this.itinerario = itinerario;
    }

    public String getNoteAggiuntive() {
        return noteAggiuntive;
    }

    public void setNoteAggiuntive(String noteAggiuntive) {
        this.noteAggiuntive = noteAggiuntive;
    }

    public String getCicerone() {
        return idCicerone;
    }

    public void setCicerone(String cicerone) {
        this.idCicerone = cicerone;
    }


    public String getIndirizzo() {
        return indirizzo;
    }

    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
    }

    public String getLuogo() {
        return luogo;
    }


    public void setLuogo(String luogo) {
        this.luogo = luogo;
    }

    public String getLingua() {
        return lingua;
    }

    public void setLingua(String lingua) {
        this.lingua = lingua;
    }
}
