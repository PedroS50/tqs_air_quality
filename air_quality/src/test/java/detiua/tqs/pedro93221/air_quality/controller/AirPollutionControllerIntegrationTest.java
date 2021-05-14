package detiua.tqs.pedro93221.air_quality.controller;

import org.springframework.http.MediaType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.Matchers.hasSize;

import detiua.tqs.pedro93221.air_quality.TqsAirQualityApplication;
import detiua.tqs.pedro93221.air_quality.model.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = TqsAirQualityApplication.class)
@AutoConfigureMockMvc
public class AirPollutionControllerIntegrationTest {

    @Autowired
    private MockMvc mvc;

    private String aveiroAddress = "Portugal, Aveiro";
    private Location aveiro = new Location(new Coordinates(40.6405055   , -8.6537539), aveiroAddress);

    @Test
    public void whenGetCurrentAP_thenReturnValidAnalysis() throws Exception {
        AirPollutionAnalysis analysis = getAnalysis();

        mvc.perform(get("/api/current?address=" + aveiroAddress).contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("location.coordinates.latitude", is(analysis.getLocation().getCoordinates().getLatitude())))
            .andExpect(jsonPath("location.coordinates.longitude", is(analysis.getLocation().getCoordinates().getLongitude())));

    }

    @Test
    public void whenGetForecastAP_thenReturnValidAnalysis() throws Exception {
        AirPollutionAnalysis analysis = getAnalysis();

        mvc.perform(get("/api/forecast?address=" + aveiroAddress).contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("location.coordinates.latitude", is(analysis.getLocation().getCoordinates().getLatitude())))
            .andExpect(jsonPath("location.coordinates.longitude", is(analysis.getLocation().getCoordinates().getLongitude())));

    }

    @Test
    public void whenGetHistoricalAP_thenReturnValidAnalysis() throws Exception {
        AirPollutionAnalysis analysis = getAnalysis();

        LocalDateTime start = LocalDateTime.now();
        LocalDateTime end = LocalDateTime.now();

        mvc.perform(get("/api/history?address=" + aveiroAddress + "&start=" + start + "&end=" + end).contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("location.coordinates.latitude", is(analysis.getLocation().getCoordinates().getLatitude())))
            .andExpect(jsonPath("location.coordinates.longitude", is(analysis.getLocation().getCoordinates().getLongitude())));

    }

    @Test
    public void whenGetHistoricalAPInvalidInterval_thenReturnNull() throws Exception {
        LocalDateTime end = LocalDateTime.now();
        LocalDateTime start = LocalDateTime.now();

        mvc.perform(get("/api/history?address=" + aveiroAddress + "&start=" + start + "&end=" + end).contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest());

    }

    @Test
    public void whenGetCache_thenReturnCacheDetails() throws Exception {

        mvc.perform(get("/api/cache").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(3)))
            .andExpect(jsonPath("$[0].type", is("Current")))
            .andExpect(jsonPath("$[1].type", is("Forecast")))
            .andExpect(jsonPath("$[2].type", is("History")));
        
        mvc.perform(get("/api/cache?type=Current").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(1)))
            .andExpect(jsonPath("$[0].type", is("Current")));

    }

    @Test
    public void whenGetCacheTypeInvalid_thenThrowResponseStatusException() throws Exception {
        mvc.perform(get("/api/cache?type=Invalid").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest());

    }    

    public AirPollutionAnalysis getAnalysis() {
        AirPollution airPol1 = new AirPollution(2, LocalDateTime.parse("2021-05-14T04:00:00"), new Components(205.28, 0.0, 1.11, 95.84, 1.36, 1.31, 3.64, 0.05));

        List<AirPollution> results = new ArrayList<AirPollution>(Arrays.asList(airPol1));

        return new AirPollutionAnalysis(results, aveiro);
    }
}
