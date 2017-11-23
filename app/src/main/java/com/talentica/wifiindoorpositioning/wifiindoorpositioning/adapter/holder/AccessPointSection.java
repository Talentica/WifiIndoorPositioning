package com.talentica.wifiindoorpositioning.wifiindoorpositioning.adapter.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.talentica.wifiindoorpositioning.wifiindoorpositioning.model.AccessPoint;

import java.util.ArrayList;
import java.util.List;

import io.github.luizgrp.sectionedrecyclerviewadapter.SectionParameters;
import io.github.luizgrp.sectionedrecyclerviewadapter.StatelessSection;

/**
 * Created by suyashg on 26/08/17.
 */

public class AccessPointSection extends StatelessSection {
    private List<AccessPoint> accessPoints = new ArrayList<>();

    public AccessPointSection(SectionParameters sectionParameters) {
        super(sectionParameters);
    }

    @Override
    public int getContentItemsTotal() {
        return accessPoints.size();
    }

    @Override
    public RecyclerView.ViewHolder getItemViewHolder(View view) {
        return new PointViewHolder(view);
    }

    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
        PointViewHolder itemHolder = (PointViewHolder) holder;
        AccessPoint accessPoint = accessPoints.get(position);
        itemHolder.tvIdentifier.setText(accessPoint.getSsid());
        itemHolder.tvIdentifier2.setText(accessPoint.getMac_address());
        itemHolder.tvPointX.setText(String.valueOf(accessPoint.getX()));
        itemHolder.tvPointY.setText(String.valueOf(accessPoint.getY()));
    }

    public List<AccessPoint> getAccessPoints() {
        return accessPoints;
    }

    public void setAccessPoints(List<AccessPoint> accessPoints) {
        this.accessPoints = accessPoints;
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder) {
        super.onBindHeaderViewHolder(holder);
        SectionHeaderViewHolder headerViewHolder = (SectionHeaderViewHolder) holder;
        headerViewHolder.tvTitle.setText("Access Points");
    }

    @Override
    public RecyclerView.ViewHolder getHeaderViewHolder(View view) {
        return new SectionHeaderViewHolder(view);
    }
}
