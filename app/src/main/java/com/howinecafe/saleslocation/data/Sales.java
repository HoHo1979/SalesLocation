package com.howinecafe.saleslocation.data;

import java.io.Serializable;
import java.util.List;

/**
 * Created by JamesHo on 3/28/2017.
 */

public class Sales implements Serializable {

    String name;
    String email;
    List<String> locations;

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

    public List<String> getLocations() {
        return locations;
    }

    public void setLocations(List<String> locations) {
        this.locations = locations;
    }
}
