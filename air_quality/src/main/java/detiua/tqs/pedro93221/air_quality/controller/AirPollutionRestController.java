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
@CrossOrigin(origins="*")
@RequestMapping("/api")
public class AirPollutionRestController {

    @Autowired
    private AirPollutionService airPollutionService;

    @GetMapping(path = "/current")
    public AirPollutionAnalysis getCurrentAirPollution(
            @RequestParam(value = "address", required = true) String address) {

        return airPollutionService.getCurrentAirPollution(address);
    }

    @GetMapping(path = "/forecast")
    public AirPollutionAnalysis getForecastAirPollution(
            @RequestParam(value = "address", required = true) String address) {

        return airPollutionService.getForecastAirPollution(address);
    }

    @GetMapping(path = "/history")
    public AirPollutionAnalysis getHistoricalAirPollution(
            @RequestParam(value = "address", required = true) String address,
            @RequestParam(value = "start", required = true) String start,
            @RequestParam(value = "end", required = true) String end) {

        LocalDateTime ldtStart = null;
        LocalDateTime ldtEnd = null;

        try {
            ldtStart = LocalDateTime.parse(start);
            ldtEnd = LocalDateTime.parse(end);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid time format.");
        }

        if (ldtStart == null || ldtEnd == null || ldtStart.isAfter(ldtEnd))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid time interval.");

        return airPollutionService.getHistoricalAirPollution(address, ldtStart, ldtEnd);
    }

    @GetMapping(path = "/cache")
    public List<CacheDetails> getCacheDetails(@RequestParam(value = "type", required = false, defaultValue="") String cacheType) {
        if (!cacheType.equals("Current") && !cacheType.equals("Forecast") && !cacheType.equals("History") && !cacheType.equals(""))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid cache type.");

        if (!cacheType.equals(""))
            return airPollutionService.getCache(cacheType);

        return airPollutionService.getCache();
    }
}