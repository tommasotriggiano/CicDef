package uniba.di.itps.ciceroneapp.auth;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import uniba.di.itps.ciceroneapp.R;
import uniba.di.itps.ciceroneapp.main.MainActivity;

public class PasswordActivity extends AppCompatActivity implements View.OnClickListener,AuthInterface.MvpView {
    private EditText emailReset;
    private FirebaseAuth user;
    private AuthInterface.Presenter passwordPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);

        emailReset = findViewById(R.id.etPassEmail);
        Button resetPass = findViewById(R.id.ResetPass);
        user = FirebaseAuth.getInstance();
        passwordPresenter = new AuthPresenter(this);

        resetPass.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ResetPass:
                passwordPresenter.sendPasswordResetEmail(emailReset.getText().toString().trim(),this);
                break;
            default:break;
        }
    }

    @Override
    public void startOtherActivity() {
        startActivity(new Intent(PasswordActivity.this, LoginActivity.class));
        this.finish();
    }

    @Override
    public void sendErrorMessage() {
        Toast.makeText(this, R.string.EmailNotRegistered, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void sendSuccessfullMessage() {
        Toast.makeText(this, R.string.Emailsent, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void setErrorEmail(String string) {
        this.emailReset.setError(getResources().getString(R.string.Toast1));
    }

    @Override
    public void setErrorPassword(String string) { }

    @Override
    public void setErrorName(String string) { }

    @Override
    public void setErrorSurname(String string) { }
}

