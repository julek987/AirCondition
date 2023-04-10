package com.example.tqs_116726_hw1;

import java.util.Objects;

public class Location {
    private String locationName;

    private String PM10 = "0.00";
    private String CO = "0.00";
    private String NO2 = "0.00";
    private String O3 = "0.00";
    private String SO2 = "0.00";

    public Location() {}
    public Location(String locationName) {
        this.locationName = locationName;
    }
    public Location(String locationName, String PM10, String CO, String NO2, String o3, String SO2) {
        this.locationName = locationName;
        this.PM10 = PM10;
        this.CO = CO;
        this.NO2 = NO2;
        this.O3 = o3;
        this.SO2 = SO2;
    }

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

    public void setSO2(String SO2) {
        this.SO2 = SO2;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Location location = (Location) o;
        return Objects.equals(locationName, location.locationName) && Objects.equals(PM10, location.PM10) && Objects.equals(CO, location.CO) && Objects.equals(NO2, location.NO2) && Objects.equals(O3, location.O3) && Objects.equals(SO2, location.SO2);
    }

    @Override
    public int hashCode() {
        return Objects.hash(locationName, PM10, CO, NO2, O3, SO2);
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
}
