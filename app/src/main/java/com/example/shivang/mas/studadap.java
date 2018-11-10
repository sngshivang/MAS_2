package com.example.shivang.mas;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class studadap extends ArrayAdapter<fieldsinfo> {
    public studadap(Context context, ArrayList<fieldsinfo> users) {
        super(context, 0, users);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        fieldsinfo user = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.listview, parent, false);
        }
        // Lookup view for data population
        TextView tvName =  convertView.findViewById(R.id.stdname);
        TextView tvHome =  convertView.findViewById(R.id.stddig);
        TextView tv3 = convertView.findViewById(R.id.stdper);
        // Populate the data into the template view using the data object
        tvName.setText(user.name);
        tvHome.setText(user.dig);
        tv3.setText(user.inti);
        // Return the completed view to render on screen
        return convertView;
    }
}