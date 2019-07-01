package uniba.di.itps.ciceroneapp.GestioneAttività.detailEventCreated;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import uniba.di.itps.ciceroneapp.GestioneAttività.InterfaceGestioneAttività;
import uniba.di.itps.ciceroneapp.GestioneAttività.PresenterGestioneAttività;
import uniba.di.itps.ciceroneapp.R;

public class ViewRequest extends AppCompatActivity {
    InterfaceGestioneAttività.Presenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewrequest);
        RecyclerView richieste = findViewById(R.id.richiedenti);
        Toolbar indietro = findViewById(R.id.toolbarindietro);
        presenter = new PresenterGestioneAttività(this);
        Intent receive = getIntent();
        richieste.setHasFixedSize(true);
        richieste.setLayoutManager(new LinearLayoutManager(this));
        presenter.initRecyclerViewRichiedenti(richieste,receive, "IN ATTESA");
        indietro.setNavigationOnClickListener(v -> finish());


    }
}
