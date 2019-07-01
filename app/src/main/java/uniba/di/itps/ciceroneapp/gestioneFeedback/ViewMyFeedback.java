package uniba.di.itps.ciceroneapp.gestioneFeedback;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;


import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;
import uniba.di.itps.ciceroneapp.R;

public class ViewMyFeedback extends AppCompatActivity implements FeedbackInterface.MvpView{
    private CircleImageView imageCicerone;
    private TextView nomeC,cognomeC,emailC,telefono;
    private Toolbar indietro;
    private String id,stato;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_feedback);
        FeedbackInterface.Presenter presenter = new GestioneFeedBackPresenter(this);
        imageCicerone = findViewById(R.id.imageProfile);
        RecyclerView recyclerView = findViewById(R.id.feedback);
        indietro = findViewById(R.id.indietro);
        nomeC = findViewById(R.id.nomeAut);
        cognomeC = findViewById(R.id.cognomeAut);
        indietro.setOnClickListener(v -> finish());
        emailC = findViewById(R.id.emailApp);
        telefono = findViewById(R.id.telefono);
        Intent receive = getIntent();
        id = receive.getStringExtra("id");
        stato = null;
        if(receive.getStringExtra("stato") != null){
        stato = receive.getStringExtra("stato");}
        presenter.setCicerone(this,id,stato);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        presenter.initRecyclerViewFeedback(recyclerView,id);
    }

    @Override
    public void setTextNome(String nome) {
        this.nomeC.setText(nome); }

    @Override
    public void setTextCognome(String cognome) {
        this.cognomeC.setText(cognome); }

    @Override
    public void setTextEmail(String email) {
        this.emailC.setText(email); }

    @Override
    public void setTextTelefono(String telefono) {
        this.telefono.setText(telefono); }

    @Override
    public void setImmagine(String foto) {
        Picasso.get().load(foto).into(this.imageCicerone);
    }
}
