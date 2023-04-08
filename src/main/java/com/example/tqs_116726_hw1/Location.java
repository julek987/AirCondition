package com.example.tqs_116726_hw1;

public class Location {
    private String locationName;

    private String PM10 = "0.00";
    private String CO = "0.00";
    private String NO2 = "0.00";
    private String O3 = "0.00";
    private String SO2 = "0.00";

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getPM10() {
        return PM10;
    }

    public void setPM10(String PM10) {
        this.PM10 = PM10;
    }

    public String getCO() {
        return CO;
    }

    public void setCO(String CO) {
        this.CO = CO;
    }

    public String getNO2() {
        return NO2;
    }

    public void setNO2(String NO2) {
        this.NO2 = NO2;
    }

    public String getO3() {
        return O3;
    }

    public void setO3(String o3) {
        O3 = o3;
    }

    public String getSO2() {
        return SO2;
    }

    public Location(String locationName) {
        this.locationName = locationName;
    }

    @Override
    public String toString() {
        return "Location{" +
                "locationName='" + locationName + '\'' +
                ", PM10='" + PM10 + '\'' +
                ", CO='" + CO + '\'' +
                ", NO2='" + NO2 + '\'' +
                ", O3='" + O3 + '\'' +
                ", SO2='" + SO2 + '\'' +
                '}';
    }

    public void setSO2(String SO2) {
        this.SO2 = SO2;
    }
}
