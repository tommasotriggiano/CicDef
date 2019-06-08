package uniba.di.itps.ciceroneapp.searchActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.widget.TextView;

import uniba.di.itps.ciceroneapp.R;

public class GestioneRichiestePresenter implements  GestioneRichiesteInterfaccia.Presenter{

    private Context context;

    GestioneRichiestePresenter(Context context) {
        this.context = context;
    }

    @Override
    public void showCategories(TextView category) {
        String[]categories = context.getResources().getStringArray(R.array.Categories);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(context.getResources().getString(R.string.category));
        builder.setSingleChoiceItems(R.array.Categories, -1, (dialog, which) -> category.setText(categories[which]));
        builder.setPositiveButton("OK", (dialog, which) -> {

        });
        builder.setNegativeButton("ANNULLA", (dialog, which) -> {

        });
        builder.create();
        builder.show();
    }
}
