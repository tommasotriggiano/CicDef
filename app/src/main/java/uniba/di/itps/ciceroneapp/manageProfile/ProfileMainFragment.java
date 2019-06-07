package uniba.di.itps.ciceroneapp.manageProfile;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import de.hdodenhof.circleimageview.CircleImageView;
import uniba.di.itps.ciceroneapp.GestioneAttività.InterfaceGestioneAttività;
import uniba.di.itps.ciceroneapp.R;
import uniba.di.itps.ciceroneapp.login.LoginActivity;


public class ProfileMainFragment extends Fragment {
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
        visualizzaProfilo.setOnClickListener(v -> {
            presenter.addFragment(new ViewProfileFragment());

        });
        logout.setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), LoginActivity.class));
            getActivity().finish();

        });
    }




    }
