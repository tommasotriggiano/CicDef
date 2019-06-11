package uniba.di.itps.ciceroneapp.GestioneAttività.gestioneMyEvent;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import uniba.di.itps.ciceroneapp.GestioneAttività.InterfaceGestioneAttività;
import uniba.di.itps.ciceroneapp.R;
import uniba.di.itps.ciceroneapp.model.Event;

public class RecyclerViewMyEventAdapter extends RecyclerView.Adapter<MyEventHolder> {
    Context context;
    ArrayList<Event> events;

    public RecyclerViewMyEventAdapter(Context context,ArrayList<Event> events) {
        this.context = context;
        this.events = events;
    }

    @NonNull
    @Override
    public MyEventHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new MyEventHolder(LayoutInflater.from(viewGroup.getContext()).
                inflate(R.layout.event_created_item,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyEventHolder myEventHolder, int i) {
        Event event = events.get(i);
        myEventHolder.titolo.setText(event.getTitolo());
        myEventHolder.date.setText(event.getDateEvento());
        myEventHolder.nIscritti.setText(String.valueOf(event.getnMaxPartecipanti()));
        if(event.getFoto() != null){
            Picasso.get().load(event.getFoto()).into(myEventHolder.fotoEvento);
        }


    }

    @Override
    public int getItemCount() {
        return events.size();
    }

}
