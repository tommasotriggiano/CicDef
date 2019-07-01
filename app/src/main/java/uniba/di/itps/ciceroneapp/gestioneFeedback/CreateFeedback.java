package uniba.di.itps.ciceroneapp.gestioneFeedback;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;
import uniba.di.itps.ciceroneapp.R;

public class CreateFeedback extends AppCompatActivity implements FeedbackInterface.MvpView {
    private TextView nomec,cognomec,emailc,telefonoc;
    private CircleImageView imageProfilec;
    private Toolbar indietro;
    private EditText titolo,descrizione;
    private Spinner rating;
    private Button pubblica;
    private FeedbackInterface.Presenter presenter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_feedback);
        nomec = findViewById(R.id.nomeFe);
        indietro = findViewById(R.id.indietro);
        cognomec = findViewById(R.id.cognomeFe);
        emailc = findViewById(R.id.emailFe);
        telefonoc = findViewById(R.id.telefonoFe);
        imageProfilec = findViewById(R.id.imageProfileFe);
        titolo = findViewById(R.id.titoloFe);
        descrizione = findViewById(R.id.feedBackdescription);
        rating = findViewById(R.id.spinner);
        pubblica = findViewById(R.id.pubblica);
        presenter = new GestioneFeedBackPresenter(this);
        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        presenter.setCicerone(this,id,"PASSATO");
        indietro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        pubblica.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.createFeedback(id,titolo.getText().toString(),descrizione.getText().toString(),rating.getSelectedItem().toString());
            }
        });
    }

    @Override
    public void setTextNome(String nome) {
        this.nomec.setText(nome);
    }

    @Override
    public void setTextCognome(String cognome) {
        this.cognomec.setText(cognome);
    }

    @Override
    public void setTextEmail(String email) {
        this.emailc.setText(email);
    }

    @Override
    public void setTextTelefono(String telefono) {
        this.telefonoc.setText(telefono); }

    @Override
    public void setImmagine(String foto) {
        Picasso.get().load(foto).into(this.imageProfilec);

    }
}
