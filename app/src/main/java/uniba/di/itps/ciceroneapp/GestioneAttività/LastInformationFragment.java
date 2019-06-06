package uniba.di.itps.ciceroneapp.GestioneAttività;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import uniba.di.itps.ciceroneapp.MyEventMainFragment;
import uniba.di.itps.ciceroneapp.R;
import uniba.di.itps.ciceroneapp.main.MainActivity;


public class LastInformationFragment extends Fragment {

    private EditText num,priceText;
    private int numPartecipants;
    private EditText requirements;
    private double price;
    private Spinner valute;
    FirebaseFirestore db;
    FirebaseUser user;
    private Button save;
    private InterfaceGestioneAttività.MvpView mvpView;
    private  InterfaceGestioneAttività.Presenter presenter;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.last_information_fragment, container, false);
        db = FirebaseFirestore.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        mvpView = (InterfaceGestioneAttività.MvpView)getActivity();
        num = (EditText) view.findViewById(R.id.numPart);

        priceText = (EditText) view.findViewById(R.id.prezzoEdit);
        requirements = (EditText)view.findViewById(R.id.requisitiEdit);
        valute = (Spinner) view.findViewById(R.id.valuta);
        save = (Button)view.findViewById(R.id.button8);
        mvpView.hideBottomNavigation();
        presenter = new PresenterGestioneAttività(getActivity(),db,user);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //save
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment f = new MyEventMainFragment();
                Bundle receive = getArguments();
                if(num.getText().toString().isEmpty()){
                    num.requestFocus();
                    num.setError("Inserisci numero partecipanti");
                    return;
                }
                if(priceText.getText().toString().isEmpty()){
                    num.requestFocus();
                    num.setError("Inserisci un prezzo a persona");
                }
                receive.putInt("numPartecipanti",Integer.parseInt(num.getText().toString()));
                if(!(requirements.getText().toString().isEmpty())){
                receive.putString("requirementsPartecipanti",requirements.getText().toString());}
                receive.putDouble("price",Double.parseDouble(priceText.getText().toString()));
                receive.putString("valutes",valute.getSelectedItem().toString());
                presenter.createEvent(receive);
                //getActivity().startActivity(new Intent(getContext(),MainActivity.class));

            }
        });

    }
}

