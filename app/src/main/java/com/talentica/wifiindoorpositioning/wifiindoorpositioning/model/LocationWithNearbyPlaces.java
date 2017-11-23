package com.talentica.wifiindoorpositioning.wifiindoorpositioning.model;

import java.util.ArrayList;

/**
 * Created by suyashg on 21/09/17.
 */

public class LocationWithNearbyPlaces {

    private String location;
    private ArrayList<LocDistance> places;

    public LocationWithNearbyPlaces(String location, ArrayList<LocDistance> places) {
        this.location = location;
        this.places = places;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public ArrayList<LocDistance> getPlaces() {
        return places;
    }

    public void setPlaces(ArrayList<LocDistance> places) {
        this.places = places;
    }
}
