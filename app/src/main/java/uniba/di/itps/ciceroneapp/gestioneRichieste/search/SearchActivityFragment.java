package uniba.di.itps.ciceroneapp.gestioneRichieste.search;

import android.app.AlertDialog;
import android.content.Intent;
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
import android.widget.TextView;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.compat.AutocompleteFilter;
import com.google.android.libraries.places.compat.Place;
import com.google.android.libraries.places.compat.ui.PlaceAutocomplete;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import uniba.di.itps.ciceroneapp.GestioneAttività.InterfaceGestioneAttività;
import uniba.di.itps.ciceroneapp.R;

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
    private RecyclerView recyclerView;
    private TextView category;
    private InterfaceGestioneAttività.MvpView mvpView;
    private GestioneRichiesteInterfaccia.Presenter presenter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_activity, container, false);

        user = FirebaseAuth.getInstance().getCurrentUser();
        db = FirebaseFirestore.getInstance();
        //view del layout
        searchBox = view.findViewById(R.id.searchBox);
        date = view.findViewById(R.id.date);
        category = view.findViewById(R.id.category);
        //Interfacce e Presenter
        mvpView = (InterfaceGestioneAttività.MvpView) getActivity();
        presenter = new GestioneRichiestePresenter(getActivity());
        //recyclerView
        recyclerView = view.findViewById(R.id.attivitàRicercate);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        //arrayList recyclerView


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
        date.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }
            @Override
            public void afterTextChanged(Editable s) {
                presenter.initRecyclerViewCerca(recyclerView,searchBox.getText().toString(),date.getText().toString(),category.getText().toString());
        }});
        category.setOnClickListener(v -> this.showCategories(category));
        category.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                presenter.initRecyclerViewCerca(recyclerView,searchBox.getText().toString(),date.getText().toString(),category.getText().toString());
            }
        });




    }



    @Override
    public void onActivityResult(int requestCode,int resultCode,Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if (requestCode == PLACE_AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Place place = PlaceAutocomplete.getPlace(getContext(), data);
                searchBox.setText(place.getName().toString());

                //mostra filtri
                date.setVisibility(View.VISIBLE);
                category.setVisibility(View.VISIBLE);

                //mostra recyclerView con la risposta della query
                presenter.initRecyclerViewCerca(recyclerView,searchBox.getText().toString(),date.getText().toString(),category.getText().toString());

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
    public void enableButton(Intent receive) {

    }

    @Override
    public void showCategories(TextView category) {
        String[]categories = getContext().getResources().getStringArray(R.array.Categories);
        //Crea il dialog Radio Button
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(getContext().getResources().getString(R.string.category));
        builder.setSingleChoiceItems(R.array.Categories, -1, (dialog, which) -> category.setText(categories[which]));
        builder.setPositiveButton("OK", (dialog, which) -> {});
        builder.setNegativeButton("ANNULLA", (dialog, which) -> {});
        builder.create();
        //mostra il dialog Radio Button
        builder.show();
    }

    @Override
    public void setTextTitolo(String string) {
        //nested
    }

    @Override
    public void setTextCategoria(String string) {
        //nested
    }

    @Override
    public void setTextLuogo(String string) {
        //nested

    }

    @Override
    public void setTextDurata(String string) {
        //nested

    }

    @Override
    public void setTextLingua(String string) {
        //nested

    }

    @Override
    public void setTextData(String string) {
        //nested

    }

    @Override
    public void setTextNomeC(String string) {
        //nested

    }

    @Override
    public void setTextCognomeC(String string) {
        //nested

    }

    @Override
    public void setTextOraInizio(String string) {
        //nested

    }

    @Override
    public void setTextDescrizione(String string) {
        //nested

    }

    @Override
    public void setTextIndirizzo(String string) {
        //nested

    }

    @Override
    public void setTextPrezzo(String prezzo, String valuta) {

    }

    @Override
    public void setImmagineProfilo(String fotoprofilo) {
        //nested

    }

    @Override
    public void setImmagineAttività(String img) {
        //nested

    }

    @Override
    public void setNMaxPartecipanti(String nMaxPartecipanti) {

    }

    @Override
    public void goToGuests() {
        //nested

    }

    @Override
    public void goToEvent() {
        //nested

    }


}
