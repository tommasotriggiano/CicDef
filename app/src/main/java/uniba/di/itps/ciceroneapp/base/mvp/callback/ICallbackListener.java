package uniba.di.itps.ciceroneapp.base.mvp.callback;

import java.util.ArrayList;

import uniba.di.itps.ciceroneapp.model.Event;


public interface ICallbackListener {
    void onCallback(ArrayList<Event> events);
}
