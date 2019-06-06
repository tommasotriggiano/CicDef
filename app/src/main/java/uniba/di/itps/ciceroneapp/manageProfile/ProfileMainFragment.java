package uniba.di.itps.ciceroneapp.manageProfile;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import de.hdodenhof.circleimageview.CircleImageView;
import uniba.di.itps.ciceroneapp.R;
import uniba.di.itps.ciceroneapp.login.LoginActivity;


public class ProfileMainFragment extends Fragment {
    private CircleImageView imageProfile;
    private TextView attività,feedback,valuta,logout;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile_main, container, false);
        imageProfile = (CircleImageView) view.findViewById(R.id.profile);
        attività = view.findViewById(R.id.storico);
        feedback = view.findViewById(R.id.feedback);
        valuta = view.findViewById(R.id.valuta);

        logout = view.findViewById(R.id.logout);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState){
        super.onViewCreated(view,savedInstanceState);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), LoginActivity.class));
                getActivity().finish();

            }
        });









    }}