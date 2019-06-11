package uniba.di.itps.ciceroneapp.GestioneAttività.gestioneMyEvent;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import uniba.di.itps.ciceroneapp.GestioneAttività.InterfaceGestioneAttività;
import uniba.di.itps.ciceroneapp.GestioneAttività.InterfaceGestioneAttività.MvpView;
import uniba.di.itps.ciceroneapp.R;

public  class MyEventHolder extends RecyclerView.ViewHolder implements InterfaceGestioneAttività.MvpView {

    LinearLayout root;
    ImageView fotoEvento;
    TextView titolo;
    TextView cart_badge;
    TextView date;
    TextView nIscritti;


    public MyEventHolder(@NonNull View itemView) {
        super(itemView);
        fotoEvento = itemView.findViewById(R.id.picEventIW);
        titolo = itemView.findViewById(R.id.titleTW);
        date = itemView.findViewById(R.id.dateTW);
        nIscritti = itemView.findViewById(R.id.subsAvaibleTW);
    }



    public void setFotoEvento(String string) {
        fotoEvento.setImageURI(Uri.parse(string));
    }

    public void setTitolo(String string) {
        this.titolo.setText(string);
    }

    public void setCart_badge(String string) {
        this.cart_badge.setText(string);
    }

    public void setDate(String string) {
        this.date.setText(string);
    }

    public void setnIscritti(String string) {
        this.nIscritti.setText(string);
    }

    @Override
    public void showDialogDate(TextView date, boolean birth) {

    }


    @Override
    public void setFragment(Fragment fragment) {

    }

    @Override
    public void hideBottomNavigation() {

    }

    @Override
    public void showBottomNavigation() {

    }

    @Override
    public void showDialogDate(TextView date) {

    }
}
