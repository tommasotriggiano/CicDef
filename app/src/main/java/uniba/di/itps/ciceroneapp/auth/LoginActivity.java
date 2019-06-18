package uniba.di.itps.ciceroneapp.auth;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import uniba.di.itps.ciceroneapp.R;
import uniba.di.itps.ciceroneapp.main.MainActivity;


/**
 * Visualizza la schermata di Login
 */

public class LoginActivity extends AppCompatActivity implements AuthInterface.MvpView,View.OnClickListener  {

    static final int RC_SIGN_IN=1;
    private AuthInterface.Presenter presenter;

    private EditText inputEmail, inputPassword;
    private TextInputLayout layout_email,layout_password;
    private GoogleApiClient mGoogleApiClient;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mAuth.signOut();

        presenter = new AuthPresenter(this);

        //findView methods
        SignInButton signin = findViewById(R.id.sign_in_button);
        inputEmail = findViewById(R.id.email);
        inputPassword = findViewById(R.id.password);
        layout_password= findViewById(R.id.layout_password) ;
        layout_email= findViewById(R.id.layout_email) ;
        TextView resetPassword = findViewById(R.id.tvForgotPassword);
        Button btnLogin = findViewById(R.id.Reg2);
        ProgressBar progressBar = findViewById(R.id.progressBar2);
        progressBar.setVisibility(View.INVISIBLE);
        Button register = findViewById(R.id.Register);

        //GoogleSignIn
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("275319194457-rrmojsh1ar5cu0bu0ggfqec0id5mur7c.apps.googleusercontent.com")
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(LoginActivity.this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        mGoogleApiClient.connect();

        //OnClickListener

        register.setOnClickListener(this);

        resetPassword.setOnClickListener(this);

        btnLogin.setOnClickListener(this);

        signin.setOnClickListener(this);
    }


    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
            mGoogleApiClient.clearDefaultAccountAndReconnect();
        }
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                presenter.firebaseAuthWithGoogle(account,this);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                // ...
            }
        }
    }

    @Override
    public void startOtherActivity() {
        startActivity(new Intent(this,MainActivity.class));
        this.finish();
    }

    @Override
    public void sendErrorMessage() {
        Toast.makeText(this,getString(uniba.di.itps.ciceroneapp.R.string.Auth_Failed),Toast.LENGTH_SHORT).show();
    }

    @Override
    public void sendSuccessfullMessage() {
        Toast.makeText(this,getString(R.string.AuthSuccess),Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setErrorEmail(String string) {
        this.layout_email.requestFocus();
        this.layout_email.setError(string); }

    @Override
    public void setErrorPassword(String string) {
        this.layout_password.requestFocus();
        this.layout_password.setError(string); }

    @Override
    public void setErrorName(String string) { }

    @Override
    public void setErrorSurname(String string) { }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.Register:
                startActivity(new Intent(LoginActivity.this,RegistrationActivity.class));
                break;
            case R.id.Reg2:
                presenter.signInWithEmailAndPasswordUser(inputEmail.getText().toString().trim(),inputPassword.getText().toString().trim(),this);
                break;
            case R.id.tvForgotPassword:
                startActivity(new Intent(LoginActivity.this,PasswordActivity.class));
                break;

                case R.id.sign_in_button:
                signIn();
                break;
            default:break;
        }

    }
}




