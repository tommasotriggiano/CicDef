package uniba.di.itps.ciceroneapp.gestioneRichieste.search.DetailEventRequested;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Map;

import uniba.di.itps.ciceroneapp.R;
import uniba.di.itps.ciceroneapp.model.Guest;

public class GuestAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Guest> guests;

    public GuestAdapter(Context context, ArrayList<Guest> guests){
        this.context = context;
        this.guests = guests;
    }
    @Override
    public int getCount() {
        return guests.size();
    }

    @Override
    public Object getItem(int position) {
        return guests.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).
                    inflate(R.layout.guest_list, parent, false);
        }

        // get current item to be displayed
        Guest currentguest = (Guest) getItem(position);

        // get the TextView for item name and item description
        TextView textViewItemName = convertView.findViewById(R.id.nameItem);
        TextView textViewItemSurname = convertView.findViewById(R.id.surnameItem);

        //sets the text for item name and item description from the current item object
        textViewItemName.setText(currentguest.getNome());
        textViewItemSurname.setText(currentguest.getCognome());

        // returns the view for the current row
        return convertView;
    }
}
