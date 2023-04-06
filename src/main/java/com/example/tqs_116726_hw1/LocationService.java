package com.example.tqs_116726_hw1;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;

@Service
public class LocationService {

    public String location(){
        return "Location";
    }

    public String userChoseLocation(@ModelAttribute Location location, Model model){
        assignValuesToLocation(location, "10.5", "123", "34.3", "0.9", "1234654");
        assignValuesToModel(location, model);
        return "LocationInfo";
    }

    public void assignValuesToModel(@ModelAttribute Location location, Model model){
        model.addAttribute("locationName", location.getLocationName());
        model.addAttribute("PM10", location.getPM10());
        model.addAttribute("CO", location.getCO());
        model.addAttribute("NO2", location.getNO2());
        model.addAttribute("O3", location.getO3());
        model.addAttribute("SO2", location.getSO2());
    }

    public void assignValuesToLocation(Location location, String PM10, String CO, String NO2, String O3, String SO2){
        location.setPM10(PM10);
        location.setCO(CO);
        location.setNO2(NO2);
        location.setO3(O3);
        location.setSO2(SO2);
    }
}
