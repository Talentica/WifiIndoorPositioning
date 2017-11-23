package com.talentica.wifiindoorpositioning.wifiindoorpositioning.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.talentica.wifiindoorpositioning.wifiindoorpositioning.R;
import com.talentica.wifiindoorpositioning.wifiindoorpositioning.model.AccessPoint;
import com.talentica.wifiindoorpositioning.wifiindoorpositioning.model.LocDistance;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by suyashg on 27/08/17.
 */

public class NearbyReadingsAdapter extends RecyclerView.Adapter<NearbyReadingsAdapter.ViewHolder> {
    private ArrayList<LocDistance> readings = new ArrayList<>();

    @Override
    public NearbyReadingsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LinearLayout linearLayout = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_reference_reading, parent, false);
        // set the view's size, margins, paddings and layout parameters
        NearbyReadingsAdapter.ViewHolder vh = new NearbyReadingsAdapter.ViewHolder(linearLayout);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.name.setText(readings.get(position).getName());
        holder.loc.setText(readings.get(position).getLocation());
        holder.distance.setText(String.valueOf(readings.get(position).getDistance()));
    }

    @Override
    public int getItemCount() {
        return readings.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView name, loc, distance;

        public ViewHolder(LinearLayout v) {
            super(v);
            name = v.findViewById(R.id.wifi_ssid);
            loc = v.findViewById(R.id.wifi_bssid);
            distance = v.findViewById(R.id.wifi_level);
        }
    }

    public List<LocDistance> getReadings() {
        return readings;
    }

    public void addAP(LocDistance locDistance) {
        readings.add(locDistance);
    }

    public void setReadings(ArrayList<LocDistance> readings) {
        this.readings = readings;
    }
}
