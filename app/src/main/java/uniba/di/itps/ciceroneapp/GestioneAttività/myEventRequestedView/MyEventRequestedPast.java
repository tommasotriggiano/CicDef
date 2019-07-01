package uniba.di.itps.ciceroneapp.GestioneAttività.myEventRequestedView;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import uniba.di.itps.ciceroneapp.R;
import uniba.di.itps.ciceroneapp.gestioneRichieste.search.GestioneRichiesteInterfaccia;
import uniba.di.itps.ciceroneapp.gestioneRichieste.search.GestioneRichiestePresenter;

public class MyEventRequestedPast extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.past_requested,container,false);
        GestioneRichiesteInterfaccia.Presenter presenter = new GestioneRichiestePresenter(getContext());
        RecyclerView recyclerViewR = view.findViewById(R.id.pastrecyclerViewR);
        recyclerViewR.setHasFixedSize(true);
        recyclerViewR.setLayoutManager(new LinearLayoutManager(getContext()));
        String stato = "PASSATO";
        presenter.initRecyclerViewRichieste(recyclerViewR, stato);
        return view;
    }


}
