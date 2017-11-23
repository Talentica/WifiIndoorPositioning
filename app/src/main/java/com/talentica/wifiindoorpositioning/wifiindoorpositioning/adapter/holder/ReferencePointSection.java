package com.talentica.wifiindoorpositioning.wifiindoorpositioning.adapter.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.talentica.wifiindoorpositioning.wifiindoorpositioning.model.ReferencePoint;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.github.luizgrp.sectionedrecyclerviewadapter.SectionParameters;
import io.github.luizgrp.sectionedrecyclerviewadapter.StatelessSection;

/**
 * Created by suyashg on 26/08/17.
 */

public class ReferencePointSection extends StatelessSection {
    private List<ReferencePoint> referencePoints = new ArrayList<>();


    public ReferencePointSection(SectionParameters sectionParameters) {
        super(sectionParameters);
    }

    @Override
    public int getContentItemsTotal() {
        return referencePoints.size();
    }

    @Override
    public RecyclerView.ViewHolder getItemViewHolder(View view) {
        return new PointViewHolder(view);
    }

    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
        PointViewHolder itemHolder = (PointViewHolder) holder;
        ReferencePoint referencePoint = referencePoints.get(position);
        itemHolder.tvIdentifier.setText(referencePoint.getName());
        itemHolder.tvPointX.setText(String.valueOf(referencePoint.getX()));
        itemHolder.tvPointY.setText(String.valueOf(referencePoint.getY()));
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder) {
        super.onBindHeaderViewHolder(holder);
        SectionHeaderViewHolder headerViewHolder = (SectionHeaderViewHolder) holder;
        headerViewHolder.tvTitle.setText("Reference Points");
    }

    @Override
    public RecyclerView.ViewHolder getHeaderViewHolder(View view) {
        return new SectionHeaderViewHolder(view);
    }

    public List<ReferencePoint> getReferencePoints() {
        return referencePoints;
    }

    public void setReferencePoints(List<ReferencePoint> referencePoints) {
        this.referencePoints = referencePoints;
    }
}
