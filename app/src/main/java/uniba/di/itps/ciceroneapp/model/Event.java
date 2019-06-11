
package uniba.di.itps.ciceroneapp.model;



import android.annotation.SuppressLint;
import android.content.Context;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import uniba.di.itps.ciceroneapp.R;
import uniba.di.itps.ciceroneapp.base.mvp.callback.ICallbackListener;
import uniba.di.itps.ciceroneapp.data.DataFetch;

public class Event {

    private String titolo;
    private String foto;
    private String descrizione;
    private String categoria;
    private int nMaxPartecipanti;
    private List<User> partecipanti;
    private String dateEvento;
    private String orarioIncontro;
    private String orarioInizio;
    private String orarioFine;
    private double prezzo;
    private String valuta;
    private Map<String,Object> itinerario;
    private String noteAggiuntive;
    private String idCicerone;
    private String lingua;
    private String linguaSecondaria;
    private Map<String,Object> tappe;
    private String requisiti;
    private String luogo;
    private String indirizzo;
    private String stato;

    public Event() {
    } //per firebase.

    public Event(String titolo, String descrizione, String categoria, int nMaxPartecipanti, String dateEvento,
                 String orarioIncontro, String orarioInizio, double prezzo,
                 String valuta, Map<String,Object> itinerario,String idCicerone) {
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
        this.idCicerone = idCicerone;
    }

    public List<User> getPartecipanti() {
        return partecipanti;
    }

    public void setPartecipanti(List<User> partecipanti) {
        this.partecipanti = partecipanti;
    }

    public String getIdCicerone() {
        return idCicerone;
    }

    public void setIdCicerone(String idCicerone) {
        this.idCicerone = idCicerone;
    }

    public Map<String, Object> getTappe() {
        return tappe;
    }

    public void setTappe(Map<String, Object> tappe) {
        this.tappe = tappe;
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

    public Map<String, Object> getItinerario() {
        return itinerario;
    }

    public void setItinerario(Map<String, Object> itinerario) {
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
        event.put("itinerario",this.itinerario);
        event.put("noteAggiuntive",this.noteAggiuntive);
        event.put("idCicerone",this.idCicerone);
        event.put("lingua",this.lingua);
        event.put("linguaSecondaria",this.linguaSecondaria);
        event.put("tappe",this.tappe);
        event.put("requisiti",this.requisiti);
        event.put("luogo",this.luogo);
        event.put("indirizzo",this.indirizzo);
        event.put("stato",this.stato);
        return event;
    }

    public void createEventToDatabase(){

        this.setIdCicerone(FirebaseAuth.getInstance().getCurrentUser().getUid());
        FirebaseFirestore.getInstance().collection(DataFetch.EVENTI).document().set(this);
    }

    public void initStatus(){
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date currentDate = new Date();
        String date = sdf.format(currentDate);

        FirebaseFirestore.getInstance().collection(DataFetch.EVENTI).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for(DocumentSnapshot ds : queryDocumentSnapshots.getDocuments()){
                    Event event = ds.toObject(Event.class);
                    try {
                        if(sdf.parse(event.getDateEvento()).before(sdf.parse(date))){
                            event.setStato("PASSATO");
                            FirebaseFirestore.getInstance().collection(DataFetch.EVENTI).document(ds.getId()).update(event.toMap());
                        }
                        else if (sdf.parse(event.getDateEvento()).equals(sdf.parse(date))||sdf.parse(event.getDateEvento()).after(sdf.parse(date))){
                            event.setStato("IN CORSO");
                            FirebaseFirestore.getInstance().collection(DataFetch.EVENTI).document(ds.getId()).update(event.toMap());
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

    }

    public void updateEventToDatabase(String idDocument){
        FirebaseFirestore.getInstance().collection(DataFetch.EVENTI).document(idDocument).update(this.toMap());
    }

}


