package uniba.di.itps.ciceroneapp.GestioneAttività.createEventView;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.ArrayList;

import uniba.di.itps.ciceroneapp.GestioneAttività.InterfaceGestioneAttività;
import uniba.di.itps.ciceroneapp.GestioneAttività.PresenterGestioneAttività;
import uniba.di.itps.ciceroneapp.GestioneAttività.adapter.StageAdapter;
import uniba.di.itps.ciceroneapp.R;
import uniba.di.itps.ciceroneapp.model.Stage;


public class ChooseItineraryFragment extends Fragment  {
    private Button add;
    private EditText name,description;
    private ImageButton goOn;
    private ListView listViewStages;
    private ArrayList<Stage> stages = new ArrayList<>();
    private InterfaceGestioneAttività.Presenter presenter;
    private Toolbar toolbar;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_choose_itinerary, container, false);
        add = view.findViewById(R.id.button9);
        name = view.findViewById(R.id.nomeTappa);
        description = view.findViewById(R.id.descrizioneTappa);
        InterfaceGestioneAttività.MvpView viewMvp = (InterfaceGestioneAttività.MvpView) getActivity();
        assert viewMvp != null;
        viewMvp.hideBottomNavigation();
        listViewStages = view.findViewById(R.id.listViewStages);
        goOn = view.findViewById(R.id.button10);
        toolbar = view.findViewById(R.id.toolbar);
        presenter = new PresenterGestioneAttività(getActivity());
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        toolbar.setNavigationOnClickListener(v -> {
            presenter.addFragment(new AddEventMainFragment(), (InterfaceGestioneAttività.MvpView) getActivity());

        });
        //add
        add.setOnClickListener(v -> {
            if(name.getText().toString().isEmpty()){
                name.requestFocus();
                name.setError("Inserisci nome tappa");
                return;
            }
            if(description.getText().toString().isEmpty()){
                description.requestFocus();
                description.setError("Inserisci descrizione tappa");
                return;
            }
            presenter.addStage(stages,name.getText().toString(),description.getText().toString());
            listViewStages.setAdapter(new StageAdapter(getContext(),stages));

        });
        //goOn
        goOn.setOnClickListener(v -> {
            Fragment f = new LastInformationFragment();
            Bundle receive = getArguments();
            receive.putSerializable("tappe",stages);
                if(presenter.setArguument(f,receive)){
                presenter.addFragment(f,(InterfaceGestioneAttività.MvpView) getActivity());
            }});
    }
}
