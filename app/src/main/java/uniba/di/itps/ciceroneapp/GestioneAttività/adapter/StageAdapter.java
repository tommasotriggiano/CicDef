package uniba.di.itps.ciceroneapp.GestioneAttività.adapter;



import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import uniba.di.itps.ciceroneapp.R;
import uniba.di.itps.ciceroneapp.model.Stage;

public class StageAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Stage> stages;

    public StageAdapter(Context context, ArrayList<Stage> stages){
        this.context = context;
        this.stages = stages;
    }

    @Override
    public int getCount() {
        return stages.size();
    }

    @Override
    public Object getItem(int position) {
        return stages.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // inflate the layout for each list row
        if (convertView == null) {
            convertView = LayoutInflater.from(context).
                    inflate(R.layout.layout_tappe_list, parent, false);
        }

        // get current item to be displayed
        Stage currentstage = (Stage) getItem(position);

        // get the TextView for item name and item description
        TextView textViewItemName = convertView.findViewById(R.id.title);
        TextView textViewUpdate= convertView.findViewById(R.id.textViewUpdate);
        TextView textViewDelete= convertView.findViewById(R.id.textViewDelete);
        //sets the text for item name and item description from the current item object
        textViewItemName.setText(currentstage.getIndirizzo());

        // returns the view for the current row
        return convertView;
    }

    }



