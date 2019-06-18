package uniba.di.itps.ciceroneapp.auth;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import uniba.di.itps.ciceroneapp.R;

public class RegistrationActivity extends AppCompatActivity implements View.OnClickListener,AuthInterface.MvpView {

    private TextInputLayout layout_email,layout_password,layout_name,layout_surname;
    private EditText inputEmail,inputPassword,inputName,inputSurname;
    private AuthInterface.Presenter registrationPresenter;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        registrationPresenter = new AuthPresenter(this);

        Button signUp = findViewById(R.id.Reg2);
        inputName = findViewById(R.id.name);
        inputSurname = findViewById(R.id.surname);
        inputEmail = findViewById(R.id.email);
        inputPassword = findViewById(R.id.password);
        layout_name =findViewById(R.id.layout_signup_name);
        layout_surname =findViewById(R.id.layout_signup_surname);
        layout_password=  findViewById(R.id.layout_signup_password) ;
        layout_email= findViewById(R.id.layout_signup_email);

        signUp.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.Reg2) {
            registrationPresenter.createUser(inputName.getText().toString(), inputSurname.getText().toString(), inputEmail.getText().toString().trim(), inputPassword.getText().toString().trim(), this);
        }

    }

    @Override
    public void startOtherActivity() {
        startActivity(new Intent(RegistrationActivity.this,LoginActivity.class));
        this.finish();
    }

    @Override
    public void sendErrorMessage() {
        Toast.makeText(this, R.string.Failure, Toast.LENGTH_SHORT).show(); }

    @Override
    public void sendSuccessfullMessage() {
        Toast.makeText(this, R.string.Emailsent, Toast.LENGTH_SHORT).show(); }

    @Override
    public void setErrorEmail(String string) {
        this.layout_email.setError(getResources().getString(R.string.Toast1));
    }

    @Override
    public void setErrorPassword(String string) {
        this.layout_password.setError(getResources().getString(R.string.Toast2)); }

    @Override
    public void setErrorName(String string) {
        this.layout_name.setError(getResources().getString(R.string.errorName)); }

    @Override
    public void setErrorSurname(String string) {
        this.layout_surname.setError(getResources().getString(R.string.errorSurname));


    }
}









