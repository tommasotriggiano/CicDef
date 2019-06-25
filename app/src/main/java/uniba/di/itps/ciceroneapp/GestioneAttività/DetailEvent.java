package uniba.di.itps.ciceroneapp.GestioneAttività;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import uniba.di.itps.ciceroneapp.R;
import uniba.di.itps.ciceroneapp.gestioneRichieste.search.DetailEventRequest;
import uniba.di.itps.ciceroneapp.gestioneRichieste.search.GestioneRichiesteInterfaccia;
import uniba.di.itps.ciceroneapp.gestioneRichieste.search.GestioneRichiestePresenter;
import uniba.di.itps.ciceroneapp.main.MainActivity;

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
        presenter.setEventDetail(receive,this);
        delete.setOnClickListener(v -> {
        presenter.deleteEvent(receive,this);

        });
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

    @Override
    public void setTextOrario(String orarioIncontro) {

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
        this.startActivity(new Intent(DetailEvent.this, MainActivity.class));
    }
}

