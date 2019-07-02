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
    private TextView nomefe,cognomefe,emailfe,telefonofe;
    private CircleImageView imageProfilefe;
    private Toolbar indietrofe;
    private EditText titolo,descrizione;
    private Spinner rating;
    private Button pubblica;
    private FeedbackInterface.Presenter presenter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_feedback);
        nomefe = findViewById(R.id.nomeFe);
        indietrofe = findViewById(R.id.indietro);
        cognomefe = findViewById(R.id.cognomeFe);
        emailfe = findViewById(R.id.emailFe);
        telefonofe = findViewById(R.id.telefonoFe);
        imageProfilefe = findViewById(R.id.imageProfileFe);
        titolo = findViewById(R.id.titoloFe);
        descrizione = findViewById(R.id.feedBackdescription);
        rating = findViewById(R.id.spinnerRating);
        pubblica = findViewById(R.id.pubblica);
        presenter = new GestioneFeedBackPresenter(this);
        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        presenter.setCicerone(this, id,"PASSATO");
        indietrofe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        pubblica.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.createFeedback(id,titolo.getText().toString(),descrizione.getText().toString(),rating.getSelectedItem().toString());
                finish();
            }
        });
    }

    @Override
    public void setTextNome(String nome) {
        this.nomefe.setText(nome);
    }

    @Override
    public void setTextCognome(String cognome) {
        this.cognomefe.setText(cognome);
    }

    @Override
    public void setTextEmail(String email) {
        this.emailfe.setText(email);
    }

    @Override
    public void setTextTelefono(String telefono) {
        this.telefonofe.setText(telefono); }

    @Override
    public void setImmagine(String foto) {
        Picasso.get().load(foto).into(this.imageProfilefe);

    }
}
