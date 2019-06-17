package uniba.di.itps.ciceroneapp.GestioneAttività.myEventCreatedView;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import uniba.di.itps.ciceroneapp.GestioneAttività.InterfaceGestioneAttività;
import uniba.di.itps.ciceroneapp.GestioneAttività.InterfaceGestioneAttività.MvpView;
import uniba.di.itps.ciceroneapp.R;

public  class MyEventHolder extends RecyclerView.ViewHolder implements InterfaceGestioneAttività.MvpView  {

    CardView root;
    ImageView fotoEvento;
    TextView titolo;
    TextView date;
    TextView nIscritti;


    public MyEventHolder(@NonNull View itemView) {
        super(itemView);
        root = itemView.findViewById(R.id.cardViewCreated);
        fotoEvento = itemView.findViewById(R.id.immagineA);
        titolo = itemView.findViewById(R.id.titolo);
        date = itemView.findViewById(R.id.data);
        nIscritti = itemView.findViewById(R.id.posti);
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
        this.titolo.setText(titolo); }

    @Override
    public void setTextPartecipanti(String partecipanti) {
        this.nIscritti.setText(partecipanti); }

    @Override
    public void setTextData(String data) {
        this.date.setText(data);

    }

    @Override
    public void setImmatività(String url) {
        Picasso.get().load(url).into(this.fotoEvento);
    }
}
