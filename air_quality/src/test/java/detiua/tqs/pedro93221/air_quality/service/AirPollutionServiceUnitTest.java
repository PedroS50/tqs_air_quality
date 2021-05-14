package detiua.tqs.pedro93221.air_quality.service;

import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import detiua.tqs.pedro93221.air_quality.cache.CacheDetails;
import detiua.tqs.pedro93221.air_quality.model.*;
import detiua.tqs.pedro93221.air_quality.repository.AirPollutionRepository;
import detiua.tqs.pedro93221.air_quality.repository.LocationRepository;

@ExtendWith(MockitoExtension.class)
class AirPollutionServiceUnitTest {

    @Mock(lenient = true)
    private AirPollutionRepository airPolRepository;

    @Mock(lenient = true)
    private LocationRepository locationRepository;

    @InjectMocks
    private AirPollutionService airPollutionService;

    private String aveiroAddress, portoAddress;
    private List<AirPollution> airPolAveiroAnalysis, historicalAirPolAveiroAnalysis;
    private Location aveiro, porto;
    private LocalDateTime startDate, endDate;

    @BeforeEach
    void setUp() {
        aveiroAddress = "Portugal, Aveiro";
        portoAddress = "Portugal, Porto";
        aveiro = new Location(new Coordinates(10, 11), aveiroAddress);
        porto = new Location(new Coordinates(1, 2), portoAddress);
        
        AirPollution airPolAveiro = new AirPollution(2, LocalDateTime.now(), new Components(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0));
        AirPollution airPolAveiro2 = new AirPollution(1, LocalDateTime.now(), new Components(1.1, 2.2, 3.3, 4.4, 5.5, 6.6, 7.7, 8.8));

        airPolAveiroAnalysis = new ArrayList<AirPollution>(Arrays.asList(airPolAveiro));
        historicalAirPolAveiroAnalysis = new ArrayList<AirPollution>(Arrays.asList(airPolAveiro, airPolAveiro2));
        startDate = LocalDateTime.parse("2019-10-11T13:00:00");
        endDate = LocalDateTime.parse("2019-11-11T13:00:00");
        
        Mockito.when(locationRepository.getLocation(aveiroAddress)).thenReturn(aveiro);
        Mockito.when(locationRepository.getLocation(portoAddress)).thenReturn(porto); 
        
        Mockito.when(airPolRepository.getCurrentAnalysis(aveiro)).thenReturn(airPolAveiroAnalysis);
        Mockito.when(airPolRepository.getForecastAnalysis(aveiro)).thenReturn(airPolAveiroAnalysis);
        Mockito.when(airPolRepository.getHistoricalAnalysis(aveiro, startDate, endDate)).thenReturn(historicalAirPolAveiroAnalysis);
        
        Mockito.when(airPolRepository.getCurrentAnalysis(porto)).thenReturn(null);
        Mockito.when(airPolRepository.getForecastAnalysis(porto)).thenReturn(null);
        Mockito.when(airPolRepository.getHistoricalAnalysis(porto, startDate, endDate)).thenReturn(null);
    }

    @Test
    void whenGetCurrentAPFromValidLocation_thenReturnValidAnalysis () {
        AirPollutionAnalysis airPolAnalysis = airPollutionService.getCurrentAirPollution(aveiroAddress);
        assertThat( airPolAnalysis, is(new AirPollutionAnalysis(airPolAveiroAnalysis, aveiro)) );

        verifyGetLocationIsCalledOnce(aveiroAddress);
        verifyGetCurrentAirPollutionIsCalledOnce(aveiro);
    }

    @Test
    void whenGetCurrentAPFromInvalidLocation_thenThrowsResponseStatusException () throws ResponseStatusException {
        assertThrows( ResponseStatusException.class, () -> { airPollutionService.getCurrentAirPollution(portoAddress); } );
        
        verifyGetLocationIsCalledOnce(portoAddress);
        verifyGetCurrentAirPollutionIsCalledOnce(porto);
    }

    @Test
    void whenGetForecastAPFromValidLocation_thenReturnValidAnalysis () {
        AirPollutionAnalysis airPolAnalysis = airPollutionService.getForecastAirPollution(aveiroAddress);

        assertThat( airPolAnalysis, is(new AirPollutionAnalysis(airPolAveiroAnalysis, aveiro)) );
    
        verifyGetLocationIsCalledOnce(aveiroAddress);
        verifyGetForecastAirPollutionIsCalledOnce(aveiro);
    }

    @Test
    void whenGetForecastAPFromInvalidLocation_thenThrowsResponseStatusException () throws ResponseStatusException {
        assertThrows( ResponseStatusException.class, () -> { airPollutionService.getForecastAirPollution(portoAddress); } );
        
        verifyGetLocationIsCalledOnce(portoAddress);
        verifyGetForecastAirPollutionIsCalledOnce(porto);
    }

    @Test
    void whenGetHistoricalAPFromValidLocation_thenReturnValidAnalysis () {
        AirPollutionAnalysis airPolAnalysis = airPollutionService.getHistoricalAirPollution(aveiroAddress, startDate, endDate);

        assertThat( airPolAnalysis, is(new AirPollutionAnalysis(historicalAirPolAveiroAnalysis, aveiro)) );
    
        verifyGetLocationIsCalledOnce(aveiroAddress);
        verifyGetHistoricalAirPollutionIsCalledOnce(aveiro, startDate, endDate);
    }

    @Test
    void whenGetHistoricalAPFromInvalidLocation_thenThrowsResponseStatusException () throws ResponseStatusException {
        assertThrows( ResponseStatusException.class, () -> { airPollutionService.getHistoricalAirPollution(portoAddress, startDate, endDate); } );
    
        verifyGetLocationIsCalledOnce(portoAddress);
        verifyGetHistoricalAirPollutionIsCalledOnce(porto, startDate, endDate);
    }

    @Test
    void whenMultipleGetCurrentAP_thenCacheDetailsAreCorrect() {
        airPollutionService.getCurrentAirPollution(aveiroAddress);
        airPollutionService.getCurrentAirPollution(aveiroAddress);

        List<CacheDetails> cacheResults = airPollutionService.getCache("Current");

        assertThat( cacheResults.size(), is(1) );
        assertThat( cacheResults.get(0), is(new CacheDetails("Current", 1, 1, 2)) );
    }

    @Test
    void whenMultipleGetForecastAP_thenCacheDetailsAreCorrect() {
        airPollutionService.getForecastAirPollution(aveiroAddress);
        airPollutionService.getForecastAirPollution(aveiroAddress);

        List<CacheDetails> cacheResults = airPollutionService.getCache("Forecast");

        assertThat( cacheResults.size(), is(1) );
        assertThat( cacheResults.get(0), is(new CacheDetails("Forecast", 1, 1, 2)) );
        
    }

    @Test
    void whenMultipleGetHistoricalAP_thenCacheDetailsAreCorrect() {
        airPollutionService.getHistoricalAirPollution(aveiroAddress, startDate, endDate);
        airPollutionService.getHistoricalAirPollution(aveiroAddress, startDate, endDate);

        List<CacheDetails> cacheResults = airPollutionService.getCache("History");

        assertThat( cacheResults.size(), is(1) );
        assertThat( cacheResults.get(0), is(new CacheDetails("History", 1, 1, 2)) );
    }

    @Test
    void whenMultipleRequestsToDifferentEndpoints_thenCacheDetailsAreCorrect() {
        airPollutionService.getCurrentAirPollution(aveiroAddress);
        airPollutionService.getCurrentAirPollution(aveiroAddress);
        airPollutionService.getCurrentAirPollution(aveiroAddress);
        airPollutionService.getForecastAirPollution(aveiroAddress);
        airPollutionService.getHistoricalAirPollution(aveiroAddress, startDate, endDate);
        airPollutionService.getHistoricalAirPollution(aveiroAddress, startDate, endDate);

        CacheDetails currentCache = new CacheDetails("Current", 2, 1, 3);
        CacheDetails forecastCache = new CacheDetails("Forecast", 0, 1, 1);
        CacheDetails historicalCache = new CacheDetails("History", 1, 1, 2);

        List<CacheDetails> cacheResults = airPollutionService.getCache();

        assertThat( cacheResults.contains(currentCache), is(true) );
        assertThat( cacheResults.contains(forecastCache), is(true) );
        assertThat( cacheResults.contains(historicalCache), is(true) );
    }

    private void verifyGetCurrentAirPollutionIsCalledOnce(Location location) {
        Mockito.verify(airPolRepository, VerificationModeFactory.times(1)).getCurrentAnalysis(location);
    }
    private void verifyGetForecastAirPollutionIsCalledOnce(Location location) {
        Mockito.verify(airPolRepository, VerificationModeFactory.times(1)).getForecastAnalysis(location);
    }
    private void verifyGetHistoricalAirPollutionIsCalledOnce(Location location, LocalDateTime start, LocalDateTime end) {
        Mockito.verify(airPolRepository, VerificationModeFactory.times(1)).getHistoricalAnalysis(location, start, end);
    }
    private void verifyGetLocationIsCalledOnce(String address) {
        Mockito.verify(locationRepository, VerificationModeFactory.times(1)).getLocation(address);
    }

}