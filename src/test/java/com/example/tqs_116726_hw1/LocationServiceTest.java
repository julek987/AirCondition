package com.example.tqs_116726_hw1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class LocationServiceTest {

    @Mock
    LocationRepository locationRepositoryMock;

    @Mock
    Model modelMock;

    LocationService locationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        locationService = new LocationService();
        locationService.locationRepository = locationRepositoryMock;
    }
    @Test
    void testUserChoseLocationWithEmptyLocationName() {
        Location location = new Location();
        location.setLocationName("");
        String expected = "ErrorPage";
        String actual = locationService.userChoseLocation(location, modelMock);
        assertEquals(expected, actual);
    }
    @Test
    void testUserChoseLocationWithNullLocationName() {
        Location location = new Location();
        location.setLocationName(null);
        String expected = "ErrorPage";
        String actual = locationService.userChoseLocation(location, modelMock);
        assertEquals(expected, actual);
    }
    @Test
    void testUserChoseLocationWhenLocationIsNotInRepository() {
        Location location = new Location();
        location.setLocationName("Lisbon");
        List<String> dataList = new ArrayList<>();
        dataList.add("10");
        dataList.add("20");
        dataList.add("30");
        dataList.add("40");
        dataList.add("50");

        when(locationRepositoryMock.findByLocationNameAndRequestDateAndHit("Lisbon", LocalDate.now(), false)).thenReturn(null);
        when(locationRepositoryMock.save(any(LocationEntity.class))).thenReturn(new LocationEntity());

        String expected = "LocationInfo";
        String actual = locationService.userChoseLocation(location, modelMock);
        assertEquals(expected, actual);

        verify(locationRepositoryMock, times(1)).findByLocationNameAndRequestDateAndHit("Lisbon", LocalDate.now(), false);
        verify(locationRepositoryMock, times(1)).save(any(LocationEntity.class));
        verify(modelMock, times(1)).addAttribute(eq("locations"), anyList());
        verify(modelMock, times(1)).addAttribute(eq("nameAndValue"), anyMap());
        verify(modelMock, times(1)).addAttribute(eq("numberOfHits"), eq(0));
    }
    @Test
    void testUserChoseLocationWhenLocationIsAlreadyInRepository() {
        Location location = new Location();
        location.setLocationName("Lisbon");
        List<String> dataList = new ArrayList<>();
        dataList.add("10");
        dataList.add("20");
        dataList.add("30");
        dataList.add("40");
        dataList.add("50");

        LocationEntity locationEntity = new LocationEntity();
        locationEntity.setHit(false);
        when(locationRepositoryMock.findByLocationNameAndRequestDateAndHit("Lisbon", LocalDate.now(), false)).thenReturn(locationEntity);

        String expected = "LocationInfo";
        String actual = locationService.userChoseLocation(location, modelMock);
        assertEquals(expected, actual);

        verify(locationRepositoryMock, times(1)).findByLocationNameAndRequestDateAndHit("Lisbon", LocalDate.now(), false);
        verify(modelMock, times(1)).addAttribute(eq("locations"), anyList());
        verify(modelMock, times(1)).addAttribute(eq("nameAndValue"), anyMap());
        verify(modelMock, times(1)).addAttribute(eq("numberOfHits"), eq(1));
    }


    @Test
    public void testAssignValuesToModel_withLocationAndValues() {
        Location location = new Location();
        location.setLocationName("Test Location");
        location.setPM10("10");
        location.setCO("20");
        location.setNO2("30");
        location.setO3("40");
        location.setSO2("50");
        Model model = new ExtendedModelMap();

        locationService.assignValuesToModel(location, model);

        assertTrue(model.containsAttribute("locations"));
        assertTrue(model.containsAttribute("nameAndValue"));
        assertTrue(model.containsAttribute("numberOfHits"));
        assertEquals("Test Location", ((Map<String, String>) model.getAttribute("nameAndValue")).get("locationName"));
        assertEquals("10", ((Map<String, String>) model.getAttribute("nameAndValue")).get("PM10"));
        assertEquals("20", ((Map<String, String>) model.getAttribute("nameAndValue")).get("CO"));
        assertEquals("30", ((Map<String, String>) model.getAttribute("nameAndValue")).get("NO2"));
        assertEquals("40", ((Map<String, String>) model.getAttribute("nameAndValue")).get("O3"));
        assertEquals("50", ((Map<String, String>) model.getAttribute("nameAndValue")).get("SO2"));
        assertEquals(0, model.getAttribute("numberOfHits"));
    }
    @Test
    public void testAssignValuesToModel_withNullLocation() {
        Location location = null;
        Model model = new ExtendedModelMap();

        locationService.assignValuesToModel(location, model);

        assertTrue(model.containsAttribute("locations"));
        assertFalse(model.containsAttribute("nameAndValue"));
        assertEquals(0, model.getAttribute("numberOfHits"));
    }

    @Test
    public void testAssignValuesToModel_withNullLocationName() {
        Location location = new Location();
        location.setLocationName(null);
        Model model = new ExtendedModelMap();

        locationService.assignValuesToModel(location, model);

        assertTrue(model.containsAttribute("locations"));
        assertFalse(model.containsAttribute("nameAndValue"));
        assertEquals(0, model.getAttribute("numberOfHits"));
    }


    @Test
    public void testAssignValuesToLocation_withValidParameters() {
        Location location = new Location();
        String PM10 = "10";
        String CO = "20";
        String NO2 = "30";
        String O3 = "40";
        String SO2 = "50";

        locationService.assignValuesToLocation(location, PM10, CO, NO2, O3, SO2);

        assertEquals(PM10, location.getPM10());
        assertEquals(CO, location.getCO());
        assertEquals(NO2, location.getNO2());
        assertEquals(O3, location.getO3());
        assertEquals(SO2, location.getSO2());
    }

    @Test
    public void testAssignValuesToLocation_withNullLocation() {
        Location location = null;
        String PM10 = "10";
        String CO = "20";
        String NO2 = "30";
        String O3 = "40";
        String SO2 = "50";

        assertThrows(NullPointerException.class, () -> locationService.assignValuesToLocation(location, PM10, CO, NO2, O3, SO2));
    }
}