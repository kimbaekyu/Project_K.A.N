package com.example.tmap_last1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class BuildingAdapter extends ArrayAdapter<com.example.tmap_last1.Building> {
    public static ArrayList<com.example.tmap_last1.Building> buildingList = new ArrayList<com.example.tmap_last1.Building>();
    ListView listView;

    public BuildingAdapter(Context context, int resource, List<com.example.tmap_last1.Building> buildingList){

        super(context, resource, buildingList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        com.example.tmap_last1.Building building = getItem(position);

        if(convertView == null){

            convertView = LayoutInflater.from(getContext()).inflate(R.layout.building_item, parent, false);
        }

        TextView tv = convertView.findViewById(R.id.building_name);


        tv.setText(building.getName());


        return convertView;
    }
}