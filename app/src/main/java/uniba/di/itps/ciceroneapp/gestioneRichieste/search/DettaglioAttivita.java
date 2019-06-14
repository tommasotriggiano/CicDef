package uniba.di.itps.ciceroneapp.gestioneRichieste.search;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import de.hdodenhof.circleimageview.CircleImageView;
import uniba.di.itps.ciceroneapp.R;
import uniba.di.itps.ciceroneapp.main.MainActivity;

public class DettaglioAttivita extends AppCompatActivity implements GestioneRichiesteInterfaccia.MvpView {
    public TextView titolo,categoria,lingua,durata,luogo,data,descrizione,indirizzo,requisiti,nomeC,cognomeC,oraInizio,tappe,richiedi;
    private ImageView immagineAtt;
    private LinearLayout linear;
    private CircleImageView ciceroneProfile;
    private GestioneRichiesteInterfaccia.Presenter presenter;
    private Intent receive;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_detail_activity);
        immagineAtt = findViewById(R.id.imageView);
        titolo = findViewById(R.id.titolo);
        categoria = findViewById(R.id.categoria);
        luogo = findViewById(R.id.luogo);
        durata = findViewById(R.id.durata);
        lingua = findViewById(R.id.lingua);
        data = findViewById(R.id.data);
        ciceroneProfile = findViewById(R.id.imageProfile);
        nomeC = findViewById(R.id.nome);
        cognomeC = findViewById(R.id.cognome);
        linear = findViewById(R.id.linearProfile);
        descrizione = findViewById(R.id.descrizioneText);
        indirizzo = findViewById(R.id.doveText);
        oraInizio = findViewById(R.id.oraText);
        tappe = findViewById(R.id.visualizzaTappe);
        richiedi = findViewById(R.id.effettuaRichiesta);
        presenter = new GestioneRichiestePresenter(this);
        receive =  getIntent();

        presenter.setEventDetail(receive,this);
        richiedi.setOnClickListener(v -> {
            presenter.createRequestToDatabase(receive);
            startActivity(new Intent(DettaglioAttivita.this, MainActivity.class));

        });
    }





    @Override
    public void showCategories(TextView category) { }

    @Override
    public void setTextTitolo(String string) { titolo.setText(string); }

    @Override
    public void setTextCategoria(String string) { this.categoria.setText(string); }

    @Override
    public void setTextLuogo(String string) {this.luogo.setText(string);}

    @Override
    public void setTextDurata(String string) { this.durata.setText(string + getResources().getString(R.string.hours)); }

    @Override
    public void setTextLingua(String string) { this.lingua.setText(getResources().getString(R.string.offeredLanguage) +string);}

    @Override
    public void setTextData(String string) { this.data.setText(string);}

    @Override
    public void setTextNomeC(String string) { this.nomeC.setText(string); }

    @Override
    public void setTextCognomeC(String string) { this.cognomeC.setText(string); }

    @Override
    public void setTextOraInizio(String string) { this.oraInizio.setText(string); }
    @Override
    public void setTextDescrizione(String string) { this.descrizione.setText(string); }
    @Override
    public void setTextIndirizzo(String string) { this.indirizzo.setText(string); }
    @Override
    public void setImmagineProfilo(String fotoprofilo) { Picasso.get().load(fotoprofilo).into(this.ciceroneProfile); }
    @Override
    public void setImmagineAttività(String img) { Picasso.get().load(img).into(this.immagineAtt); }

    @Override
    public void goToGuests() {

    }

    @Override
    public void goToEvent() {
        startActivity(new Intent(DettaglioAttivita.this,MainActivity.class));

    }
}
