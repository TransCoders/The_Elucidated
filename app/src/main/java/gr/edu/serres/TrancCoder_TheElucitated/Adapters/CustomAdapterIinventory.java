package gr.edu.serres.TrancCoder_TheElucitated.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import gr.edu.serres.TrancCoder_TheElucitated.R;

/**
 * Created by James Nikolaidis on 12/12/2016.
 */


public class CustomAdapterIinventory extends ArrayAdapter<String> {

    private String[] ImageID ;


    public CustomAdapterIinventory(Context context, ArrayList<String> dialogName, String[] Imageid) throws NullPointerException, IndexOutOfBoundsException {



        super(context, R.layout.inventory_layout, dialogName);

        ImageID = Imageid;


    }

@Override

   public View getView(int position, View convertView, ViewGroup parent) throws NullPointerException{

    LayoutInflater theInflater = LayoutInflater.from(getContext());
    View theView = theInflater.inflate(R.layout.inventory_layout, parent, false);
    String tvShow = getItem(position);
    TextView theTextView = (TextView) theView.findViewById(R.id.Inventory_Text);
    theTextView.setText(tvShow);
    ImageView Image =(ImageView) theView.findViewById(R.id.imageView2);
    Image.setImageResource(Integer.parseInt(ImageID[position]));
    return theView;
   }
}