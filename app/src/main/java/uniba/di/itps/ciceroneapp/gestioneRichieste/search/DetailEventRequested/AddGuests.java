package uniba.di.itps.ciceroneapp.gestioneRichieste.search.DetailEventRequested;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;
import uniba.di.itps.ciceroneapp.R;
import uniba.di.itps.ciceroneapp.gestioneRichieste.search.GestioneRichiesteInterfaccia;
import uniba.di.itps.ciceroneapp.gestioneRichieste.search.GestioneRichiestePresenter;

public class AddGuests extends AppCompatActivity implements GestioneRichiesteInterfaccia.MvpView, View.OnClickListener {
    private TextView showGuest,numberGuest,myName,mySurname,prezzo;
    private Button confirm,addGuest;
    private EditText nameGuest,surnameGuest,emailGuest;
    private CircleImageView myImage;
    private RelativeLayout listGuestRelative,confirmLayout;
    private ListView listGuest;
    private Toolbar exit;
    private Intent receive;
    private GestioneRichiesteInterfaccia.Presenter presenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new GestioneRichiestePresenter(this);
        setContentView(R.layout.activity_add_guests);
        showGuest = findViewById(R.id.ospiti);
        numberGuest = findViewById(R.id.numGuest);
        myName = findViewById(R.id.nome);
        exit = findViewById(R.id.indietro);
        mySurname = findViewById(R.id.cognome);
        prezzo = findViewById(R.id.prezzoConferma);
        receive = getIntent();
        confirm = findViewById(R.id.confermaRichiesta);
        addGuest = findViewById(R.id.addGuestButton);
        nameGuest = findViewById(R.id.nomeOspite);
        surnameGuest = findViewById(R.id.cognomeOspite);
        emailGuest = findViewById(R.id.emailOspite);
        myImage = findViewById(R.id.imageProfile);
        listGuestRelative = findViewById(R.id.ospit);
        listGuest = findViewById(R.id.listGuests);
        confirmLayout = findViewById(R.id.conferma);
        showGuest.setOnClickListener(this);
        addGuest.setOnClickListener(this);
        confirm.setOnClickListener(this);

        exit.setNavigationOnClickListener(v -> finish());
        presenter.setAddGuest(receive,this);


    }




    @Override
    public void showCategories(TextView category) {}

    @Override
    public void setTextTitolo(String string) {}

    @Override
    public void setTextCategoria(String string) {}

    @Override
    public void setTextLuogo(String string) {}

    @Override
    public void setTextDurata(String string) {}

    @Override
    public void setTextLingua(String string) {}

    @Override
    public void setTextData(String string) {}

    @Override
    public void setTextNomeC(String string) {
        this.myName.setText(string); }

    @Override
    public void setTextCognomeC(String string) {
        this.mySurname.setText(string); }

    @Override
    public void setTextOraInizio(String string) {}

    @Override
    public void setTextDescrizione(String string) {}

    @Override
    public void setTextIndirizzo(String string) {}


    @Override
    public void setTextPrezzo(String prezzo, String valuta) {
        this.prezzo.setText(prezzo + " "+valuta); }

    @Override
    public void setImmagineProfilo(String fotoprofilo) {
        Picasso.get().load(fotoprofilo).into(this.myImage); }

    @Override
    public void setImmagineAttività(String img) {}

    @Override
    public void setNMaxPartecipanti(String nMaxPartecipanti) {
        this.numberGuest.setText(nMaxPartecipanti);
    }

    @Override
    public void setTextStato(String stato) {}

    @Override
    public void goToGuests() {}

    @Override
    public void goToEvent() {
        //deve andare alla sezione dove visualizza le richieste
        startActivity(new Intent(AddGuests.this, DetailEventRequest.class)); }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.ospiti:
                listGuestRelative.setVisibility(View.VISIBLE);
                listGuest.setVisibility(View.GONE);
                confirmLayout.setVisibility(View.GONE);
                break;

            case R.id.addGuestButton:
                presenter.setListViewGuest(listGuest,nameGuest.getText().toString(),surnameGuest.getText().toString(),emailGuest.getText().toString());
                listGuestRelative.setVisibility(View.GONE);
                listGuest.setVisibility(View.VISIBLE);
                confirmLayout.setVisibility(View.VISIBLE);
                break;

            case R.id.confermaRichiesta:
                presenter.createRequestToDatabase(receive,this);
                break;
        }

    }
}
