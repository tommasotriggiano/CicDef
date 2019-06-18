package uniba.di.itps.ciceroneapp.GestioneAttività.createEventView;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toolbar;

import uniba.di.itps.ciceroneapp.GestioneAttività.InterfaceGestioneAttività;
import uniba.di.itps.ciceroneapp.GestioneAttività.PresenterGestioneAttività;
import uniba.di.itps.ciceroneapp.R;

public class AddEventMainFragment extends Fragment {
    private ImageButton goOn;
    InterfaceGestioneAttività.Presenter presenter;
    private InterfaceGestioneAttività.MvpView mvpView;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_add_event_main, container, false);
        presenter = new PresenterGestioneAttività(getActivity());
        goOn = view.findViewById(R.id.imageButton);
        mvpView = (InterfaceGestioneAttività.MvpView)getActivity();
        mvpView.showBottomNavigation();
        return view;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        goOn.setOnClickListener(v -> {
            Fragment f = new BasicInformationFragment();
            presenter.addFragment(f, (InterfaceGestioneAttività.MvpView) getActivity());

        });

    }



}



