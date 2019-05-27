package uniba.di.itps.ciceroneapp.resetPass;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;

import uniba.di.itps.ciceroneapp.R;

public class PasswordActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText emailReset;
    private FirebaseAuth user;
    private PasswordInterface passwordPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);

        emailReset = findViewById(R.id.etPassEmail);
        Button resetPass = findViewById(R.id.ResetPass);
        user = FirebaseAuth.getInstance();
        passwordPresenter = new PasswordPresenter(this, user);

        resetPass.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ResetPass:
                passwordPresenter.sendPasswordResetEmail(emailReset);
                break;
            default:break;
        }
    }
}

