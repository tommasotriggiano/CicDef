package uniba.di.itps.ciceroneapp.GestioneAttività.createEventView;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import java.util.Calendar;

/**
 * Created by tommaso on 01/06/2019.
 */

public class DatePickerFragment extends DialogFragment {

    DatePickerDialog.OnDateSetListener onDateSet;
    private int year, month, day;

    public DatePickerFragment() {
        //costruttore
    }

    public void setCallBack(DatePickerDialog.OnDateSetListener ondate) {
        onDateSet = ondate;
    }
    @Override
    public void setArguments(Bundle args) {
        super.setArguments(args);
        year = args.getInt("year");
        month = args.getInt("month");
        day = args.getInt("day");
    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        Calendar c= Calendar.getInstance();
        DatePickerDialog datePicker= new DatePickerDialog(getActivity(),onDateSet,year,month,day);
        datePicker.getDatePicker().setMinDate(c.getTimeInMillis());
        return datePicker;
    }




}


