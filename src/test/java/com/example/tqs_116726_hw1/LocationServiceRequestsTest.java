package com.example.tqs_116726_hw1;

import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class LocationServiceRequestsTest {

    @Mock
    IGetHttp httpClient = Mockito.mock(IGetHttp.class);

    @InjectMocks
    LocationService locationService;

    String exampleResponseForCoordinates = "[{\"name\":\"Porto\",\"local_names\":{\"el\":\"Πόρτο\",\"lt\":\"Portas\",\"kn\":\"ಪೋರ್ಟೊ\",\"feature_name\":\"Porto\",\"ru\":\"Порту\",\"ascii\":\"Porto\",\"sr\":\"Порто\",\"ar\":\"بورتو\",\"uk\":\"Порту\",\"hu\":\"Porto\",\"pt\":\"Porto\",\"es\":\"Oporto\"},\"lat\":41.1494512,\"lon\":-8.6107884,\"country\":\"PT\"}]";
    String exampleResponseForAirPollution = "{\"coord\":{\"lon\":-8.6108,\"lat\":41.1495},\"list\":[{\"main\":{\"aqi\":3},\"components\":{\"co\":203.61,\"no\":0.01,\"no2\":2.16,\"o3\":101.57,\"so2\":1.88,\"pm2_5\":2.83,\"pm10\":8.79,\"nh3\":0.21},\"dt\":1681154286}]}";
    Optional<String> coordinates = "41.1494512&lon=-8.6107884".describeConstable();
    Optional<List<String>> airPolutionList = Optional.of(Arrays.asList("8.79", "203.61", "2.16", "101.57", "1.88"));

    @Test
    void getLocationCoordinatesTest() throws IOException, URISyntaxException, ParseException {
        when(httpClient.doHttpGet(any(String.class))).thenReturn(exampleResponseForCoordinates);
        assertThat(locationService.getLocationCoordinates("Porto"), equalTo(coordinates));
    }

    @Test
    void getLocationAirPollutionTest() throws IOException, URISyntaxException, ParseException {
        when(httpClient.doHttpGet(any(String.class))).thenReturn(exampleResponseForAirPollution);
        assertThat(locationService.getLocationAirPollution("41.1494512&lon=-8.6107884"), equalTo(airPolutionList));
    }

}
