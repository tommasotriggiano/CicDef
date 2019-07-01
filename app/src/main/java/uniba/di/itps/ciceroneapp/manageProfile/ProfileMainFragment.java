package uniba.di.itps.ciceroneapp.manageProfile;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import de.hdodenhof.circleimageview.CircleImageView;
import uniba.di.itps.ciceroneapp.GestioneAttività.EventFragment;
import uniba.di.itps.ciceroneapp.GestioneAttività.InterfaceGestioneAttività;
import uniba.di.itps.ciceroneapp.GestioneAttività.myEventCreatedView.MyEventCreatedFragment;
import uniba.di.itps.ciceroneapp.R;
import uniba.di.itps.ciceroneapp.auth.LoginActivity;
import uniba.di.itps.ciceroneapp.gestioneFeedback.ViewMyFeedback;


public class ProfileMainFragment extends Fragment implements View.OnClickListener {
    private CircleImageView imageProfile;
    private InterfacciaGestioneProfilo.Presenter presenter;
    private  InterfaceGestioneAttività.MvpView mvpView;
    private InterfacciaGestioneProfilo.MvpView profileView;
    private TextView attività,feedback,valuta,logout,visualizzaProfilo;
    private FirebaseUser user;
    private FirebaseFirestore db;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile_main, container, false);
        visualizzaProfilo = view.findViewById(R.id.visualizzaProfilo);
        attività = view.findViewById(R.id.storico);
        user = FirebaseAuth.getInstance().getCurrentUser();
        db = FirebaseFirestore.getInstance();
        mvpView = (InterfaceGestioneAttività.MvpView)getActivity();
        profileView = new ViewProfileFragment();
        if (mvpView != null) {
            mvpView.showBottomNavigation();
        }
        imageProfile = view.findViewById(R.id.profile);
        attività = view.findViewById(R.id.storico);
        feedback = view.findViewById(R.id.feedback);
        valuta = view.findViewById(R.id.valuta);
        presenter = new GestioneProfiloPresenter(getActivity(),user,db);
        logout = view.findViewById(R.id.logout);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState){
        super.onViewCreated(view,savedInstanceState);
        profileView.loadPhoto(getContext(),imageProfile,presenter);
        visualizzaProfilo.setOnClickListener(this);
        attività.setOnClickListener(this);
        logout.setOnClickListener(this);
        feedback.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.visualizzaProfilo:
                presenter.addFragment(new ViewProfileFragment());
                break;
            case R.id.storico:
                presenter.goToStorico(new EventFragment(),"PASSATO");
                break;
            case R.id.logout:
                startActivity(new Intent(getActivity(), LoginActivity.class));
                getActivity().finish();
                break;
            case R.id.feedback:
                presenter.goToMyFeedback();

        }

    }
}
