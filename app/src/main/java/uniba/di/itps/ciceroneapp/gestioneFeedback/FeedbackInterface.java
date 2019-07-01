package uniba.di.itps.ciceroneapp.gestioneFeedback;

import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Map;

import uniba.di.itps.ciceroneapp.model.Feedback;

public interface FeedbackInterface {
    interface MvpView {
        void setTextNome(String nome);
        void setTextCognome(String cognome);
        void setTextEmail(String email);
        void setTextTelefono(String telefono);
        void setImmagine(String foto);
    }
    interface Presenter{
        void setCicerone(MvpView view,String id,String stato);
        void initRecyclerViewFeedback(RecyclerView recyclerView,String id);
        void setHolderFeedback(FeedbackAdapter.Holder holder, int i, ArrayList<Map<String,Object>> feedback);
        void createFeedback(String idCic,String titolo, String commento, String rating);
    }
}
