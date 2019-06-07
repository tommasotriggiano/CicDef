package uniba.di.itps.ciceroneapp.GestioneAttività;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

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
    private InterfaceGestioneAttività.MvpView viewMvp;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_choose_itinerary, container, false);
        add = (Button)view.findViewById(R.id.button9);
        name = (EditText)view.findViewById(R.id.nomeTappa);
        description = (EditText) view.findViewById(R.id.descrizioneTappa);
        viewMvp = (InterfaceGestioneAttività.MvpView) getActivity();
        viewMvp.hideBottomNavigation();
        listViewStages = (ListView) view.findViewById(R.id.listViewStages);
        goOn = (ImageButton)view.findViewById(R.id.button10);
        presenter = new PresenterGestioneAttività(getActivity());
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //add
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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

            }
        });
        //goOn
        goOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment f = new LastInformationFragment();
                Bundle receive = getArguments();
                receive.putSerializable("tappe",stages);
                Toast.makeText(getContext(),"1"+receive.get("tappe").toString(),Toast.LENGTH_LONG).show();
                if(presenter.setArguument(f,receive)){
                    presenter.addFragment(f);
                }}
        });
    }
}
