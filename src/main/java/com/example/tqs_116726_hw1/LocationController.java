package com.example.tqs_116726_hw1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class LocationController {

    private final LocationService locationService;
    @Autowired
    public LocationController(LocationService locationService, LocationService locationService1){
        this.locationService = locationService1;
    }
    @GetMapping("/")
    public String index(){
        return locationService.location();
    }

    @PostMapping("/choose")
    public String userChoseLocation(@ModelAttribute Location location, Model model){
        return locationService.userChoseLocation(location, model);
    }
}
