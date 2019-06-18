package uniba.di.itps.ciceroneapp.GestioneAttività.myEventRequestedView;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import uniba.di.itps.ciceroneapp.GestioneAttività.InterfaceGestioneAttività;
import uniba.di.itps.ciceroneapp.R;

public class MyEventRequestedHolder extends RecyclerView.ViewHolder implements InterfaceGestioneAttività.MvpView {
    LinearLayout root;
    private ImageView fotoEvento;
    private TextView titolo;
    private TextView orarioIncontro;
    private TextView data;
    private TextView luogoIncontro;
    private TextView indirizzoIncontro;
    private TextView stato;



    public MyEventRequestedHolder(@NonNull View itemView) {
        super(itemView);
        fotoEvento = itemView.findViewById(R.id.picEventReqIW);
        root=itemView.findViewById(R.id.rootRequested);
        titolo = itemView.findViewById(R.id.titoloReqTW);
        orarioIncontro=itemView.findViewById(R.id.orarioIncReqTW);
        data = itemView.findViewById(R.id.dataReqTW);
        luogoIncontro = itemView.findViewById(R.id.luogoReqTW);
        indirizzoIncontro=itemView.findViewById(R.id.indirizzoIncontroReqTW);
        stato = itemView.findViewById(R.id.statusReqTW);
    }

    @Override
    public void setFragment(Fragment fragment) { }

    @Override
    public void hideBottomNavigation() { }

    @Override
    public void showBottomNavigation() { }

    @Override
    public void showDialogDate(TextView date, boolean birth) { }

    @Override
    public void setTextTitolo(String titolo) {
        this.titolo.setText(titolo);
    }

    @Override
    public void setTextPartecipanti(String partecipanti) { }

    @Override
    public void setTextData(String data) {
        this.data.setText(data);

    }

    @Override
    public void setImmatività(String url) {
        Picasso.get().load(url).into(this.fotoEvento);

    }

    @Override
    public void setTextOrario(String orarioIncontro) {
        this.orarioIncontro.setText(orarioIncontro);

    }

    @Override
    public void setTextLuogo(String luogo) {
        this.luogoIncontro.setText(luogo); }

    @Override
    public void setTextIndirizzo(String indirizzo) {
        this.indirizzoIncontro.setText(indirizzo); }

    @Override
    public void setTextStato(String stato) {
        this.stato.setText(stato);


    }
}
