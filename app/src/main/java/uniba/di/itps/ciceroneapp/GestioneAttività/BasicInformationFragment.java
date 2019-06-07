package uniba.di.itps.ciceroneapp.GestioneAttività;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;


import uniba.di.itps.ciceroneapp.R;



public class BasicInformationFragment extends Fragment {

    public static final int PICK_IMAGE = 1;
    private Uri resultUri;
    private ImageButton goOn;
    private ImageView addImage;
    private EditText title;
    private EditText description;
    private Spinner category;

    private InterfaceGestioneAttività.Presenter presenter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_basic_information, container, false);
        InterfaceGestioneAttività.MvpView viewMvp = (InterfaceGestioneAttività.MvpView) getActivity();
        goOn = view.findViewById(R.id.goOn);
        addImage = view.findViewById(R.id.addImageButton);
        title = view.findViewById(R.id.titleEdit);
        description = view.findViewById(R.id.descriptionEdit);
        category = view.findViewById(R.id.spinnerCategory);
        presenter = new PresenterGestioneAttività(getActivity());
        if (viewMvp != null) {
            viewMvp.hideBottomNavigation();
        }
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //button addPhoto
        addImage.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);

        });
        //button goOn
        goOn.setOnClickListener(v -> {
            Fragment f = new ChooseLocationFragment();
            Bundle b = new Bundle();
            if(resultUri != null){
                b.putString("url",resultUri.toString());}
            if(title.getText().toString().isEmpty()){
                title.requestFocus();
                title.setError("Inserisci titolo");
                return;
            }
            if(description.getText().toString().isEmpty()){
                description.requestFocus();
                description.setError("Inserisci descrizione");
                return;
            }
            b.putString("titolo",title.getText().toString());
            b.putString("descrizione",description.getText().toString());
            b.putString("categoria",category.getSelectedItem().toString());

            if(presenter.setArguument(f,b)){
                presenter.addFragment(f);}});

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == PICK_IMAGE) {
            resultUri = data.getData();
            addImage.setImageURI(resultUri);

        }
    }
}
