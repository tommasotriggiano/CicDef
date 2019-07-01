package uniba.di.itps.ciceroneapp.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Guest implements Serializable {

    private String nome;
    private String cognome;
    private String email;

    public Guest(String nome, String cognome) {
        this.nome = nome;
        this.cognome = cognome;
        email = "";
    }
    public Guest(){
        //Firebase
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Map<String, Object> toMap() {
        Map<String,Object> guests = new HashMap<>();
        guests.put("nome",this.nome);
        guests.put("cognome",this.cognome);
        guests.put("email",this.email);
        return guests;
    }
}
