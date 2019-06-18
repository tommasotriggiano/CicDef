package uniba.di.itps.ciceroneapp.auth;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import uniba.di.itps.ciceroneapp.R;

public class PasswordActivity extends AppCompatActivity implements View.OnClickListener,AuthInterface.MvpView {
    private EditText emailReset;
    private AuthInterface.Presenter passwordPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);

        emailReset = findViewById(R.id.etPassEmail);
        Button resetPass = findViewById(R.id.ResetPass);
        passwordPresenter = new AuthPresenter(this);

        resetPass.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.ResetPass) {
            passwordPresenter.sendPasswordResetEmail(emailReset.getText().toString().trim(), this);
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

