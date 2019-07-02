package uniba.di.itps.ciceroneapp.gestioneRichieste.search;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import uniba.di.itps.ciceroneapp.R;

public class ViewHolderAttivitàRicercate extends RecyclerView.ViewHolder implements GestioneRichiesteInterfaccia.MvpView {
    GestioneRichiesteInterfaccia.Presenter presenter;
    TextView titolo,categoria,numPartecipanti,lingua,prezzo,rating,valuta;
    ImageView image;
    CardView cardView;

    ViewHolderAttivitàRicercate(@NonNull View itemView, Context mCtx) {
        super(itemView);
        presenter = new GestioneRichiestePresenter(mCtx);
        cardView = itemView.findViewById(R.id.cardViewSearch);
        titolo = itemView.findViewById(R.id.titolo);
        categoria = itemView.findViewById(R.id.data);
        numPartecipanti = itemView.findViewById(R.id.posti);
        lingua = itemView.findViewById(R.id.lingua);
        prezzo = itemView.findViewById(R.id.textViewPrice);
        rating = itemView.findViewById(R.id.rating);
        image = itemView.findViewById(R.id.immagineA);
        valuta = itemView.findViewById(R.id.valuta);


    }




    @Override
    public void showCategories(TextView category) {

    }

    @Override
    public void setTextTitolo(String string) {
        this.titolo.setText(string); }

    @Override
    public void setTextCategoria(String string) {
        this.categoria.setText(string);

    }

    @Override
    public void setTextLuogo(String string) {

    }

    @Override
    public void setTextDurata(String string) {

    }

    @Override
    public void setTextLingua(String string) {
        this.lingua.setText(string);

    }

    @Override
    public void setTextData(String string) {

    }

    @Override
    public void setTextNomeC(String string) {

    }

    @Override
    public void setTextCognomeC(String string) {

    }

    @Override
    public void setTextOraInizio(String string) {

    }

    @Override
    public void setTextDescrizione(String string) {

    }

    @Override
    public void setTextIndirizzo(String string) {

    }

    @Override
    public void setTextPrezzo(String prezzo, String valuta) {
        this.prezzo.setText(prezzo + "  "+valuta );
    }

    @Override
    public void setImmagineProfilo(String fotoprofilo) {

    }

    @Override
    public void setImmagineAttività(String img) {
        Picasso.get().load(img).into(this.image);

    }

    @Override
    public void setNMaxPartecipanti(String nMaxPartecipanti) {
        this.numPartecipanti.setText(nMaxPartecipanti);
    }

    @Override
    public void setTextStato(String stato) {

    }

    @Override
    public void goToGuests() {

    }

    @Override
    public void goToEvent() {

    }

    @Override
    public void startActivity(Intent passId) {

    }
}


