package com.howinecafe.saleslocation.data;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by JamesHo on 2017/6/28.
 */

public class SalesLocation implements Serializable {



    String uid;
    double latitude;
    double longitude;
    String mapProvider;
    long time;

    public SalesLocation() {
    }

    public SalesLocation(double latitude, double longitude, String mapProvider, long time) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.mapProvider = mapProvider;
        this.time = time;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getMapProvider() {
        return mapProvider;
    }

    public void setMapProvider(String mapProvider) {
        this.mapProvider = mapProvider;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
