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
    int checkInOut;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SalesLocation that = (SalesLocation) o;

        if (Double.compare(that.latitude, latitude) != 0) return false;
        if (Double.compare(that.longitude, longitude) != 0) return false;
        if (time != that.time) return false;
        return uid != null ? uid.equals(that.uid) : that.uid == null;

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = uid != null ? uid.hashCode() : 0;
        temp = Double.doubleToLongBits(latitude);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(longitude);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (int) (time ^ (time >>> 32));
        return result;
    }

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

    public int getCheckInOut() {
        return checkInOut;
    }

    public void setCheckInOut(int checkInOut) {
        this.checkInOut = checkInOut;
    }
}
