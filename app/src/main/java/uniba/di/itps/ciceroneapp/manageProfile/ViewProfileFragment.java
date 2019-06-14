package uniba.di.itps.ciceroneapp.manageProfile;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.Calendar;

import de.hdodenhof.circleimageview.CircleImageView;
import uniba.di.itps.ciceroneapp.GestioneAttività.InterfaceGestioneAttività;
import uniba.di.itps.ciceroneapp.R;
import uniba.di.itps.ciceroneapp.model.User;

import static uniba.di.itps.ciceroneapp.GestioneAttività.createEvent.BasicInformationFragment.PICK_IMAGE;

public class ViewProfileFragment extends Fragment implements InterfacciaGestioneProfilo.MvpView {
    private Toolbar toolbar;
    private FirebaseUser user;
    private FirebaseFirestore db;
    private InterfacciaGestioneProfilo.Presenter presenter;
    private TextView nome,cognome,sesso,dataNascita;
    private ImageView b_nome,b_cognome,b_sesso,b_dataNascita,addPhoto;
    private EditText e_nome,e_cognome;
    private CardView c_nome;
    private CardView c_cognome;
    private CardView c_sesso;
    private CircleImageView profileImage;
    private Spinner gender;
    private Button modifica;
    private String nomeS,cognomeS,dataS,sessoS,fotoS;

    InterfaceGestioneAttività.MvpView mvpView;
    private static final String TAG = "ViewProfileFragment";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.viewprofile, container, false);
        toolbar = view.findViewById(R.id.toolbar);

        user = FirebaseAuth.getInstance().getCurrentUser();
        db = FirebaseFirestore.getInstance();
        presenter = new GestioneProfiloPresenter(getActivity(),user,db);
        //TextView
        nome = view.findViewById(R.id.text_name);
        cognome = view.findViewById(R.id.text_surname);
        dataNascita = view.findViewById(R.id.text_birth);
        sesso = view.findViewById(R.id.text_gender);
        profileImage = view.findViewById(R.id.profile);
        //bottoni per la modifica
        b_nome = view.findViewById(R.id.img_edit_name);
        b_cognome = view.findViewById(R.id.img_edit_surname);
        b_sesso = view.findViewById(R.id.img_edit_gender);
        b_dataNascita = view.findViewById(R.id.img_edit_birth);
        //addPhoto
        addPhoto = view.findViewById(R.id.addPhoto);
        //salva e modifica
        modifica = view.findViewById(R.id.modifica);
        //EditText
        e_nome = view.findViewById(R.id.edit_name);
        e_cognome = view.findViewById(R.id.edit_surname);
        gender = view.findViewById(R.id.edit_gender);
        //cardView
        c_nome = view.findViewById(R.id.card_name);
        c_cognome = view.findViewById(R.id.card_surname);
        c_sesso = view.findViewById(R.id.card_gender);

        mvpView = (InterfaceGestioneAttività.MvpView) getActivity();
        if (mvpView != null) {
            mvpView.hideBottomNavigation();
        }

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        toolbar.setNavigationOnClickListener(v -> {
            presenter.addFragment(new ProfileMainFragment());
        });
        b_nome.setOnClickListener(v -> {
            c_nome.setVisibility(View.GONE);
            e_nome.setVisibility(View.VISIBLE);
            e_nome.setText(nome.getText());
            e_nome.requestFocus();
        });
        b_cognome.setOnClickListener(v -> {
            c_cognome.setVisibility(View.GONE);
            e_cognome.setVisibility(View.VISIBLE);
            e_cognome.setText(cognome.getText());
            e_cognome.requestFocus();

        });
        b_sesso.setOnClickListener(v -> {
            c_sesso.setVisibility(View.GONE);
            gender.setVisibility(View.VISIBLE);


        });
        b_dataNascita.setOnClickListener(v -> {
            this.showBirthDate();
        });
        addPhoto.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);

        });
        modifica.setOnClickListener(v -> {
            if(e_nome.getText().toString().isEmpty()){
                e_nome.setError("Inserisci Nome");
                nomeS = nome.getText().toString();
            }else{nomeS = e_nome.getText().toString();}

            if(e_cognome.getText().toString().isEmpty()){
                e_cognome.setError("Inserisci Cognome");
                cognomeS = cognome.getText().toString();
            }else{cognomeS = e_cognome.getText().toString();}

           presenter.salvaCambiamenti(fotoS,nomeS,cognomeS,dataNascita.getText().toString(),gender.getSelectedItem().toString());
           mvpView.setFragment(new ProfileMainFragment());

        });

        this.loadProfile();
    }


    @Override
    public void loadProfile() {
        presenter.readDataProfile(object -> {
            nome.setText(((User)object).getNome());
            cognome.setText(((User)object).getCognome());
            if(((User)object).getFotoprofilo() != null){
                fotoS = ((User)object).getFotoprofilo();
                Picasso.get().load(((User)object).getFotoprofilo()).into(profileImage);
            }
            if(((User)object).getSesso() != null){
                sesso.setText(((User)object).getSesso());
            }
            else{
                sesso.setText(getActivity().getResources().getString(R.string.notEntered));
            }
            if(((User)object).getDatanascita() != null){
                dataNascita.setText(((User)object).getDatanascita());
            }
            else{
                dataNascita.setText(getActivity().getResources().getString(R.string.notEntered));

            }

        });

    }

    @Override
    public void loadPhoto(Context context, CircleImageView profile, InterfacciaGestioneProfilo.Presenter presenter) {

        presenter.readDataProfile(object -> {
       if(((User)object).getFotoprofilo() != null){

           Picasso.get().load(((User)object).getFotoprofilo()).into(profile);
       }
        });
    }

    @Override
    public void showBirthDate() {
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        int yearUpdate;
        int monthUpdate;
        int dayUpdate;
        String data = dataNascita.getText().toString();
        String[] dateParts = data.split("/");


        DatePickerDialog.OnDateSetListener mDateSetListner = (view, year1, month1, dayOfMonth) -> {
            int day1 = Integer.parseInt(String.valueOf(dayOfMonth));
            String dateString;

            if(month1 <10){
                if(day1 <10){
                    dateString="0"+ dayOfMonth + "/" + "0"+ (month1 + 1) + "/" + year1;
                    dataNascita.setText(dateString);
                }
                else{
                    dateString= dayOfMonth + "/" +"0"+ (month1 + 1) + "/" + year1;
                    dataNascita.setText(dateString);}
            } else if(day1 <10){
                dateString="0"+ dayOfMonth + "/" + (month1 + 1) + "/" + year1;
                dataNascita.setText(dateString);}
            else{
                dateString= dayOfMonth + "/" + (month1 + 1) + "/" + year1;
                dataNascita.setText(dateString);}
        };
        DatePickerDialog dialog= new DatePickerDialog(getActivity(),R.style.MyDialogTheme,mDateSetListner,year,month,day);
        if (!(data.equalsIgnoreCase(getActivity().getResources().getString(R.string.notEntered)))){
            yearUpdate = Integer.parseInt(dateParts[2]);
            monthUpdate = Integer.parseInt(dateParts[1]);
            dayUpdate = Integer.parseInt(dateParts[0]);
        dialog.updateDate(yearUpdate,monthUpdate-1,dayUpdate);}
        dialog.getDatePicker().setMaxDate(c.getTimeInMillis());
        dialog.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == PICK_IMAGE) {
            Uri resultUri = data.getData();
            profileImage.setImageURI(resultUri);
            if (resultUri != null) {
                fotoS = resultUri.toString();
            }

        }
    }
}

