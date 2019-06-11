package uniba.di.itps.ciceroneapp.model;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import uniba.di.itps.ciceroneapp.data.DataFetch;

public class User {
    private String uid;
    private String nome;
    private String cognome;
    private String datanascita;
    private String fotoprofilo;
    private String sesso;
    private String email;


    public User(String nome, String cognome, String email, String uid) {
        this.uid = uid;
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
    }
    public User(){}

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

    public String getDatanascita() {
        return datanascita;
    }

    public void setDatanascita(String datanascita) {
        this.datanascita = datanascita;
    }

    public String getFotoprofilo() {
        return fotoprofilo;
    }

    public void setFotoprofilo(String fotoprofilo) {
        this.fotoprofilo = fotoprofilo;
    }

    public String getSesso() {
        return sesso;
    }

    public void setSesso(String sesso) {
        this.sesso = sesso;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUid(){
        return uid;
    }
    public void setUid(String uid){
        this.uid = uid;
    }

    public Map<String,Object> toMap(){
        Map<String,Object>user = new HashMap<>();
        user.put("uid",this.uid);
        user.put("nome",this.nome);
        user.put("cognome",this.cognome);
        user.put("datanascita",this.datanascita);
        user.put("fotoprofilo",this.fotoprofilo);
        user.put("sesso",this.sesso);
        user.put("email",this.email);
        return user;
    }
    public void createUsertoDatabase(){
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        FirebaseFirestore.getInstance().collection(DataFetch.UTENTI).document(uid).set(this);
    }


}
