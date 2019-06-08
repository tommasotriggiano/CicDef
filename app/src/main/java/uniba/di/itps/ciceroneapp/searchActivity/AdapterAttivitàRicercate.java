package uniba.di.itps.ciceroneapp.searchActivity;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

public class AdapterAttivitàRicercate extends RecyclerView.Adapter<AdapterAttivitàRicercate.ViewHolderAttivitàRicercate> {
    @NonNull
    @Override
    public ViewHolderAttivitàRicercate onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderAttivitàRicercate viewHolderAttivitàRicercate, int i) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolderAttivitàRicercate extends RecyclerView.ViewHolder {
        public ViewHolderAttivitàRicercate(@NonNull View itemView) {
            super(itemView);
        }
    }
}
