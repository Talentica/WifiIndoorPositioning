package com.talentica.wifiindoorpositioning.wifiindoorpositioning.model;

import java.util.Date;
import java.util.UUID;

import io.realm.RealmList;
import io.realm.RealmModel;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by suyashg on 25/08/17.
 */

public class ReferencePoint extends RealmObject {

    @PrimaryKey
    private String id = UUID.randomUUID().toString();
    private Date createdAt = new Date();
    private String name;
    private String description;
    private double x;
    private double y;
//    Important: must set it as: x y (space in between)
    private String locId;
//    Important: These readings list count must be equal to the number of APS in area.
//    If some AP is not accesible at this RP then put the least RSS value i.e. NaN in Algorithms.java
    private RealmList<AccessPoint> readings;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public String getLocId() {
        return locId;
    }

    public void setLocId(String locId) {
        this.locId = locId;
    }

    public RealmList<AccessPoint> getReadings() {
        return readings;
    }

    public void setReadings(RealmList<AccessPoint> readings) {
        this.readings = readings;
    }
}
