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
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.compat.AutocompleteFilter;
import com.google.android.libraries.places.compat.Place;
import com.google.android.libraries.places.compat.ui.PlaceAutocomplete;

import java.util.Objects;

import uniba.di.itps.ciceroneapp.R;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;
import static android.content.ContentValues.TAG;


public class ChooseMeetingFragment extends Fragment {
    private static final int PLACE_AUTOCOMPLETE_REQUEST_CODE = 1 ;
    private ImageButton goOn;
    private TextView meetingAddress;
    private Spinner meetingHour;
    private Spinner start;
    private Spinner end;
    private InterfaceGestioneAttività.Presenter presenter;
    private InterfaceGestioneAttività.MvpView mvpView;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_choose_meeting, container, false);
        goOn = (ImageButton) view.findViewById(R.id.button6);
        meetingAddress = (TextView) view.findViewById(R.id.Luogo);
        meetingHour = (Spinner) view.findViewById(R.id.meetingHourSpinner);
        start = (Spinner)view.findViewById(R.id.spinner4);
        end = (Spinner)view.findViewById(R.id.spinner3);
        presenter = new PresenterGestioneAttività(getActivity());
        mvpView = (InterfaceGestioneAttività.MvpView)getActivity();
        mvpView.hideBottomNavigation();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //indirizzo
        meetingAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = null;
                try {
                    AutocompleteFilter typeFilter = new AutocompleteFilter.Builder()
                            .setTypeFilter(AutocompleteFilter.TYPE_FILTER_ADDRESS)
                            .build();
                    intent = new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_OVERLAY).setFilter(typeFilter)
                            .build(getActivity());
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }
                startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE);

            }

        });
        goOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment f = new ChooseLanguageFragment();
                Bundle receive = getArguments();
                if(meetingAddress.getText().toString().isEmpty()){
                    meetingAddress.setError("Inserisci indirizzo di Incontro");
                    return;
                }
                receive.putString("indirizzoIncontro",meetingAddress.getText().toString());
                receive.putString("oraIncontro",meetingHour.getSelectedItem().toString());
                receive.putString("oraInizio",start.getSelectedItem().toString());
                receive.putString("oraFine",end.getSelectedItem().toString());
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
                meetingAddress.setText(place.getName().toString());
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
