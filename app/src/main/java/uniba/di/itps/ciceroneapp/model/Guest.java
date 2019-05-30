package uniba.di.itps.ciceroneapp.model;

public class Guest {

    String nome;
    String cognome;
    String email;

    public Guest(String nome, String cognome) {
        this.nome = nome;
        this.cognome = cognome;
        email = "";
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
}
