package uniba.di.itps.ciceroneapp.gestioneFeedback;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Map;

import uniba.di.itps.ciceroneapp.GestioneAttività.InterfaceGestioneAttività;
import uniba.di.itps.ciceroneapp.data.DataFetch;
import uniba.di.itps.ciceroneapp.model.Feedback;
import uniba.di.itps.ciceroneapp.model.User;

public class GestioneFeedBackPresenter implements FeedbackInterface.Presenter {
    private ArrayList<Map<String,Object>> feedbackArraylist;
    private Context context;

    public GestioneFeedBackPresenter(Context context) {
        this.context = context;
        feedbackArraylist = new ArrayList<>();
    }

    @Override
    public void setCicerone(FeedbackInterface.MvpView mvpView,String id,String stato) {
        FirebaseFirestore.getInstance().collection(DataFetch.UTENTI).document(id).get().addOnSuccessListener(documentSnapshot -> {
            User user = documentSnapshot.toObject(User.class);
            if(user.getFotoprofilo() != null){
            mvpView.setImmagine(user.getFotoprofilo());}
            mvpView.setTextCognome(user.getCognome());
            mvpView.setTextNome(user.getNome());
            mvpView.setTextEmail(user.getEmail());
            if(user.getTelefono() != null){
            mvpView.setTextTelefono(user.getTelefono());}});



    }

    @Override
    public void initRecyclerViewFeedback(RecyclerView recyclerView,String id) {
        FirebaseFirestore.getInstance().collection(DataFetch.UTENTI).
                document(id).
                collection(DataFetch.FEEDBACK).get().addOnSuccessListener(queryDocumentSnapshots -> {
                    for(DocumentSnapshot d : queryDocumentSnapshots.getDocuments()){
                        if(d.exists()){
                        Map<String,Object> feedback = d.getData();
                        feedbackArraylist.add(feedback);}

                    }
                    FeedbackAdapter fa = new FeedbackAdapter(context,feedbackArraylist);
                    recyclerView.setAdapter(fa);
                    fa.notifyDataSetChanged();
                });

}

    @Override
    public void setHolderFeedback(FeedbackAdapter.Holder holder, int i,ArrayList<Map<String,Object>> feedback) {
        holder.nome.setText((String)feedback.get(i).get("nome"));
        holder.cognome.setText((String)feedback.get(i).get("cognome"));
        holder.rating.setText(String.valueOf(feedback.get(i).get("rating")));
        holder.titolo.setText((String)feedback.get(i).get("titolo"));
        holder.commento.setText((String)feedback.get(i).get("commento"));
        holder.ratingMax.setText("5");
    }

    @Override
    public void createFeedback(String idCic,String titolo, String commento, String rating) {
        if(titolo.isEmpty() && commento.isEmpty() && rating.isEmpty()){
            Toast.makeText(context,"Campi vuoti",Toast.LENGTH_SHORT).show();
        }
        else{
            Feedback fb = new Feedback(titolo,commento, Integer.valueOf(rating), FirebaseAuth.getInstance().getCurrentUser().getUid());
            if(fb.createFeedbackToDatabase(idCic)){
                Toast.makeText(context,"Successo",Toast.LENGTH_SHORT).show();
            }
        }
    }
}
