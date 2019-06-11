package uniba.di.itps.ciceroneapp.main;

import android.content.Context;

import uniba.di.itps.ciceroneapp.model.Event;

public class PresenterMain implements InterfaceMain.Presenter {
    private Context ctx;

    PresenterMain(Context ctx) {
        this.ctx = ctx;
    }

    @Override
    public void initAttivita() {
        Event event = new Event();
        event.initStatus();}

}
