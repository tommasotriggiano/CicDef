package uniba.di.itps.ciceroneapp.GestioneAttività.myEventCreatedView;

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

public  class MyEventHolder extends RecyclerView.ViewHolder  {

    LinearLayout root;
    ImageView fotoEvento;
    TextView titolo;
    TextView cart_badge;
    TextView date;
    TextView nIscritti;


    public MyEventHolder(@NonNull View itemView) {
        super(itemView);
        fotoEvento = itemView.findViewById(R.id.immagineA);
        titolo = itemView.findViewById(R.id.titolo);
        date = itemView.findViewById(R.id.data);
        nIscritti = itemView.findViewById(R.id.posti);
    }
}
