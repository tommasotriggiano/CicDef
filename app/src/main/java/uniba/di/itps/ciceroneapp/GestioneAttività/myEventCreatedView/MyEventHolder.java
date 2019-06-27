package uniba.di.itps.ciceroneapp.GestioneAttività.myEventCreatedView;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import uniba.di.itps.ciceroneapp.GestioneAttività.InterfaceGestioneAttività;
import uniba.di.itps.ciceroneapp.R;

public  class MyEventHolder extends RecyclerView.ViewHolder implements InterfaceGestioneAttività.MvpView  {

    CardView root;
    private ImageView fotoEvento;
    private TextView titolo;
    private TextView date;
    private TextView nIscritti;


    MyEventHolder(@NonNull View itemView) {
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
    public void setTextDescrizione(String string) {

    }

    @Override
    public void setTextPrezzo(String prezzo, String valuta) {

    }

    @Override
    public void setTextDurata(String string) {

    }

    @Override
    public void setTextLingua(String string) {

    }

    @Override
    public void setTextData(String data) {
        this.date.setText(data);

    }

    @Override
    public void setImmatività(String url) {
        Picasso.get().load(url).into(this.fotoEvento);
    }

    @Override
    public void setTextOrario(String orarioIncontro) {

    }

    @Override
    public void setTextCategoria(String string) {

    }

    @Override
    public void setTextLuogo(String luogo) {

    }

    @Override
    public void setTextIndirizzo(String indirizzo) {

    }

    @Override
    public void setTextStato(String stato) {

    }

    @Override
    public void goToEvent() {

    }

    @Override
    public void goToRequest() {

    }


}
