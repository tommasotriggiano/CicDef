package uniba.di.itps.ciceroneapp.model;

import java.util.Map;

public interface RequestInterface {
    boolean addRequestToDatabase();
    boolean updateStatoToDatabase(String id,String stato);
    Map<String,Object> toMap();
}
