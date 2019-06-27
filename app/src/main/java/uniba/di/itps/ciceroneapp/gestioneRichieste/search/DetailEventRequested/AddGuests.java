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

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import uniba.di.itps.ciceroneapp.GestioneAttività.detailEventCreated.DetailEvent;
import uniba.di.itps.ciceroneapp.R;
import uniba.di.itps.ciceroneapp.gestioneRichieste.search.GestioneRichiesteInterfaccia;
import uniba.di.itps.ciceroneapp.gestioneRichieste.search.GestioneRichiestePresenter;

public class AddGuests extends DetailEventRequest {
    private TextView showGuest,numberGuest,myName,mySurname,prezzo;
    private Button confirm,addGuest;
    private EditText nameGuest,surnameGuest,emailGuest;
    private CircleImageView myImage;
    private RelativeLayout listGuestRelative,confirmLayout;
    private ListView listGuest;
    private Toolbar exit;
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
        confirm = findViewById(R.id.confermaRichiesta);
        addGuest = findViewById(R.id.addGuestButton);
        nameGuest = findViewById(R.id.nomeOspite);
        surnameGuest = findViewById(R.id.cognomeOspite);
        emailGuest = findViewById(R.id.emailOspite);
        myImage = findViewById(R.id.imageProfile);
        listGuestRelative = findViewById(R.id.ospit);
        listGuest = findViewById(R.id.listGuests);
        confirmLayout = findViewById(R.id.conferma);
        showGuest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listGuestRelative.setVisibility(View.VISIBLE);
                listGuest.setVisibility(View.GONE);
                confirmLayout.setVisibility(View.GONE);
            }
        });
        addGuest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listGuestRelative.setVisibility(View.GONE);
                listGuest.setVisibility(View.VISIBLE);
                confirmLayout.setVisibility(View.VISIBLE);
            }
        });
        exit.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Intent receive = getIntent();
        presenter.setAddGuest(receive,this);


    }



    @Override
    public void setTextNomeC(String string) {
        this.myName.setText(string); }

    @Override
    public void setTextCognomeC(String string) {
        this.mySurname.setText(string); }


    @Override
    public void setTextPrezzo(String prezzo, String valuta) {
        this.prezzo.setText(prezzo + " "+valuta); }

    @Override
    public void setImmagineProfilo(String fotoprofilo) {
        Picasso.get().load(fotoprofilo).into(this.myImage); }

    @Override
    public void goToEvent() {
        //deve andare alla sezione dove visualizza le richieste
        startActivity(new Intent(AddGuests.this, DetailEventRequest.class));

    }
}
