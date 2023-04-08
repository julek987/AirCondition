package com.example.tqs_116726_hw1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping()
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

    @GetMapping("location/{locationName}")
    public @ResponseBody Location userRequestedLocationData(@PathVariable String locationName){
        return locationService.userRequestedLocationData(locationName);
    }
    @GetMapping("data/stats")
    public @ResponseBody Map<String, Integer> numberOfRequestAndHits(){
        Map<String, Integer> myMap = new HashMap<>();
        List<Integer> myList = new ArrayList<>(2);
        myList = locationService.getNumberOfRequestsAndHits();
        myMap.put("Hits",myList.get(0));
        myMap.put("Requests",myList.get(1));
        return myMap;
    }
    @PostMapping("/choose")
    public String userChoseLocation(@ModelAttribute Location location, Model model){
        return locationService.userChoseLocation(location, model);
    }
}
