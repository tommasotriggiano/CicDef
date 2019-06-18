package uniba.di.itps.ciceroneapp.GestioneAttività.myEventCreatedView;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Map;

import uniba.di.itps.ciceroneapp.GestioneAttività.InterfaceGestioneAttività;
import uniba.di.itps.ciceroneapp.GestioneAttività.PresenterGestioneAttività;
import uniba.di.itps.ciceroneapp.R;

public class RecyclerViewMyEventAdapter extends RecyclerView.Adapter<MyEventHolder> {
    private Context context;
    private ArrayList<Map<String,Object>> events;
    private InterfaceGestioneAttività.Presenter presenter;

    public RecyclerViewMyEventAdapter(Context context,ArrayList<Map<String,Object>> events) {
        this.context = context;
        this.events = events;
        presenter = new PresenterGestioneAttività(context);
    }

    @NonNull
    @Override
    public MyEventHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new MyEventHolder(LayoutInflater.from(viewGroup.getContext()).
                inflate(R.layout.event_created_item,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyEventHolder myEventHolder, int i) {
        presenter.onBindHolder(myEventHolder,i,events);
        myEventHolder.root.setOnClickListener(v -> presenter.sendEventDetail(i,events));


    }

    @Override
    public int getItemCount() {
        return events.size();
    }

}
