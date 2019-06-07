package uniba.di.itps.ciceroneapp.main;

import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.app.DatePickerDialog;
import android.widget.TextView;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import uniba.di.itps.ciceroneapp.GestioneAttività.AddEventMainFragment;
import uniba.di.itps.ciceroneapp.GestioneAttività.DatePickerFragment;
import uniba.di.itps.ciceroneapp.GestioneAttività.InterfaceGestioneAttività;
import uniba.di.itps.ciceroneapp.MyEventMainFragment;
import uniba.di.itps.ciceroneapp.manageProfile.ProfileMainFragment;
import uniba.di.itps.ciceroneapp.SearchEventMainFragment;


import uniba.di.itps.ciceroneapp.R;


public abstract class MainActivity extends AppCompatActivity implements InterfaceGestioneAttività.MvpView {
    private BottomNavigationView bottomNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        //I added this if statement to keep the selected fragment when rotating the device
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new SearchEventMainFragment()).commit();
        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            item -> {
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
            };
    @Override
    public void setFragment(Fragment fragment) {
        FragmentManager fm = this.getSupportFragmentManager();
        fm.beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).replace(R.id.fragment_container,
                fragment).commit();
    }

    @Override
    public void hideBottomNavigation() {
        bottomNav.setVisibility(View.GONE);
    }

    @Override
    public void showBottomNavigation() {
        bottomNav.setVisibility(View.VISIBLE);
    }

    @Override
    public void showDialogDate(TextView date,boolean birth) {
        SimpleDateFormat sdf= new SimpleDateFormat("EEEE");
        DatePickerDialog.OnDateSetListener ondate = (view, year, monthOfYear, dayOfMonth) -> {
            Date pick= new Date(year,monthOfYear,dayOfMonth-1);
            int month = Integer.parseInt(String.valueOf(monthOfYear+1));
            int day = Integer.parseInt(String.valueOf(dayOfMonth));
            String dateString;

            if(month<10){
                if(day<10){
                    dateString="0"+ dayOfMonth + "/" + "0"+ (monthOfYear + 1) + "/" + year;
                    date.setText(dateString);
                }
                else{
                    dateString= dayOfMonth + "/" +"0"+ (monthOfYear + 1) + "/" + year;
                    date.setText(dateString);}
            } else if(day<10){
                dateString="0"+ dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                date.setText(dateString);}
            else{
                dateString= dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                date.setText(dateString);}
        };


        DatePickerFragment datePickerFragment = new DatePickerFragment();
        //Set Up Current Date Into dialog
        if(!birth){
        Calendar calender = Calendar.getInstance();
        Bundle args = new Bundle();
        args.putInt("year", calender.get(Calendar.YEAR));
        args.putInt("month", calender.get(Calendar.MONTH));
        args.putInt("day", calender.get(Calendar.DAY_OF_MONTH));
        datePickerFragment.setArguments(args);
        datePickerFragment.setCallBack(ondate);
        datePickerFragment.show(getSupportFragmentManager(),"DatePicker");}
        else{
            datePickerFragment.setCallBack(ondate);
            datePickerFragment.show(getSupportFragmentManager(),"DatePicker");
        }

    }


}
