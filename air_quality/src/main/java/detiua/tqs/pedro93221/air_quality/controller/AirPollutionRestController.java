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

@RestController
@RequestMapping("/api")
public class AirPollutionRestController {

    @Autowired
    private AirPollutionService airPollutionService;

    @GetMapping(path="/current")
    public AirPollutionAnalysis getCurrentAirPollution( @RequestParam(value = "address", required = true) String address) {
        Location location = airPollutionService.getLocation(address);

        AirPollutionAnalysis result = airPollutionService.getCurrentAirPollution(location);

        return result;
    }

    @GetMapping(path="/forecast")
    public AirPollutionAnalysis getForecastAirPollution( @RequestParam(value = "address", required = true) String address) {
        Location location = airPollutionService.getLocation(address);

        AirPollutionAnalysis result = airPollutionService.getForecastAirPollution(location);

        return result;
    }

    @GetMapping(path="/history")
    public AirPollutionAnalysis getHistoricalAirPollution(  @RequestParam(value = "address", required = true) String address,
                                                            @RequestParam(value = "start", required = true) String start,
                                                            @RequestParam(value = "end", required = true) String end ) {

        Location location = airPollutionService.getLocation(address);
        
        LocalDateTime ldtStart = null, ldtEnd = null;
        
        try {
            ldtStart = LocalDateTime.parse(start);
            ldtEnd = LocalDateTime.parse(end);
        } catch (Exception e) {}

        if ( ldtStart == null || ldtEnd == null || ldtStart.isAfter(ldtEnd) )
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid time interval.");

        AirPollutionAnalysis result = airPollutionService.getHistoricalAirPollution(location, ldtStart, ldtEnd);

        return result;
    }

    @GetMapping(path="/cache")
    public List<CacheDetails> getCacheDetails( @RequestParam(value = "type", required = false) String cacheType) {
        return airPollutionService.getCache(cacheType);
    }
}