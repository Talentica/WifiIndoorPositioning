package com.talentica.wifiindoorpositioning.wifiindoorpositioning.adapter;

import android.net.wifi.ScanResult;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.talentica.wifiindoorpositioning.wifiindoorpositioning.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by suyashg on 27/08/17.
 */

public class WifiResultsAdapter extends RecyclerView.Adapter<WifiResultsAdapter.ViewHolder> {
    private List<ScanResult> results = new ArrayList<>();

    @Override
    public WifiResultsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LinearLayout linearLayout = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_wifi_result, parent, false);
        // set the view's size, margins, paddings and layout parameters
        WifiResultsAdapter.ViewHolder vh = new WifiResultsAdapter.ViewHolder(linearLayout);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bssid.setText("MAC: "+results.get(position).BSSID);
        holder.ssid.setText("SSID: "+results.get(position).SSID);
        holder.capabilities.setText("Type: "+results.get(position).capabilities);
        holder.frequency.setText("Frequency: "+String.valueOf(results.get(position).frequency));
        holder.level.setText(String.valueOf("RSSI:"+results.get(position).level));
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView bssid, ssid, capabilities, level, frequency;

        public ViewHolder(LinearLayout v) {
            super(v);
            bssid = v.findViewById(R.id.wifi_bssid);
            ssid = v.findViewById(R.id.wifi_ssid);
            capabilities = v.findViewById(R.id.wifi_capabilities);
            frequency = v.findViewById(R.id.wifi_frequency);
            level = v.findViewById(R.id.wifi_level);
        }
    }

    public List<ScanResult> getResults() {
        return results;
    }

    public void setResults(List<ScanResult> results) {
        this.results = results;
    }
}
