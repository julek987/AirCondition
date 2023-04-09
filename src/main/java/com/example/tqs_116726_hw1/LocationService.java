package com.example.tqs_116726_hw1;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.util.*;

@Service
public class LocationService {

    @Autowired
    LocationRepository locationRepository;

    public String location(){
        return "Location";
    }

    private int numberOfHits = 0;

    public String userChoseLocation(@ModelAttribute Location location, Model model) {
        if (!(location.getLocationName() == null || location.getLocationName().isEmpty())) {
            boolean inRepo = false;
            List<String> dataList = new ArrayList<String>();
            LocationEntity request = locationRepository.findByLocationNameAndRequestDateAndHit(location.getLocationName(), LocalDate.now(), false);
            if (request != null) {
                System.out.println("Location in database");
                numberOfHits++;
                inRepo = true;
                dataList.add(request.getPM10());
                dataList.add(request.getCO());
                dataList.add(request.getNO2());
                dataList.add(request.getO3());
                dataList.add(request.getSO2());
            } else {
                String coordinates = "default";
                coordinates = getLocationCoordinates(location.getLocationName());
                dataList = getLocationAirPollution(coordinates);
            }
            LocationEntity locationEntity = new LocationEntity();
            locationEntity.setLocationName(location.getLocationName());
            locationEntity.setRequestDate(LocalDate.now());
            if (inRepo) locationEntity.setHit(true);
            locationEntity.setPM10(dataList.get(0));
            locationEntity.setCO(dataList.get(1));
            locationEntity.setNO2(dataList.get(2));
            locationEntity.setO3(dataList.get(3));
            locationEntity.setSO2(dataList.get(4));
            locationRepository.save(locationEntity);

            assignValuesToLocation(location, dataList.get(0), dataList.get(1), dataList.get(2), dataList.get(3), dataList.get(4));
            assignValuesToModel(location, model);

            return "LocationInfo";
        }else
            return "ErrorPage";
    }


    public void assignValuesToModel(@ModelAttribute Location location, Model model){
        List<LocationEntity> locations = locationRepository.findAll();
        Collections.reverse(locations);
        model.addAttribute("locations", locations);
        Map<String, String> nameAndValue = new LinkedHashMap<String, String>();
        nameAndValue.put("locationName", location.getLocationName());
        nameAndValue.put("PM10", location.getPM10());
        nameAndValue.put("CO", location.getCO());
        nameAndValue.put("NO2", location.getNO2());
        nameAndValue.put("O3", location.getO3());
        nameAndValue.put("SO2", location.getSO2());
        model.addAttribute("nameAndValue", nameAndValue);
        model.addAttribute("numberOfHits", numberOfHits);
    }

    public void assignValuesToLocation(Location location, String PM10, String CO, String NO2, String O3, String SO2){
        location.setPM10(PM10);
        location.setCO(CO);
        location.setNO2(NO2);
        location.setO3(O3);
        location.setSO2(SO2);
    }

    public String getLocationCoordinates(String locationName){
        String latlon = "latlon";
        String request = "http://api.openweathermap.org/geo/1.0/direct?q=" + locationName + "&limit=1&appid=c907abdf274f55dabd64eb1c69718734";
        try{
            URL url = new URL(request);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            //Check if connection is made
            int responseCode = conn.getResponseCode();

            // 200 is ok
            if(responseCode != 200){
                throw new RuntimeException("HttpResponseCode: " + responseCode);
            }else{
                StringBuilder informationString = new StringBuilder();
                Scanner scanner = new Scanner(url.openStream());

                while(scanner.hasNext()){
                    informationString.append(scanner.nextLine());
                }

                //Close the scanner
                scanner.close();
                System.out.println(informationString);

                JSONParser parse = new JSONParser();
                JSONArray dataObject = (JSONArray) parse.parse(String.valueOf(informationString));

                System.out.println(dataObject.get(0));

                JSONObject countryData = (JSONObject) dataObject.get(0);

                System.out.println(countryData.get("location_type"));

                latlon = countryData.get("lat") + "&lon=" + countryData.get("lon");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return latlon;
    }
    public List<String> getLocationAirPollution(String latlon){
        List<String> list = new ArrayList<String>(5);
        String PM10 = "default";
        String CO = "default";
        String NO2 = "default";
        String O3 = "default";
        String SO2 = "default";
        String request = "http://api.openweathermap.org/data/2.5/air_pollution?lat=" + latlon + "&appid=c907abdf274f55dabd64eb1c69718734";
        try{
            URL url = new URL(request);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            //Check if connection is made
            int responseCode = conn.getResponseCode();

            // 200 is ok
            if(responseCode != 200){
                throw new RuntimeException("HttpResponseCode: " + responseCode);
            }else{
                StringBuilder informationString = new StringBuilder();
                Scanner scanner = new Scanner(url.openStream());

                while(scanner.hasNext()){
                    informationString.append(scanner.nextLine());
                }

                //Close the scanner
                scanner.close();
                System.out.println(informationString);

                JSONParser parser = new JSONParser();
                JSONObject json = (JSONObject) parser.parse(informationString.toString());
                JSONArray dataArray = (JSONArray) json.get("list");
                JSONObject dataObject = (JSONObject) dataArray.get(0);

                Object componentsObj = dataObject.get("components");
                if (componentsObj instanceof JSONObject components) {
                    Object pm10Obj = components.get("pm10");
                    PM10 = pm10Obj != null ? String.valueOf(pm10Obj) : "N/A";
                    Object coObj = components.get("co");
                    CO = coObj != null ? String.valueOf(coObj) : "N/A";
                    Object no2Obj = components.get("no2");
                    NO2 = no2Obj != null ? String.valueOf(no2Obj) : "N/A";
                    Object o3Obj = components.get("o3");
                    O3 = o3Obj != null ? String.valueOf(o3Obj) : "N/A";
                    Object so2Obj = components.get("so2");
                    SO2 = so2Obj != null ? String.valueOf(so2Obj) : "N/A";
                }

            }
        }catch (Exception e){
            e.printStackTrace();
        }
        list.add(PM10);
        list.add(CO);
        list.add(NO2);
        list.add(O3);
        list.add(SO2);
        return list;
    }

    public Location userRequestedLocationData(String locationName) {
        Location location = new Location(locationName);
        boolean inRepo = false;
        List<String> dataList = new ArrayList<String>();
        LocationEntity request = locationRepository.findByLocationNameAndRequestDateAndHit(locationName, LocalDate.now(), false);
        if (request != null) {
            System.out.println("Location in database");
            numberOfHits++;
            inRepo = true;
            dataList.add(request.getPM10());
            dataList.add(request.getCO());
            dataList.add(request.getNO2());
            dataList.add(request.getO3());
            dataList.add(request.getSO2());
        } else {
            String coordinates = "default";
            coordinates = getLocationCoordinates(locationName);
            dataList = getLocationAirPollution(coordinates);
        }
        LocationEntity locationEntity = new LocationEntity();
        locationEntity.setLocationName(locationName);
        locationEntity.setRequestDate(LocalDate.now());
        if(inRepo) locationEntity.setHit(true);
        locationEntity.setPM10(dataList.get(0));
        locationEntity.setCO(dataList.get(1));
        locationEntity.setNO2(dataList.get(2));
        locationEntity.setO3(dataList.get(3));
        locationEntity.setSO2(dataList.get(4));
        locationRepository.save(locationEntity);

        assignValuesToLocation(location, dataList.get(0), dataList.get(1), dataList.get(2), dataList.get(3), dataList.get(4));

        return location;
    }

    public List<Integer> getNumberOfRequestsAndHits() {
        List<Integer> myList = new ArrayList<>(2);
        myList.add(numberOfHits);
        myList.add((int) locationRepository.count());
        return myList;

    }
}
