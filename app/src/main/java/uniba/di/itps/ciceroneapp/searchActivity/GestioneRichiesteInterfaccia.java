package uniba.di.itps.ciceroneapp.searchActivity;

import android.widget.TextView;

public interface GestioneRichiesteInterfaccia {
    interface MvpView{

    }
    interface Presenter{
        void showCategories(TextView category);
    }
}
