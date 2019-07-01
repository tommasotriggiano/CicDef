package uniba.di.itps.ciceroneapp.GestioneAttività.detailEventCreated;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import uniba.di.itps.ciceroneapp.GestioneAttività.InterfaceGestioneAttività;
import uniba.di.itps.ciceroneapp.GestioneAttività.PresenterGestioneAttività;
import uniba.di.itps.ciceroneapp.R;

public class ViewPartecipants extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_partecipants);
        InterfaceGestioneAttività.Presenter presenter = new PresenterGestioneAttività(this);
        Toolbar toolbar = findViewById(R.id.indietro);
        RecyclerView partecipants = findViewById(R.id.partecipantsTextView);
        toolbar.setNavigationOnClickListener(v -> finish());
        Intent receive = getIntent();
        partecipants.setHasFixedSize(true);
        partecipants.setLayoutManager(new LinearLayoutManager(this));
        presenter.initRecyclerViewRichiedenti(partecipants,receive,"CONFERMATA");

    }


}
