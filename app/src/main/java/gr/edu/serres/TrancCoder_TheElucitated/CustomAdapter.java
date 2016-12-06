package gr.edu.serres.TrancCoder_TheElucitated;


import android.content.Context;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * Created by tasos on 12/11/2016.
 */

public class CustomAdapter extends ArrayAdapter<String> {
///
    public CustomAdapter(Context context, ArrayList<String> dialogName)throws  NullPointerException,IndexOutOfBoundsException {


             super(context,R.layout.adapter_activity,dialogName);
     }


    @Nullable
    @Override
    public String getItem(int position) {
        return super.getItem(position);
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) throws  NullPointerException {

            LayoutInflater theInflater = LayoutInflater.from(getContext());
            View theView = theInflater.inflate(R.layout.adapter_activity, parent, false);
            String tvShow = getItem(position);
            TextView theTextView = (TextView) theView.findViewById(R.id.textView5);
            theTextView.setText(tvShow);
            return theView;
          }

}
