package detiua.tqs.pedro93221.air_quality.repository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.BeforeEach;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.CoreMatchers.nullValue;

import detiua.tqs.pedro93221.air_quality.model.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

@ExtendWith(MockitoExtension.class)
public class AirPollutionRepositoryUnitTest {
    
    @InjectMocks
    private AirPollutionRepository airPollutionRepository;

    private Location invalidLocation;

    @BeforeEach
    public void setUp() {
        invalidLocation = new Location(new Coordinates(99999, 99999), "Invalid Location");
    }

    @Test
    public void whenCurrentLocationIsInvalid_thenReturnNull() {
        List<AirPollution> results = airPollutionRepository.getCurrentAnalysis(invalidLocation);
        assertThat( results, is(nullValue()) );
    
    }

    @Test
    public void whenForecastLocationIsInvalid_thenThrowException() {
        List<AirPollution> results = airPollutionRepository.getForecastAnalysis(invalidLocation);
        assertThat( results, is(nullValue()) );
    
    }

    @Test
    public void whenHistorricalLocationIsInvalid_thenThrowException() {
        List<AirPollution> results = airPollutionRepository.getHistoricalAnalysis(invalidLocation, LocalDateTime.now(), LocalDateTime.now());
        assertThat( results, is(nullValue()) );
    
    }

    @Test
    public void whenAirPollutionResultsAreProcessed_thenReturnValidList() {
        String jsonResponse = "{\"coord\":{\"lon\":50,\"lat\":50},\"list\":[{\"main\":{\"aqi\":1},\"components\":{\"co\":208.62,\"no\":0,\"no2\":0.74,\"o3\":55.08,\"so2\":1.01,\"pm2_5\":1.45,\"pm10\":1.45,\"nh3\":0},\"dt\":1606266000},{\"main\":{\"aqi\":1},\"components\":{\"co\":208.62,\"no\":0,\"no2\":0.85,\"o3\":54.36,\"so2\":0.92,\"pm2_5\":1.33,\"pm10\":1.33,\"nh3\":0.01},\"dt\":1606269600},{\"main\":{\"aqi\":1},\"components\":{\"co\":210.29,\"no\":0,\"no2\":1.01,\"o3\":52.93,\"so2\":0.98,\"pm2_5\":1.36,\"pm10\":1.37,\"nh3\":0.01},\"dt\":1606273200}]}";
        
        AirPollution airPol1 = new AirPollution(1, LocalDateTime.parse("2020-11-25T01:00"), new Components(208.62, 0.0, 0.74, 55.08, 1.01, 1.45, 1.45, 0.0));
        AirPollution airPol2 = new AirPollution(1, LocalDateTime.parse("2020-11-25T02:00"), new Components(208.62, 0.0, 0.85, 54.36, 0.92, 1.33, 1.33, 0.01));
        AirPollution airPol3 = new AirPollution(1, LocalDateTime.parse("2020-11-25T03:00"), new Components(210.29, 0.0, 1.01, 52.93, 0.98, 1.36, 1.37, 0.01));

        List<AirPollution> expectedResults = new ArrayList<AirPollution>(Arrays.asList(airPol1, airPol2, airPol3));

        List<AirPollution> results = airPollutionRepository.processAirPolResults(jsonResponse);

        assertThat( results.size(), is(expectedResults.size()) );
        assertThat(results.equals(expectedResults), is(true));

    }

}
