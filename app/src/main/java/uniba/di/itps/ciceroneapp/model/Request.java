package uniba.di.itps.ciceroneapp.model;

import java.util.List;

public class Request {
    User globetrotter;
    List<Guest> ospiti;
    int nPartecipanti;
    Event attivita;

    public Request(User globetrotter, List<Guest> ospiti, int nPartecipanti, Event attivita) {
        this.globetrotter = globetrotter;
        this.ospiti = ospiti;
        this.nPartecipanti = nPartecipanti;
        this.attivita = attivita;
    }

    public User getGlobetrotter() {
        return globetrotter;
    }

    public void setGlobetrotter(User globetrotter) {
        this.globetrotter = globetrotter;
    }

    public List<Guest> getOspiti() {
        return ospiti;
    }

    public void setOspiti(List<Guest> ospiti) {
        this.ospiti = ospiti;
    }

    public int getnPartecipanti() {
        return nPartecipanti;
    }

    public void setnPartecipanti(int nPartecipanti) {
        this.nPartecipanti = nPartecipanti;
    }

    public Event getAttivita() {
        return attivita;
    }

    public void setAttivita(Event attivita) {
        this.attivita = attivita;
    }
}
