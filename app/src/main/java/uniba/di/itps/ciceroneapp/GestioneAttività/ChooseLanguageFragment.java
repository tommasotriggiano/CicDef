package uniba.di.itps.ciceroneapp.GestioneAttività;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;

import uniba.di.itps.ciceroneapp.R;


public class ChooseLanguageFragment extends Fragment {
    private ImageButton goOn;
    private Spinner primaryLanguage;
    private Spinner secondaryLanguage;
    private InterfaceGestioneAttività.Presenter presenter;
    private InterfaceGestioneAttività.MvpView mvpView;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_choose_language, container, false);
        goOn = (ImageButton)view.findViewById(R.id.button7);
        primaryLanguage = (Spinner) view.findViewById(R.id.languages);
        secondaryLanguage=  (Spinner) view.findViewById(R.id.languages2);
        presenter = new PresenterGestioneAttività(getActivity());
        mvpView = (InterfaceGestioneAttività.MvpView)getActivity();
        mvpView.hideBottomNavigation();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        goOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment f = new ChooseItineraryFragment();
                Bundle receive = getArguments();
                receive.putString("linguaPrimaria",primaryLanguage.getSelectedItem().toString());
                if(!(secondaryLanguage.getSelectedItem().toString().isEmpty())){
                    receive.putString("linguaSecondaria",secondaryLanguage.getSelectedItem().toString());}
                if(presenter.setArguument(f,receive))
                    presenter.addFragment(f);

            }
        });
    }
}
