package com.howinecafe.saleslocation;

import android.content.Context;
import android.location.Location;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.howinecafe.saleslocation.data.SalesLocation;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by JamesHo on 2017/6/27.
 */

public class LocationsViewAdapter extends RecyclerView.Adapter<LocationsViewAdapter.LocationsViewHolder> {

    List<SalesLocation> locationList;

    public LocationsViewAdapter(List<SalesLocation> locationList) {
        this.locationList = locationList;
    }

    LocationsViewHolder viewHolder;

    @Override
    public LocationsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view=LayoutInflater.from(context).inflate(R.layout.row_locations,parent,false);
        viewHolder = new LocationsViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(LocationsViewHolder holder, int position) {

        SalesLocation location=locationList.get(position);
        holder.latitudeView.setText(String.valueOf(location.getLatitude()));
        holder.longitudeView.setText(String.valueOf(location.getLongitude()));
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd hh:mm");
        holder.dateView.setText(simpleDateFormat.format(new Date(location.getTime())).toString());
    }

    @Override
    public int getItemCount() {
        return locationList.size();
    }


    class LocationsViewHolder extends RecyclerView.ViewHolder{

        TextView latitudeView;
        TextView longitudeView;
        TextView dateView;

        public LocationsViewHolder(View itemView) {
            super(itemView);

            latitudeView = (TextView) itemView.findViewById(R.id.tv_latitude);
            longitudeView = (TextView) itemView.findViewById(R.id.tv_longitude);
            dateView = (TextView) itemView.findViewById(R.id.tv_date);

        }
    }

}
