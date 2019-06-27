package uniba.di.itps.ciceroneapp.model;

public interface EventInterface {

    boolean delete(String id);
    void createEventToDatabase();
    void initStatus();
    boolean updateEventToDatabase(String idDocument);

    boolean addPartecipants(String idAttivita, String idPartecipante);
}
