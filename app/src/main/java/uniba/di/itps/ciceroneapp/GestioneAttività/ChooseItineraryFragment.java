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

import uniba.di.itps.ciceroneapp.R;


public class ChooseItineraryFragment extends Fragment  {
    private ImageButton goOn;
    private InterfaceGestioneAttività.Presenter presenter;
    private InterfaceGestioneAttività.MvpView viewMvp;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_choose_itinerary, container, false);
        viewMvp = (InterfaceGestioneAttività.MvpView) getActivity();
        viewMvp.hideBottomNavigation();
        goOn = (ImageButton)view.findViewById(R.id.button10);
        presenter = new PresenterGestioneAttività(getActivity());
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        goOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment f = new LastInformationFragment();
                presenter.addFragment(f);
            }
        });
    }
}
