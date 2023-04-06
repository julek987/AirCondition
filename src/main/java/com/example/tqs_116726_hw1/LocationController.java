package com.example.tqs_116726_hw1;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class LocationController {

    @GetMapping()
    public String index(){
        return "Location";
    }

    @PostMapping("/choose")
    public String userChoseLocation(@ModelAttribute Location location, Model model){
        model.addAttribute("locationName", location.getLocationName());
        model.addAttribute("PM10", location.getPM10());
        model.addAttribute("CO", location.getCO());
        model.addAttribute("NO2", location.getNO2());
        model.addAttribute("O3", location.getO3());
        model.addAttribute("SO2", location.getSO2());
        return "LocationInfo";
    }
}
