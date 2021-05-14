package detiua.tqs.pedro93221.air_quality.service;

import detiua.tqs.pedro93221.air_quality.model.*;
import detiua.tqs.pedro93221.air_quality.repository.*;
import detiua.tqs.pedro93221.air_quality.cache.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class AirPollutionService {
    
    @Autowired
    private AirPollutionRepository airPollutionRepository;

    @Autowired
    private LocationRepository locationRepository;

    private final int CACHE_TIME_TO_LIVE = 600000;

    private final static String currRef = "Current";
    private final static String foreRef = "Forecast";
    private final static String histRef = "History";

    private Cache currentCache = new Cache(CACHE_TIME_TO_LIVE, currRef);
    private CacheDetails currentCacheDetails = new CacheDetails(currRef);

    private Cache forecastCache = new Cache(CACHE_TIME_TO_LIVE, foreRef);
    private CacheDetails forecastCacheDetails = new CacheDetails(foreRef);

    private Cache historicalCache = new Cache(CACHE_TIME_TO_LIVE, histRef);
    private CacheDetails historicalCacheDetails = new CacheDetails(histRef);

    public AirPollutionAnalysis getCurrentAirPollution(String address) {
        Location location = locationRepository.getLocation(address);

        currentCacheDetails.addRequest();

        if (currentCache.exists(location)) {
            currentCacheDetails.addHit();
            return currentCache.getAnalysis(location);
        }

        List<AirPollution> results = airPollutionRepository.getCurrentAnalysis(location);
        AirPollutionAnalysis analysis = null;

        if ( results == null ) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Invalid request. Make sure both the api key and the given arguments are valid.");
        } else {
            analysis =  new AirPollutionAnalysis(results, location);
            currentCache.setAnalysis(location, analysis);
        }

        currentCacheDetails.addMiss();

        return analysis;
    }

    public AirPollutionAnalysis getForecastAirPollution(String address) {
        Location location = locationRepository.getLocation(address);

        forecastCacheDetails.addRequest();

        if (forecastCache.exists(location)) {
            forecastCacheDetails.addHit();
            return forecastCache.getAnalysis(location);
        }

        List<AirPollution> results = airPollutionRepository.getForecastAnalysis(location);
        AirPollutionAnalysis analysis = null;

        if ( results == null ) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Invalid request. Make sure both the api key and the given arguments are valid.");
        } else {
            analysis =  new AirPollutionAnalysis(results, location);
            forecastCache.setAnalysis(location, analysis);
        }

        forecastCacheDetails.addMiss();

        return analysis;
    }

    public AirPollutionAnalysis getHistoricalAirPollution(String address, LocalDateTime start, LocalDateTime end) {
        Location location = locationRepository.getLocation(address);

        historicalCacheDetails.addRequest();

        if (historicalCache.exists(location)) {
            historicalCacheDetails.addHit();
            return historicalCache.getAnalysis(location);
        }

        List<AirPollution> results = airPollutionRepository.getHistoricalAnalysis(location, start, end);
        AirPollutionAnalysis analysis = null;

        if ( results == null ) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Invalid request. Make sure both the api key and the given arguments are valid.");
        } else {
            analysis =  new AirPollutionAnalysis(results, location);
            historicalCache.setAnalysis(location, analysis);
        }

        historicalCacheDetails.addMiss();

        return analysis;
    }

    public List<CacheDetails> getCache() {
        List<CacheDetails> cacheDetails = new ArrayList<>();

        cacheDetails.add(currentCacheDetails);
        cacheDetails.add(forecastCacheDetails);
        cacheDetails.add(historicalCacheDetails);

        return cacheDetails;
    }

    public List<CacheDetails> getCache(String type) {
        List<CacheDetails> cacheDetails = new ArrayList<>();

        if (type.equals(currRef))
            cacheDetails.add(currentCacheDetails);
        if (type.equals(foreRef))
            cacheDetails.add(forecastCacheDetails);
        if (type.equals(histRef))
            cacheDetails.add(historicalCacheDetails);

        return cacheDetails;
    }
}