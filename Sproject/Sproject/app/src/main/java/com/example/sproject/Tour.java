package com.example.sproject;

public class Tour {
    private String tourName;
    private String tourLocation;

    public Tour(String tourName, String tourLocation) {
        this.tourName = tourName;
        this.tourLocation = tourLocation;
    }

    public String getTourName() {
        return tourName;
    }

    public void setTourName(String tourName) {
        this.tourName = tourName;
    }

    public String getTourLocation() {
        return tourLocation;
    }

    public void setTourLocation(String tourLocation) {
        this.tourLocation = tourLocation;
    }
}
