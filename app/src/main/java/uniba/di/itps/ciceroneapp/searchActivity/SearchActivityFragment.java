package uniba.di.itps.ciceroneapp.searchActivity;

import android.content.Intent;
import android.databinding.adapters.TextViewBindingAdapter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.compat.AutocompleteFilter;
import com.google.android.libraries.places.compat.Place;
import com.google.android.libraries.places.compat.ui.PlaceAutocomplete;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

import uniba.di.itps.ciceroneapp.GestioneAttività.InterfaceGestioneAttività;
import uniba.di.itps.ciceroneapp.R;
import uniba.di.itps.ciceroneapp.base.mvp.callback.ICallbackListener;
import uniba.di.itps.ciceroneapp.model.Event;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;
import static android.content.ContentValues.TAG;

public class SearchActivityFragment extends Fragment implements GestioneRichiesteInterfaccia.MvpView
{
    private static final int PLACE_AUTOCOMPLETE_REQUEST_CODE = 1 ;
    private TextView searchBox;
    private TextView date;
    private FirebaseUser user;
    private FirebaseFirestore db;
    private ArrayList<Event> events;
    private RecyclerView recyclerView;
    private TextView category;
    private Button filtro;
    private InterfaceGestioneAttività.MvpView mvpView;
    private GestioneRichiesteInterfaccia.MvpView richiestaview;
    private GestioneRichiesteInterfaccia.Presenter presenter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_activity, container, false);
        user = FirebaseAuth.getInstance().getCurrentUser();
        db = FirebaseFirestore.getInstance();
        searchBox = view.findViewById(R.id.searchBox);
        date = view.findViewById(R.id.date);
        filtro = view.findViewById(R.id.filtro);
        category = view.findViewById(R.id.category);
        mvpView = (InterfaceGestioneAttività.MvpView) getActivity();
        presenter = new GestioneRichiestePresenter(getActivity(),user,db);
        richiestaview = new SearchActivityFragment();
        //getting the recyclerview from xml
        recyclerView = view.findViewById(R.id.attivitàRicercate);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        events = new ArrayList<>();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Toast.makeText(getContext(),date.getText().toString(),Toast.LENGTH_LONG).show();
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
        filtro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),searchBox.getText().toString() + " "+date.getText().toString()+" "+category.getText().toString(),Toast.LENGTH_LONG).show();
                //richiestaview.loadQuery();
                presenter.respondToQuery(events, searchBox.getText().toString(), date.getText().toString(), category.getText().toString(), events -> {
                    AdapterAttivitaRicercate adpter = new AdapterAttivitaRicercate(getActivity(),events);
                    recyclerView.setAdapter(adpter);
            });}
        });




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
                //this.loadQuery();
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

    @Override
    public void loadQuery() {
        presenter.respondToQuery(events, searchBox.getText().toString(), date.getText().toString(), category.getText().toString(), events -> {
            AdapterAttivitaRicercate adpter = new AdapterAttivitaRicercate(getActivity(),events);
            recyclerView.setAdapter(adpter);


        });


    }
}
