package uniba.di.itps.ciceroneapp.GestioneAttività.detailEventCreated;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.io.Serializable;
import java.util.Map;

import uniba.di.itps.ciceroneapp.GestioneAttività.InterfaceGestioneAttività;
import uniba.di.itps.ciceroneapp.GestioneAttività.PresenterGestioneAttività;
import uniba.di.itps.ciceroneapp.R;

public class ViewRequest extends AppCompatActivity {
    private RecyclerView richieste;
    private Toolbar indietro;
    InterfaceGestioneAttività.Presenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewrequest);
        richieste = findViewById(R.id.richiedenti);
        indietro = findViewById(R.id.toolbarindietro);
        presenter = new PresenterGestioneAttività(this);
        Intent receive = getIntent();
        richieste.setHasFixedSize(true);
        richieste.setLayoutManager(new LinearLayoutManager(this));
        presenter.initRecyclerViewRichiedenti(richieste,receive);
        indietro.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String,Object> rec = (Map<String, Object>) receive.getSerializableExtra("evento");
                Intent goBack = new Intent(ViewRequest.this,DetailEvent.class);
                goBack.putExtra("evento", (Serializable) rec);
               startActivity(goBack);
            }
        });


    }
}
