package uniba.di.itps.ciceroneapp.searchActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.compat.AutocompleteFilter;
import com.google.android.libraries.places.compat.Place;
import com.google.android.libraries.places.compat.ui.PlaceAutocomplete;

import uniba.di.itps.ciceroneapp.GestioneAttività.InterfaceGestioneAttività;
import uniba.di.itps.ciceroneapp.R;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;
import static android.content.ContentValues.TAG;

public class SearchActivityFragment extends Fragment
{
    private static final int PLACE_AUTOCOMPLETE_REQUEST_CODE = 1 ;
    private TextView searchBox;
    private TextView date;
    private TextView category;
    private InterfaceGestioneAttività.MvpView mvpView;
    private GestioneRichiesteInterfaccia.Presenter presenter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_activity, container, false);
        searchBox = view.findViewById(R.id.searchBox);
        date = view.findViewById(R.id.date);
        category = view.findViewById(R.id.category);
        mvpView = (InterfaceGestioneAttività.MvpView) getActivity();
        presenter = new GestioneRichiestePresenter(getActivity());

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        searchBox.setOnClickListener(v -> {
            Intent intent = null;
            try {
                AutocompleteFilter typeFilter = new AutocompleteFilter.Builder()
                        .setTypeFilter(AutocompleteFilter.TYPE_FILTER_CITIES)
                        .build();
                intent = new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN).setFilter(typeFilter)
                        .build(getActivity());
            } catch (GooglePlayServicesRepairableException e) {
                Log.i(TAG,e.getMessage());
            } catch (GooglePlayServicesNotAvailableException e) {
                Log.i(TAG,e.getMessage());
            }
            startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE);


        });
        date.setOnClickListener(v -> mvpView.showDialogDate(date,false));
        category.setOnClickListener(v -> presenter.showCategories(category));


    }



    @Override
    public void onActivityResult(int requestCode,int resultCode,Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if (requestCode == PLACE_AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Place place = PlaceAutocomplete.getPlace(getContext(), data);
                searchBox.setText(place.getName().toString());
                date.setVisibility(View.VISIBLE);
                category.setVisibility(View.VISIBLE);
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
