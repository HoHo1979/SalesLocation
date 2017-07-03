package com.howinecafe.saleslocation.data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created by JamesHo on 3/28/2017.
 */

public class Sales implements Serializable {

    String name;
    String email;
    Map<String,Boolean> locations;

    public Sales() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Map<String, Boolean> getLocations() {
        return locations;
    }

    public void setLocations(Map<String, Boolean> locations) {
        this.locations = locations;
    }
}
