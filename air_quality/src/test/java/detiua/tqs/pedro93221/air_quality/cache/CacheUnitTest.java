package detiua.tqs.pedro93221.air_quality.cache;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import detiua.tqs.pedro93221.air_quality.model.*;

public class CacheUnitTest {
    Cache myCache;
    int TIME_TO_LIVE = 1000;

    @BeforeEach
    public void setUp() {
        myCache = new Cache(TIME_TO_LIVE, "current");
    }

    @AfterEach
    public void cleanUp() {

    }

    @Test
    public void whenInitialized_dataStructuresAreEmpty() {
        assertThat( myCache.getExpirationDates().size(), is(0) );
        assertThat( myCache.getData().size(), is(0) );
    }

    @Test
    public void whenCacheEmpty_thenReturnFalse() {
        Location newLocation = new Location(new Coordinates(0, 0), "Aveiro");

        assertThat( myCache.exists(newLocation), is(false) );
    }

    @Test
    public void whenAddedToCache_thenReturnTrue() {
        Location newLocation1 = new Location(new Coordinates(0, 0), "Aveiro");
        Location newLocation2 = new Location(new Coordinates(1, 1), "Porto");

        myCache.setAnalysis(newLocation1, loadAirPollutionAnalysis(newLocation1));

        myCache.setAnalysis(newLocation2, loadAirPollutionAnalysis(newLocation2));

        assertThat( myCache.exists(newLocation1), is(true) );
        assertThat( myCache.exists(newLocation2), is(true) );
    }

    @Test
    public void whenLocationInCache_thenReturnAirPollutionAnalysis() {
        Location newLocation = new Location(new Coordinates(0, 0), "Aveiro");

        List<AirPollution> data = new ArrayList<>();
        data.add( new AirPollution(1, LocalDateTime.now(), new Components(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0)) );
        data.add( new AirPollution(2, LocalDateTime.now(), new Components(1.1, 2.2, 3.3, 4.4, 5.5, 6.6, 7.7, 8.8)) );

        AirPollutionAnalysis newAnalysis = new AirPollutionAnalysis(data, newLocation);
        myCache.setAnalysis(newLocation, newAnalysis);

        assertThat( myCache.getAnalysis(newLocation), is(newAnalysis) );
    }

    @Test
    public void whenDataIsAdded_thenExpirationDateIsAdded() {
        Location newLocation = new Location(new Coordinates(0, 0), "Aveiro");

        myCache.setAnalysis(newLocation, loadAirPollutionAnalysis(newLocation));

        assertThat( myCache.getExpirationDates().containsKey(newLocation), is(true) );
    }

    @Test
    public void whenDataExpires_thenExistsReturnsFalse() throws InterruptedException {
        Location newLocation = new Location(new Coordinates(0, 0), "Aveiro");

        myCache.setAnalysis(newLocation, loadAirPollutionAnalysis(newLocation));

        TimeUnit.SECONDS.sleep(1);

        assertThat( myCache.exists(newLocation), is(false) );

    }

    public AirPollutionAnalysis loadAirPollutionAnalysis(Location location) {
        List<AirPollution> data = new ArrayList<>();
        data.add( new AirPollution(1, LocalDateTime.now(), new Components(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0)) );
        
        return new AirPollutionAnalysis(data, location);

    }

}