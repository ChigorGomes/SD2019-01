package com.example.mapamundial.Model;

import java.io.Serializable;

public class LatiLong implements Serializable {
    private String capital;
    private Double latitude;
    private Double longitude;

    public LatiLong(String capital, Double latitude, Double longitude) {
        this.capital = capital;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getCapital() {
        return capital;
    }

    public void setCapital(String capital) {
        this.capital = capital;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
}
