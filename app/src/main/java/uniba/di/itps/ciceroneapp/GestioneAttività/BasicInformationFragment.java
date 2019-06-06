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

import com.squareup.picasso.Picasso;

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
    private InterfaceGestioneAttività.MvpView viewMvp;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_basic_information, container, false);
        viewMvp = (InterfaceGestioneAttività.MvpView) getActivity();
        goOn = (ImageButton) view.findViewById(R.id.goOn);
        addImage = (ImageView)view.findViewById(R.id.addImageButton);
        title = (EditText)view.findViewById(R.id.titleEdit);
        description = (EditText)view.findViewById(R.id.descriptionEdit);
        category = (Spinner)view.findViewById(R.id.spinnerCategory);
        presenter = new PresenterGestioneAttività(getActivity());
        viewMvp.hideBottomNavigation();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //button addPhoto
        addImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);

            }
        });
        //button goOn
        goOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                    presenter.addFragment(f);}}

        });

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == PICK_IMAGE) {
            resultUri = data.getData();
            Toast.makeText(getContext(),resultUri.toString(),Toast.LENGTH_LONG).show();
            addImage.setImageURI(resultUri);

        }
    }
}
