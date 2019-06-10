package uniba.di.itps.ciceroneapp.GestioneAttività.gestioneMyEvent;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import uniba.di.itps.ciceroneapp.GestioneAttività.InterfaceGestioneAttività;
import uniba.di.itps.ciceroneapp.R;

public class RecyclerViewMyEventAdapter extends RecyclerView.Adapter<MyEventHolder> {
    InterfaceGestioneAttività.Presenter presenter;

    public RecyclerViewMyEventAdapter(InterfaceGestioneAttività.Presenter presenter) {
        this.presenter = presenter;
    }

    @NonNull
    @Override
    public MyEventHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new MyEventHolder(LayoutInflater.from(viewGroup.getContext()).
                inflate(R.layout.event_created_item,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyEventHolder myEventHolder, int i) {
        presenter.onBindEventiRowsViewAtPosition(i,myEventHolder);

    }

    @Override
    public int getItemCount() {
        return presenter.getEventiRowsCount();
    }

}
