
package uniba.di.itps.ciceroneapp.model;



import android.annotation.SuppressLint;
import android.util.Log;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.protobuf.Enum;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import uniba.di.itps.ciceroneapp.data.DataFetch;

public class Event implements Serializable, EventInterface {
    private static final String TAG ="Event";
    private String id;
    private String titolo;
    private String foto;
    private String descrizione;
    private String categoria;
    private int nMaxPartecipanti;
    private List<String> partecipanti;
    private String dateEvento;
    private String orarioIncontro;
    private String orarioInizio;
    private String orarioFine;
    private double prezzo;
    private String valuta;
    private String noteAggiuntive;
    private String idCicerone;
    private String lingua;
    private String linguaSecondaria;
    private List<Stage> itinerario;
    private String requisiti;
    private String luogo;
    private String indirizzo;
    private String stato;

    public final static String IDEVENTO = "id";
    public final static String TITOLO = "titolo";
    public final static String FOTO = "foto";
    public final static String DESCRIZIONE = "descrizione";
    public final static String CATEGORIA = "categoria";
    public final static String MAX_PARTECIPANTI = "nMaxPartecipanti";
    public final static String PARECIPANTI = "partecipanti";
    public final static String DATAEVENTO = "dateEvento";
    public final static String ORARIO_INCONTRO = "orarioIncontro";
    public final static String ORARIO_INIZIO = "orarioInizio";
    public final static String ORARIO_FINE = "orarioFine";
    public final static String PREZZO = "prezzo";
    public final static String VALUTA = "valuta";
    public final static String NOTE_AGGIUNTIVE = "noteAggiuntive";
    public final static String IDCICERONE = "idCicerone";
    public final static String LINGUA = "lingua";
    public final static String LINGUA_SECONDARIA = "linguaSecondaria";
    public final static String ITINERARIO = "itinerario";
    public final static String REQUISITI = "requisiti";
    public final static String LUOGO_INCONTRO = "luogo";
    public final static String INDIRIZZO = "indirizzo";
    public final static String STATO_EVENTO = "stato";
    public final static String STATO_IN_CORSO = "IN CORSO";
    public final static String STATO_PASSATO = "PASSATO";


        public Event() {
            partecipanti = new ArrayList<>();
    } //per firebase.

    public Event(String titolo, String descrizione, String categoria, int nMaxPartecipanti, String dateEvento,
                 String orarioIncontro, String orarioInizio, double prezzo,
                 String valuta, List<Stage> itinerario,String idCicerone) {
        partecipanti = new ArrayList<>();
        this.titolo = titolo;
        this.descrizione = descrizione;
        this.categoria = categoria;
        this.nMaxPartecipanti = nMaxPartecipanti;
        this.dateEvento = dateEvento;
        this.orarioIncontro = orarioIncontro;
        this.orarioInizio = orarioInizio;
        this.prezzo = prezzo;
        this.valuta = valuta;
        this.itinerario= itinerario;
        this.idCicerone = idCicerone;
    }

    public List<String> getPartecipanti() {
        return partecipanti;
    }

    public void setPartecipanti(List<String> partecipanti) {
        this.partecipanti = partecipanti;
    }

    public String getIdCicerone() {
        return idCicerone;
    }

    public void setIdCicerone(String idCicerone) {
        this.idCicerone = idCicerone;
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

    public List<Stage> getItinerario() {
        return itinerario;
    }

    public void setItinerario(List<Stage> itinerario) {
        this.itinerario = itinerario;
    }

    public String getNoteAggiuntive() {
        return noteAggiuntive;
    }

    public void setNoteAggiuntive(String noteAggiuntive) {
        this.noteAggiuntive = noteAggiuntive;
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

    public String getStato() {
        return stato;
    }
    public void setStato(String stato) {
        this.stato = stato;
    }


    public Map<String,Object>toMap(){
        Map<String,Object> event = new HashMap<>();
        event.put("titolo",this.titolo);
        event.put("foto",this.foto);
        event.put("descrizione",this.descrizione);
        event.put("categoria",this.categoria);
        event.put("nMaxPartecipanti",this.nMaxPartecipanti);
        event.put("dateEvento",this.dateEvento);
        event.put("orarioIncontro",this.orarioIncontro);
        event.put("orarioInizio",this.orarioInizio);
        event.put("orarioFine",this.orarioFine);
        event.put("prezzo",this.prezzo);
        event.put("valuta",this.valuta);
        event.put("partecipanti",this.partecipanti);
        event.put("noteAggiuntive",this.noteAggiuntive);
        event.put("idCicerone",this.idCicerone);
        event.put("lingua",this.lingua);
        event.put("linguaSecondaria",this.linguaSecondaria);
        event.put("itinerario",this.itinerario);
        event.put("requisiti",this.requisiti);
        event.put("luogo",this.luogo);
        event.put("indirizzo",this.indirizzo);
        event.put("stato",this.stato);
        event.put("id",this.id);
        return event;
    }
    @Override
    public boolean delete(String id){
        FirebaseFirestore.getInstance().collection(DataFetch.EVENTI).document(id).delete();
        return true;
    }
    @Override
    public void createEventToDatabase(){
        FirebaseFirestore.getInstance().collection(DataFetch.EVENTI).document(this.id).set(this);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public void initStatus(){
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        Date currentDate = new Date();
        String date = sdf.format(currentDate);
        Request request = new Request();

        FirebaseFirestore.getInstance().collection(DataFetch.EVENTI).get().addOnSuccessListener(queryDocumentSnapshots -> {
            for(DocumentSnapshot ds : queryDocumentSnapshots.getDocuments()){
                Event event = ds.toObject(Event.class);
                try {
                    if(sdf.parse(event.getDateEvento()).before(sdf.parse(date))){
                        event.setStato(Event.STATO_PASSATO);
                        request.addStatoEvent(ds.getId(),Event.STATO_PASSATO);
                        FirebaseFirestore.getInstance().collection(DataFetch.EVENTI).document(ds.getId()).update(event.toMap());
                    }
                    else if (sdf.parse(event.getDateEvento()).equals(sdf.parse(date))||sdf.parse(event.getDateEvento()).after(sdf.parse(date))){
                        event.setStato(Event.STATO_IN_CORSO);
                        request.addStatoEvent(ds.getId(),Event.STATO_IN_CORSO);
                        FirebaseFirestore.getInstance().collection(DataFetch.EVENTI).document(ds.getId()).update(event.toMap());
                    }
                } catch (ParseException e) {
                    Log.d(TAG,e.getMessage());
                }
            }
        });

    }

    @Override
    public boolean updateEventToDatabase(String idDocument){
        FirebaseFirestore.getInstance().collection(DataFetch.EVENTI).document(idDocument).update(this.toMap()).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                return;
            }
        });
        return true;
    }

    @Override
    public boolean addPartecipants(String idAttivita, String idPartecipante) {
        //modifico il database di Event
        //aggiungo il partecipante e decremento il numero di partecipanti massimi
        FirebaseFirestore.getInstance().collection(DataFetch.EVENTI).document(idAttivita).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Event event = documentSnapshot.toObject(Event.class);
                if(event.getPartecipanti() != null){
                    partecipanti =  event.getPartecipanti();
                    partecipanti.add(idPartecipante);
                    event.setPartecipanti(partecipanti);}
                else{
                    partecipanti.add(idPartecipante);
                    event.setPartecipanti(partecipanti);}
                event.setnMaxPartecipanti(event.getnMaxPartecipanti()-1);
                event.updateEventToDatabase(idAttivita);
            }

    });

    return true;

}

    @Override
    public boolean deletePartecipants(String idAttivita, String idPartecipante) {
        FirebaseFirestore.getInstance().collection(DataFetch.EVENTI).document(idAttivita).get().
                addOnSuccessListener(documentSnapshot -> {
                    Event event = documentSnapshot.toObject(Event.class);
                    if (event.getPartecipanti() != null) {
                        partecipanti = event.getPartecipanti();
                        partecipanti.remove(idPartecipante);
                        event.setPartecipanti(partecipanti);
                        event.setnMaxPartecipanti(event.getnMaxPartecipanti() + 1);
                        event.updateEventToDatabase(idAttivita);
                    }
                });
        return true;}
}


