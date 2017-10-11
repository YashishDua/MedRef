package com.example.lenovo.hackbvp;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Lenovo on 10-10-2017.
 */

public class Adapter extends ArrayAdapter<String> {
    ArrayList<String> arrayList;
    public Adapter(@NonNull Context context, @LayoutRes int resource,ArrayList<String> arr) {
        super(context, R.layout.item);
        arrayList = arr;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        // Lookup view for data population
        TextView tv = (TextView) convertView.findViewById(R.id.textview);
        // Populate the data into the template view using the data object
        tv.setText(arrayList.get(position));
        // Return the completed view to render on screen
        return convertView;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }
}
