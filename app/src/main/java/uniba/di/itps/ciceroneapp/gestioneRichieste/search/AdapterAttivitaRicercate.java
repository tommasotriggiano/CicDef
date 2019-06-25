package uniba.di.itps.ciceroneapp.gestioneRichieste.search;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Map;

import uniba.di.itps.ciceroneapp.R;
import uniba.di.itps.ciceroneapp.model.Event;

public class AdapterAttivitaRicercate extends RecyclerView.Adapter<ViewHolderAttivitàRicercate> {

    //this context we will use to inflate the layout
    private Context mCtx;

    //we are storing all the products in a list
    private ArrayList<Map<String,Object>> eventList;

    private GestioneRichiesteInterfaccia.Presenter presenter;

    public AdapterAttivitaRicercate(Context mCtx, ArrayList<Map<String,Object>> eventList) {
        this.mCtx = mCtx;
        this.eventList = eventList;
        presenter = new GestioneRichiestePresenter(mCtx);
    }
    //getting the context and product list with constructor

    public ViewHolderAttivitàRicercate onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.item_search, null);
        return new ViewHolderAttivitàRicercate(view,mCtx);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderAttivitàRicercate holder, int i) {
        presenter.onBindHolder(holder,i,eventList);
        holder.cardView.setOnClickListener(v -> {
          presenter.sendEventDetail(i,eventList,true);
        });
        /*Event event = eventList.get(i);
        holder.categoria.setText(event.getCategoria());
        holder.titolo.setText(event.getTitolo());
        holder.lingua.setText(event.getLingua());
        holder.prezzo.setText(String.valueOf(event.getPrezzo()));
        holder.numPartecipanti.setText(String.valueOf(event.getnMaxPartecipanti()));
        holder.valuta.setText(event.getValuta());
        if(event.getFoto() != null){
            Picasso.get().load(event.getFoto()).into(holder.image);}*/

    }

    /*@Override
    public void onBindViewHolder(@NonNull ViewHolderAttivitaRicercate holder, int i) {
        Event event = eventList.get(i);
        holder.categoria.setText(event.getCategoria());
        holder.titolo.setText(event.getTitolo());
        holder.lingua.setText(event.getLingua());
        holder.prezzo.setText(String.valueOf(event.getPrezzo()));
        holder.numPartecipanti.setText(String.valueOf(event.getnMaxPartecipanti()));
        holder.valuta.setText(event.getValuta());
        if(event.getFoto() != null){
        Picasso.get().load(event.getFoto()).into(holder.image);}
    }*/


    @Override
    public int getItemCount() {
        return eventList.size();
    }

    /*class ViewHolderAttivitaRicercate extends RecyclerView.ViewHolder implements View.OnClickListener{
        GestioneRichiesteInterfaccia.Presenter presenter;
        TextView titolo,categoria,numPartecipanti,lingua,prezzo,rating,valuta;
        ImageView image;
        CardView cardView;
        /*ViewHolderAttivitaRicercate(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            presenter = new GestioneRichiestePresenter(mCtx);
            cardView = itemView.findViewById(R.id.cardViewSearch);
            titolo = itemView.findViewById(R.id.titolo);
            categoria = itemView.findViewById(R.id.data);
            numPartecipanti = itemView.findViewById(R.id.posti);
            lingua = itemView.findViewById(R.id.lingua);
            prezzo = itemView.findViewById(R.id.textViewPrice);
            rating = itemView.findViewById(R.id.rating);
            image = itemView.findViewById(R.id.immagineA);
            valuta = itemView.findViewById(R.id.valuta);


        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            presenter.sendEventDetail(eventList.get(position));
        }

}*/}
