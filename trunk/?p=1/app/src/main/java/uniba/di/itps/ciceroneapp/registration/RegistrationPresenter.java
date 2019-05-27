package uniba.di.itps.ciceroneapp.registration;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import uniba.di.itps.ciceroneapp.R;
import uniba.di.itps.ciceroneapp.login.LoginActivity;
import uniba.di.itps.ciceroneapp.model.User;


public class RegistrationPresenter implements RegistrationInterface {
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    private final Context mContext;

    RegistrationPresenter(Context context, FirebaseAuth auth,FirebaseFirestore database) {
        mContext = context;
        mAuth = auth;
        db = database;
    }


    @Override
    public void createUser(TextInputLayout lname,TextInputLayout lsurname,TextInputLayout lemail,TextInputLayout lpassword,final EditText name, final EditText surname, EditText email, EditText password) {
        String email1 = email.getText().toString().trim();
        String password1 = password.getText().toString().trim();
        final String name1 = name.getText().toString().trim();
        final String surname1 = surname.getText().toString().trim();

        if (email1.isEmpty()) {
            lemail.setError(mContext.getResources().getString(R.string.Toast1));
            lemail.requestFocus();
            return;}
        if(name1.isEmpty()){
            lname.setError(mContext.getResources().getString(R.string.errorName));
            lname.requestFocus();
            return;
        }
        if(surname1.isEmpty()){
            lsurname.setError(mContext.getResources().getString(R.string.errorSurname));
            lsurname.requestFocus();
            return;
        }

        if (password1.isEmpty()) {
            lpassword.setError(mContext.getResources().getString(R.string.Toast2));
            lpassword.requestFocus();
            return;}

        if (password1.length() < 6) {
            lpassword.setError(mContext.getResources().getString(R.string.TstPassShort));
            lpassword.requestFocus();
            return;}

        mAuth.createUserWithEmailAndPassword(email1, password1).addOnCompleteListener((Activity)mContext, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                final FirebaseUser user = mAuth.getInstance().getCurrentUser();
                if (task.isSuccessful()) {
                    User userDatabase = new User(name1,surname1,user.getEmail(),user.getUid());
                    db.collection("utenti").document(user.getUid()).set(userDatabase);

                    user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(mContext, R.string.Emailsent, Toast.LENGTH_SHORT).show();
                                Intent goToLogin = new Intent(mContext,LoginActivity.class);
                                mContext.startActivity(goToLogin);
                                ((Activity)mContext).finish();

                }}});} else {
                    Toast.makeText(mContext, R.string.Failure, Toast.LENGTH_SHORT).show();

                                }
                            }
                        });}






}
