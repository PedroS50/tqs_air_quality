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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
@Transactional
public class AirPollutionService {

    private static final Logger logger = LoggerFactory.getLogger(AirPollutionService.class);
    
    @Autowired
    private AirPollutionRepository airPollutionRepository;

    @Autowired
    private LocationRepository locationRepository;

    private final int CACHE_TIME_TO_LIVE = 600000;

    private final static String CURR_REF = "Current";
    private final static String FORE_REF = "Forecast";
    private final static String HIST_REF = "History";

    private Cache currentCache = new Cache(CACHE_TIME_TO_LIVE, CURR_REF);
    private CacheDetails currentCacheDetails = new CacheDetails(CURR_REF);

    private Cache forecastCache = new Cache(CACHE_TIME_TO_LIVE, FORE_REF);
    private CacheDetails forecastCacheDetails = new CacheDetails(FORE_REF);

    private Cache historicalCache = new Cache(CACHE_TIME_TO_LIVE, HIST_REF);
    private CacheDetails historicalCacheDetails = new CacheDetails(HIST_REF);

    public AirPollutionAnalysis getCurrentAirPollution(String address) {
        Location location = locationRepository.getLocation(address); 

        currentCacheDetails.addRequest();

        if (currentCache.exists(location)) {
            logger.info("LOGGER: GET /api/current - Retrieving data from cache.");
            currentCacheDetails.addHit();
            return currentCache.getAnalysis(location);
        }

        List<AirPollution> results = airPollutionRepository.getCurrentAnalysis(location);
        logger.info("LOGGER: GET /api/current - Retrieved data from external API: {}.", results);
        AirPollutionAnalysis analysis = null;

        if ( results == null ) {
            logger.info("LOGGER: GET /api/current - ResponseStatusException: Invalid request. Make sure both the api key and the given arguments are valid. {}", HttpStatus.BAD_REQUEST);
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
            logger.info("LOGGER: GET /api/forecast - Retrieving data from cache.");
            forecastCacheDetails.addHit();
            return forecastCache.getAnalysis(location);
        }

        List<AirPollution> results = airPollutionRepository.getForecastAnalysis(location);
        logger.info("LOGGER: GET /api/forecast - Retrieved data from external API: {}.", results);
        AirPollutionAnalysis analysis = null;

        if ( results == null ) {
            logger.info("LOGGER: GET /api/forecast - ResponseStatusException: Invalid request. Make sure both the api key and the given arguments are valid. {}", HttpStatus.BAD_REQUEST);
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
            logger.info("LOGGER: GET /api/history - Retrieving data from cache.");
            historicalCacheDetails.addHit();
            return historicalCache.getAnalysis(location);
        }

        List<AirPollution> results = airPollutionRepository.getHistoricalAnalysis(location, start, end);
        logger.info("LOGGER: GET /api/history - Retrieved data from external API: {}.", results);
        AirPollutionAnalysis analysis = null;

        if ( results == null ) {
            logger.info("LOGGER: GET /api/history - ResponseStatusException: Invalid request. Make sure both the api key and the given arguments are valid. {}", HttpStatus.BAD_REQUEST);
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

        if (type.equals(CURR_REF))
            cacheDetails.add(currentCacheDetails);
        if (type.equals(FORE_REF))
            cacheDetails.add(forecastCacheDetails);
        if (type.equals(HIST_REF))
            cacheDetails.add(historicalCacheDetails);

        return cacheDetails;
    }
}