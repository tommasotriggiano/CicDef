package uniba.di.itps.ciceroneapp.GestioneAttività.detailEventCreated;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;

import uniba.di.itps.ciceroneapp.GestioneAttività.InterfaceGestioneAttività;
import uniba.di.itps.ciceroneapp.GestioneAttività.PresenterGestioneAttività;
import uniba.di.itps.ciceroneapp.R;
import uniba.di.itps.ciceroneapp.main.MainActivity;
import uniba.di.itps.ciceroneapp.model.Event;
import uniba.di.itps.ciceroneapp.model.User;

public class DetailEvent extends AppCompatActivity implements InterfaceGestioneAttività.MvpView {
    private TextView titolo,categoria,lingua,durata,luogo,data,descrizione,indirizzo,requisiti,prezzo,valuta,nMaxPartecipanti,oraInizio,tappe,vediRichieste,partecipanti;
    private ImageView immagineAtt;
    private ImageButton modify,delete;
    private InterfaceGestioneAttività.Presenter presenter;
    private Intent receive;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_event);
        modify = findViewById(R.id.modify);
        delete = findViewById(R.id.delete);
        immagineAtt = findViewById(R.id.imageView);
        titolo = findViewById(R.id.titolo);
        categoria = findViewById(R.id.categoria);
        luogo = findViewById(R.id.luogo);
        durata = findViewById(R.id.durata);
        lingua = findViewById(R.id.lingua);
        data = findViewById(R.id.data);
        descrizione = findViewById(R.id.descrizioneText);
        indirizzo = findViewById(R.id.doveText);
        oraInizio = findViewById(R.id.oraText);
        tappe = findViewById(R.id.visualizzaTappe);
        vediRichieste = findViewById(R.id.effettuaRichiesta);
        prezzo = findViewById(R.id.prezzoText);
        //valuta = findViewById(R.id.valuta);
        //nMaxPartecipanti = findViewById(R.id.numeroPartecipanti);
        //presenter1 = new PresenterGestioneAttività(this);
        presenter = new PresenterGestioneAttività(this);
        receive =  getIntent();
        presenter.setEventDetailC(receive,this);
        delete.setOnClickListener(v -> {
        presenter.deleteEvent(receive,this);

        });
        modify.setOnClickListener(v -> presenter.goToModify(receive));
        vediRichieste.setOnClickListener(v -> {
            presenter.gotoViewRequest(this);

        });
    }

    @Override
    public void setFragment(Fragment fragment) {}

    @Override
    public void hideBottomNavigation() {}

    @Override
    public void showBottomNavigation() {}

    @Override
    public void showDialogDate(TextView date, boolean birth) {}

    @Override
    public void setTextTitolo(String titolo) {
        this.titolo.setText(titolo);}

    @Override
    public void setTextCategoria(String string) { this.categoria.setText(string); }

    @Override
    public void setTextLuogo(String string) {this.luogo.setText(string);}

    @Override
    public void setTextDurata(String string) { this.durata.setText(string +" "+ getResources().getString(R.string.hours)); }

    @Override
    public void setTextLingua(String string) { this.lingua.setText(getResources().getString(R.string.offeredLanguage)+ " " +string);}

    @Override
    public void setTextData(String string) { this.data.setText(string);}

    @Override
    public void setTextPartecipanti(String partecipanti) {
    }

    @Override
    public void setTextDescrizione(String string) {
        this.descrizione.setText(string);
    }


    @Override
    public void setImmatività(String url) {
        Picasso.get().load(url).into(this.immagineAtt);
    }

    @Override
    public void setTextOrario(String orarioIncontro) {
        this.oraInizio.setText(orarioIncontro);

    }


    @Override
    public void setTextIndirizzo(String indirizzo) {
        this.indirizzo.setText(indirizzo);

    }

    @Override
    public void setTextStato(String stato) {

    }
    @Override
    public void setTextPrezzo(String prezzo,String valuta) {
        this.prezzo.setText(prezzo + "  "+valuta );
    }

    @Override
    public void goToEvent() {
        this.startActivity(new Intent(DetailEvent.this, MainActivity.class));
    }

    @Override
    public void goToRequest() {
        Intent goToRequest = new Intent(DetailEvent.this, ViewRequest.class);
        goToRequest.putExtra("evento", receive.getSerializableExtra("evento"));
        startActivity(goToRequest);
    }

}

