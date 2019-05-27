package uniba.di.itps.ciceroneapp.main;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import uniba.di.itps.ciceroneapp.GestioneAttività.AddEventMainFragment;
import uniba.di.itps.ciceroneapp.MyEventMainFragment;
import uniba.di.itps.ciceroneapp.manageProfile.ProfileMainFragment;
import uniba.di.itps.ciceroneapp.SearchEventMainFragment;


import uniba.di.itps.ciceroneapp.R;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        //I added this if statement to keep the selected fragment when rotating the device
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new SearchEventMainFragment()).commit();
        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;

                    switch (item.getItemId()) {
                        case R.id.navigation_search:
                            selectedFragment = new SearchEventMainFragment();
                            break;
                        case R.id.navigation_add_event:
                            selectedFragment = new AddEventMainFragment();
                            break;
                        case R.id.navigation_event:
                            selectedFragment = new MyEventMainFragment();
                            break;
                        case R.id.navigation_profile:
                            selectedFragment = new ProfileMainFragment();
                            break;
                        default:break;
                    }

                    if (selectedFragment != null) {
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                                selectedFragment).commit();
                    }

                    return true;
                }
            };
}