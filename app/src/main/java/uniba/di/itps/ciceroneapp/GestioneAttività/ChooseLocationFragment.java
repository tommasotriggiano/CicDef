package uniba.di.itps.ciceroneapp.GestioneAttività;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;
//import com.google.android.gms.location.places.Place;
import com.google.android.libraries.places.compat.AutocompleteFilter;
import com.google.android.libraries.places.compat.Place;
import com.google.android.libraries.places.compat.Places;
import com.google.android.libraries.places.compat.ui.PlaceAutocomplete;
//import com.google.android.gms.location.places.Place;
//import com.google.android.gms.location.places.ui.PlaceAutocomplete;




import uniba.di.itps.ciceroneapp.R;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;
import static android.content.ContentValues.TAG;


public class ChooseLocationFragment extends Fragment  {
    private final int PLACE_AUTOCOMPLETE_REQUEST_CODE = 1;
    private ImageButton goOn;
    private TextView address;
    private TextView date;
    private InterfaceGestioneAttività.Presenter presenter;
    private InterfaceGestioneAttività.MvpView mvpView;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_choose_location, container, false);
        goOn = (ImageButton)view.findViewById(R.id.button3);
        address = (TextView) view.findViewById(R.id.Luogo);
        date = (TextView)view.findViewById(R.id.Date);
        presenter = new PresenterGestioneAttività(getActivity());
        mvpView = (InterfaceGestioneAttività.MvpView)getActivity();
        mvpView.hideBottomNavigation();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //indrizzo
        address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = null;
                try {
                    AutocompleteFilter typeFilter = new AutocompleteFilter.Builder()
                            .setTypeFilter(AutocompleteFilter.TYPE_FILTER_CITIES)
                            .build();
                    intent = new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_OVERLAY).setFilter(typeFilter)
                                        .build(getActivity());
                } catch (GooglePlayServicesRepairableException e) {
                    Log.i(TAG,e.getMessage());
                } catch (GooglePlayServicesNotAvailableException e) {
                    Log.i(TAG,e.getMessage());
                }
                startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE);

            }
        });
        //data
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mvpView.showDialogDate(date);
            }
        });
        //continua
        goOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment f = new ChooseMeetingFragment();
                Bundle receive = getArguments();
                if(address.getText().toString().isEmpty()){
                    address.setError("Inserisci indirizzo");
                    return;
                }
                if(date.getText().toString().isEmpty()){
                    date.setError("Inserisci data");
                    return;
                }
                receive.putString("indirizzo",address.getText().toString());
                receive.putString("data",date.getText().toString());
                if(presenter.setArguument(f,receive))
                    presenter.addFragment(f);
            }
        });
    }


    @Override
    public void onActivityResult(int requestCode,int resultCode,Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if (requestCode == PLACE_AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Place place = PlaceAutocomplete.getPlace(getContext(), data);
                Log.i(TAG, "Place: " + place.getName());
                address.setText(place.getName().toString());
            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(getContext(), data);
                // TODO: Handle the error.
                Log.i(TAG, status.getStatusMessage());

            } else if (resultCode == RESULT_CANCELED) {
                Log.i(TAG, "error");
                // The user canceled the operation.
            }
        }
    }



}
