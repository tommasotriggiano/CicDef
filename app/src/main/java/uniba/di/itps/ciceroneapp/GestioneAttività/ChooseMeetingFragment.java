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
import android.widget.TextView;

import uniba.di.itps.ciceroneapp.R;


public class ChooseMeetingFragment extends Fragment {
    private ImageButton goOn;
    private TextView meetingAddress;
    private Spinner meetingHour;
    private Spinner start;
    private Spinner end;
    private InterfaceGestioneAttività.Presenter presenter;
    private InterfaceGestioneAttività.MvpView mvpView;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_choose_meeting, container, false);
        goOn = (ImageButton) view.findViewById(R.id.button6);
        meetingAddress = (TextView) view.findViewById(R.id.Luogo);
        meetingHour = (Spinner) view.findViewById(R.id.meetingHourSpinner);
        start = (Spinner)view.findViewById(R.id.spinner4);
        end = (Spinner)view.findViewById(R.id.spinner3);
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
                Bundle receive = getArguments();
                if(meetingAddress.getText().toString().isEmpty()){
                    meetingAddress.setError("Inserisci indirizzo di Incontro");
                    return;
                }
                receive.putString("indirizzoIncontro",meetingAddress.getText().toString());
                receive.putString("oraIncontro",meetingHour.getSelectedItem().toString());
                receive.putString("oraInizio",start.getSelectedItem().toString());
                receive.putString("oraFine",end.getSelectedItem().toString());
                if(presenter.setArguument(f,receive))
                    presenter.addFragment(f);

            }
        });
    }
}
