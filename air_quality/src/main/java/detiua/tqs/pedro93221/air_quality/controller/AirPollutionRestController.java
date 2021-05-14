package detiua.tqs.pedro93221.air_quality.controller;

import detiua.tqs.pedro93221.air_quality.service.AirPollutionService;
import detiua.tqs.pedro93221.air_quality.model.*;
import detiua.tqs.pedro93221.air_quality.cache.CacheDetails;

import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@CrossOrigin(origins="*")
@RequestMapping("/api")
public class AirPollutionRestController {

    private static final Logger logger = LoggerFactory.getLogger(AirPollutionRestController.class);

    @Autowired
    private AirPollutionService airPollutionService;

    @GetMapping(path = "/current")
    public AirPollutionAnalysis getCurrentAirPollution(
            @RequestParam(value = "address", required = true) String address) {
        
        logger.info("LOGGER: GET /api/current?address={}", address);
        
        return airPollutionService.getCurrentAirPollution(address);
    }

    @GetMapping(path = "/forecast")
    public AirPollutionAnalysis getForecastAirPollution(
            @RequestParam(value = "address", required = true) String address) {
        
        logger.info("LOGGER: GET /api/forecast?address={}", address);
        
        return airPollutionService.getForecastAirPollution(address);
    }

    @GetMapping(path = "/history")
    public AirPollutionAnalysis getHistoricalAirPollution(
            @RequestParam(value = "address", required = true) String address,
            @RequestParam(value = "start", required = true) String start,
            @RequestParam(value = "end", required = true) String end) {
        
        logger.info("LOGGER: GET /api/history?address={}&start={}&end={}", address, start, end);
        
        LocalDateTime ldtStart = null;
        LocalDateTime ldtEnd = null;

        try {
            ldtStart = LocalDateTime.parse(start);
            ldtEnd = LocalDateTime.parse(end);
        } catch (Exception e) {
            logger.info("LOGGER: GET /api/history - ResponseStatusException: Invalid time format. {}", HttpStatus.BAD_REQUEST);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid time format.");
        }

        if (ldtStart == null || ldtEnd == null || ldtStart.isAfter(ldtEnd)) {
            logger.info("LOGGER: GET /api/history - ResponseStatusException: Invalid time interval. {}", HttpStatus.BAD_REQUEST);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid time interval.");
        }

        return airPollutionService.getHistoricalAirPollution(address, ldtStart, ldtEnd);
    }

    @GetMapping(path = "/cache")
    public List<CacheDetails> getCacheDetails(@RequestParam(value = "type", required = false, defaultValue="") String cacheType) {
        if (!cacheType.equals("Current") && !cacheType.equals("Forecast") && !cacheType.equals("History") && !cacheType.equals("")) {
        logger.info("LOGGER: GET /api/history - ResponseStatusException: Invalid cache type. {}", HttpStatus.BAD_REQUEST);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid cache type.");
        }

        if (!cacheType.equals("")) {
            logger.info("LOGGER: GET /cache");
            return airPollutionService.getCache(cacheType);
        }

        logger.info("LOGGER: GET /cache?type={}", cacheType);
        return airPollutionService.getCache();
    }
}