package uniba.di.itps.ciceroneapp.auth;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

/**
 * Definisce l'interfaccia tra la View {@link LoginActivity} e il AuthPresenter {@link Presenter}
 */

public interface AuthInterface {
    interface MvpView{
        void startOtherActivity();
        void sendErrorMessage();
        void sendSuccessfullMessage();
        void setErrorEmail(String string);
        void setErrorPassword(String string);
        void setErrorName(String string);
        void setErrorSurname(String string);

    }
    interface Presenter{
        void firebaseAuthWithGoogle(final GoogleSignInAccount account,MvpView mvpView);
        void signInWithEmailAndPasswordUser(String email, String password,MvpView mvpView);
        void checkIfEmailVerified(MvpView mView);
        void sendPasswordResetEmail(String email, MvpView mvpView);
        void createUser(String name,String surname,String email,String password,MvpView mvpView);
    }
}
