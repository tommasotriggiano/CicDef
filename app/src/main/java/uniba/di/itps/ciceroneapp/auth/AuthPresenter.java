package uniba.di.itps.ciceroneapp.auth;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import uniba.di.itps.ciceroneapp.R;
import uniba.di.itps.ciceroneapp.model.User;


/**
 * Responsabile per la gestione delle azioni dalla View
 */

public class AuthPresenter implements AuthInterface.Presenter {

    private Context mContext;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();


    AuthPresenter(Context context){
        mContext = context;
    }

    @Override
    public void firebaseAuthWithGoogle(final GoogleSignInAccount account,AuthInterface.MvpView mView) {
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener((Activity)mContext, task -> {
                    if (task.isSuccessful()) {
                        String uid = mAuth.getCurrentUser().getUid();
                        String email = mAuth.getCurrentUser().getEmail();
                        String name = account.getGivenName();
                        String surname = account.getFamilyName();
                        String photoUrl = account.getPhotoUrl().toString();
                        User user = new User(name,surname,email,uid);
                        user.setFotoprofilo(photoUrl);

                        user.createUsertoDatabase();
                        mView.sendSuccessfullMessage();
                        mView.startOtherActivity();
                    } else {
                        mView.sendErrorMessage();
                    }
                });
    }

    @Override
    public void signInWithEmailAndPasswordUser(String email, String password,AuthInterface.MvpView mView) {
        if (email.isEmpty()) {
            mView.setErrorEmail(mContext.getResources().getString(R.string.Toast1));
            return;}
        if (password.isEmpty()) {
            mView.setErrorPassword(mContext.getResources().getString(R.string.Toast2));
            return;}

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener((Activity)mContext, task -> {
            if (!task.isSuccessful()) {
                mView.sendErrorMessage();
            }
            else{
                checkIfEmailVerified(mView);}
            });
    }

    @Override
    public void checkIfEmailVerified(AuthInterface.MvpView mView){

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null && user.isEmailVerified()) {
                    mView.sendSuccessfullMessage();
                    mView.startOtherActivity();
            } else{
                mView.sendErrorMessage();}
    }

    @Override
    public void sendPasswordResetEmail(String email,AuthInterface.MvpView mvpView) {
        if (email.isEmpty()) {
            mvpView.setErrorEmail(mContext.getResources().getString(R.string.Toast1));
            return; }
        else{
            mAuth.sendPasswordResetEmail(email).addOnCompleteListener(task -> {
                if (task.isSuccessful()){
                    mvpView.sendSuccessfullMessage();
                    mvpView.startOtherActivity(); }
                else{
                    mvpView.sendErrorMessage(); }
            });
        }
    }

    @Override
    public void createUser(String name, String surname, String email, String password,AuthInterface.MvpView mvpView) {

        if(name.isEmpty()){
            mvpView.setErrorName(mContext.getResources().getString(R.string.errorName));
            return;
        }
        if(surname.isEmpty()){
            mvpView.setErrorSurname(mContext.getResources().getString(R.string.errorSurname));
            return;
        }
        if (email.isEmpty()) {
            mvpView.setErrorEmail(mContext.getResources().getString(R.string.Toast1));
            return;}

        if (password.isEmpty()) {
            mvpView.setErrorPassword(mContext.getResources().getString(R.string.Toast2));
            return;}
        if (password.length() < 6) {
            mvpView.setErrorPassword(mContext.getResources().getString(R.string.TstPassShort));
            return;}

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener((Activity)mContext, task -> {
            final FirebaseUser user = mAuth.getCurrentUser();
            if (task.isSuccessful()) {
                User userDatabase = new User(name,surname,user.getEmail(),user.getUid());
                userDatabase.createUsertoDatabase();
                user.sendEmailVerification().addOnCompleteListener(task1 -> {
                    if(task1.isSuccessful()){
                        mvpView.sendSuccessfullMessage();
                        mvpView.startOtherActivity();
                        ((Activity)mContext).finish();
                    }});} else {
                mvpView.sendErrorMessage();

            }
        });}


    }



