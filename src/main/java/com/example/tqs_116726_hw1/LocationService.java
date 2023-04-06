package com.example.tqs_116726_hw1;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Service
public class LocationService {

    public String location(){
        return "Location";
    }

    public String userChoseLocation(@ModelAttribute Location location, Model model){
        String latlon = "default";
        latlon = getLocationCoordinates(location.getLocationName());
        List<String> dataList = getLocationAirPollution(latlon);
        assignValuesToLocation(location, dataList.get(0), dataList.get(1), dataList.get(2), dataList.get(3), dataList.get(4));
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
                    PM10 = pm10Obj != null ? String.valueOf(((Double) pm10Obj).intValue()) : "N/A";
                    Object coObj = components.get("co");
                    CO = coObj != null ? String.valueOf(((Double) coObj)) : "N/A";
                    Object no2Obj = components.get("no2");
                    NO2 = no2Obj != null ? String.valueOf(((Double) no2Obj)) : "N/A";
                    Object o3Obj = components.get("o3");
                    O3 = o3Obj != null ? String.valueOf(((Double) o3Obj)) : "N/A";
                    Object so2Obj = components.get("so2");
                    SO2 = so2Obj != null ? String.valueOf(((Double) so2Obj)) : "N/A";
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
}
