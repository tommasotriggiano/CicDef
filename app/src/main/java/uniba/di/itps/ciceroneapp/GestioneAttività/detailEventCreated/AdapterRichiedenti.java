package uniba.di.itps.ciceroneapp.GestioneAttività.detailEventCreated;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import uniba.di.itps.ciceroneapp.GestioneAttività.InterfaceGestioneAttività;
import uniba.di.itps.ciceroneapp.GestioneAttività.PresenterGestioneAttività;
import uniba.di.itps.ciceroneapp.R;
import uniba.di.itps.ciceroneapp.gestioneRichieste.search.ViewHolderAttivitàRicercate;

public class AdapterRichiedenti extends RecyclerView.Adapter<AdapterRichiedenti.Holder> {
    private ArrayList<Map<String,Object>> richiedenti;
    private InterfaceGestioneAttività.Presenter presenter;
    private Context context;

    public AdapterRichiedenti(Context context, ArrayList<Map<String,Object>> richiedenti){
        this.context = context;
        this.richiedenti = richiedenti;
        presenter = new PresenterGestioneAttività(context);

    }
    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_richiedente, null);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        presenter.setHolderRichiedenti(holder,position,richiedenti);
        holder.accetta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.accettaRichiesta(position,richiedenti);
            }
        });
        holder.rifiuta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.rifiutaRichiesta(position,richiedenti);

            }
        });


    }

    @Override
    public int getItemCount() {
        return richiedenti.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        public CircleImageView imageProfile;
        public TextView nome,cognome,email,telefono;
        public Button accetta,rifiuta;

        public Holder(@NonNull View itemView) {
            super(itemView);
            imageProfile = itemView.findViewById(R.id.imageProfile);
            nome = itemView.findViewById(R.id.nomeAut);
            cognome = itemView.findViewById(R.id.cognomeAut);
            email = itemView.findViewById(R.id.emailApp);
            telefono = itemView.findViewById(R.id.telefono);
            accetta = itemView.findViewById(R.id.accetta);
            rifiuta = itemView.findViewById(R.id.rifiuta);
        }

}}
