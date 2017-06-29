package com.howinecafe.saleslocation.data;

import java.io.Serializable;
import java.util.List;

/**
 * Created by JamesHo on 3/28/2017.
 */

public class Company implements Serializable {

    String c_name;
    String e_name;
    String c_address;
    String e_address;
    List<Sales> salesList;
    List<Partner> partnerList;
    Double latitude;
    Double longtitude;

    public String getC_name() {
        return c_name;
    }

    public void setC_name(String c_name) {
        this.c_name = c_name;
    }

    public String getE_name() {
        return e_name;
    }

    public void setE_name(String e_name) {
        this.e_name = e_name;
    }

    public String getC_address() {
        return c_address;
    }

    public void setC_address(String c_address) {
        this.c_address = c_address;
    }

    public String getE_address() {
        return e_address;
    }

    public void setE_address(String e_address) {
        this.e_address = e_address;
    }

    public List<Sales> getSalesList() {
        return salesList;
    }

    public void setSalesList(List<Sales> salesList) {
        this.salesList = salesList;
    }

    public List<Partner> getPartnerList() {
        return partnerList;
    }

    public void setPartnerList(List<Partner> partnerList) {
        this.partnerList = partnerList;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongtitude() {
        return longtitude;
    }

    public void setLongtitude(Double longtitude) {
        this.longtitude = longtitude;
    }
}
