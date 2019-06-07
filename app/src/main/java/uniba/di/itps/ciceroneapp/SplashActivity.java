package uniba.di.itps.ciceroneapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import uniba.di.itps.ciceroneapp.login.LoginActivity;
import uniba.di.itps.ciceroneapp.main.MainActivity;


public class SplashActivity extends Activity {
    int SPLASH_TIME_OUT = 3000;
    FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        user = FirebaseAuth.getInstance().getCurrentUser();
        new Handler().postDelayed(() -> {
            if(user != null && user.isEmailVerified()) {
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                finish();}
            else{
                Intent i = new Intent(SplashActivity.this,LoginActivity.class);
                startActivity(i);
                finish();}
        }, SPLASH_TIME_OUT);
    }
}
