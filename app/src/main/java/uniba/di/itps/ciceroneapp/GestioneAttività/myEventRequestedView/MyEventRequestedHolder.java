package uniba.di.itps.ciceroneapp.GestioneAttività.myEventRequestedView;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import uniba.di.itps.ciceroneapp.GestioneAttività.InterfaceGestioneAttività;
import uniba.di.itps.ciceroneapp.R;

public class MyEventRequestedHolder extends RecyclerView.ViewHolder implements InterfaceGestioneAttività.MvpView {
    LinearLayout root;
    ImageView fotoEvento;
    TextView titolo;
    TextView orarioIncontro;
    TextView data;
    TextView luogoIncontro;
    TextView indirizzoIncontro;



    public MyEventRequestedHolder(@NonNull View itemView) {
        super(itemView);
        fotoEvento = itemView.findViewById(R.id.picEventReqIW);
        root=itemView.findViewById(R.id.rootRequested);
        titolo = itemView.findViewById(R.id.titoloReqTW);
        orarioIncontro=itemView.findViewById(R.id.orarioIncReqTW);
        data = itemView.findViewById(R.id.dataReqTW);
        luogoIncontro = itemView.findViewById(R.id.luogoReqTW);
        indirizzoIncontro=itemView.findViewById(R.id.indirizzoIncontroReqTW);
    }

    @Override
    public void setFragment(Fragment fragment) {

    }

    @Override
    public void hideBottomNavigation() {

    }

    @Override
    public void showBottomNavigation() {

    }

    @Override
    public void showDialogDate(TextView date, boolean birth) {

    }

    @Override
    public void setTextTitolo(String titolo) {
        this.titolo.setText(titolo);

    }

    @Override
    public void setTextPartecipanti(String partecipanti) {

    }

    @Override
    public void setTextData(String data) {

    }

    @Override
    public void setImmatività(String url) {

    }
}
