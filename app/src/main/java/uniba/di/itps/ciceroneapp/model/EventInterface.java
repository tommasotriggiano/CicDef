package uniba.di.itps.ciceroneapp.model;

interface EventInterface {
    boolean delete();

    void createEventToDatabase();

    void initStatus();

    void updateEventToDatabase(String idDocument);
}
