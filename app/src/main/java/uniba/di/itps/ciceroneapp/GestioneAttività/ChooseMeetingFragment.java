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

/**
 * Created by tommaso on 27/05/2019.
 */

public class ChooseMeetingFragment extends Fragment {
    private ImageButton goOn;
    private InterfaceGestioneAttività.Presenter presenter;
    private InterfaceGestioneAttività.MvpView mvpView;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_choose_meeting, container, false);
        goOn = (ImageButton) view.findViewById(R.id.button6);
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
                Fragment f = new ChooseLanguageFragment();
                presenter.addFragment(f);

            }
        });
    }
}
