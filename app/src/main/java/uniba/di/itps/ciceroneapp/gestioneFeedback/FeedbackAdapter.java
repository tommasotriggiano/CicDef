package uniba.di.itps.ciceroneapp.gestioneFeedback;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import uniba.di.itps.ciceroneapp.R;

public class FeedbackAdapter extends RecyclerView.Adapter<FeedbackAdapter.Holder> {
    private Context context;
    private ArrayList<Map<String,Object>> feedback;
    private FeedbackInterface.Presenter presenter;

    public FeedbackAdapter(Context context, ArrayList<Map<String, Object>> feedback) {
        this.context = context;
        this.feedback = feedback;
        presenter = new GestioneFeedBackPresenter(context);
    }

    @NonNull
    @Override
    public FeedbackAdapter.Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_feedback, null);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FeedbackAdapter.Holder holder, int i) {
        presenter.setHolderFeedback(holder,i,feedback);
    }
    @Override
    public int getItemCount() {
        return feedback.size();
    }

    public class Holder extends RecyclerView.ViewHolder{
        public CircleImageView imageGlobetrotter;
        public TextView nome,cognome,titolo,rating,commento,ratingMax;
        public Holder(@NonNull View itemView) {
            super(itemView);
            imageGlobetrotter = itemView.findViewById(R.id.imageProfileF);
            nome = itemView.findViewById(R.id.nomeF);
            cognome = itemView.findViewById(R.id.cognomeF);
            titolo = itemView.findViewById(R.id.titolo);
            commento = itemView.findViewById(R.id.commentoText);
            ratingMax = itemView.findViewById(R.id.textView6);
            rating = itemView.findViewById(R.id.ratingNum);
        }
    }
}
