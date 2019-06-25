package uniba.di.itps.ciceroneapp.GestioneAttività.myEventRequestedView;

import android.content.Intent;
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
import uniba.di.itps.ciceroneapp.gestioneRichieste.search.GestioneRichiesteInterfaccia;

public class MyEventRequestedHolder extends RecyclerView.ViewHolder implements GestioneRichiesteInterfaccia.MvpView {
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
    public void enableButton(Intent receive) {

    }

    @Override
    public void showCategories(TextView category) {

    }

    @Override
    public void setTextTitolo(String titolo) {
        this.titolo.setText(titolo);
    }

    @Override
    public void setTextCategoria(String string) {
    }


    @Override
    public void setTextData(String data) {
        this.data.setText(data);

    }

    @Override
    public void setTextNomeC(String string) {}

    @Override
    public void setTextCognomeC(String string) {}

    @Override
    public void setTextOraInizio(String string) {
        this.orarioIncontro.setText(string); }

    @Override
    public void setTextDescrizione(String string) { }


    @Override
    public void setTextLuogo(String luogo) {
        this.luogoIncontro.setText(luogo); }

    @Override
    public void setTextDurata(String string) {

    }

    @Override
    public void setTextLingua(String string) {
    }

    @Override
    public void setTextIndirizzo(String indirizzo) {
        this.indirizzoIncontro.setText(indirizzo); }

    @Override
    public void setTextPrezzo(String prezzo, String valuta) { }

    @Override
    public void setImmagineProfilo(String fotoprofilo) {}

    @Override
    public void setImmagineAttività(String img) {
        Picasso.get().load(img).into(this.fotoEvento);
    }

    @Override
    public void setNMaxPartecipanti(String nMaxPartecipanti) {
        this.orarioIncontro.setText(nMaxPartecipanti);

    }

    @Override
    public void setTextStato(String stato) {
        this.stato.setText(stato);
    }

    @Override
    public void goToGuests() {

    }

    @Override
    public void goToEvent() {

    }
}
