package com.example.tqs_116726_hw1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
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
}