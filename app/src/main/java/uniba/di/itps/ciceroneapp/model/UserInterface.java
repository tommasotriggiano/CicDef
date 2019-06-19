package uniba.di.itps.ciceroneapp.model;

import java.util.Map;

interface UserInterface {
    Map<String,Object> toMap();

    void createUsertoDatabase();

    boolean updateUsertoDatabase();
}
