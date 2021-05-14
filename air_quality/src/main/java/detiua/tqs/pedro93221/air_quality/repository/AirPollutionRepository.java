package detiua.tqs.pedro93221.air_quality.repository;

import detiua.tqs.pedro93221.air_quality.model.*;
import detiua.tqs.pedro93221.air_quality.converter.Converter;

import org.springframework.stereotype.Repository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.client.RestTemplate;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import java.io.FileReader;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

@Repository
public class AirPollutionRepository {
    private static final String openweathermap_apiKey = loadOWMKey();
    private static final String CURRENT_API_URL = "http://api.openweathermap.org/data/2.5/air_pollution?appid=" + openweathermap_apiKey;
    private static final String FORECAST_API_URL = "http://api.openweathermap.org/data/2.5/air_pollution/forecast?appid=" + openweathermap_apiKey;
    private static final String HISTORICAL_API_URL = "http://api.openweathermap.org/data/2.5/air_pollution/history?appid=" + openweathermap_apiKey;

    private static final String LATREF = "&lat=";
    private static final String LONREF = "&lon=";

    private RestTemplate restTemplate = new RestTemplate();

    private Converter converter = new Converter();

    private static String loadOWMKey() {
        String path = "src/main/java/detiua/tqs/pedro93221/air_quality/keys.txt";

        try {
            JSONParser parser = new JSONParser();

            JSONObject jsonObject = (JSONObject) parser.parse(new FileReader(path));

            return (String) jsonObject.get("openweathermap");

        } catch (Exception e) {
            return "";

        }
    }

    public List<AirPollution> getCurrentAnalysis(Location location) {
        String requestUrl = CURRENT_API_URL;
        requestUrl += LATREF + location.getCoordinates().getLatitude();
        requestUrl += LONREF + location.getCoordinates().getLongitude();

        List<AirPollution> airPolList = null;

        try { 
            ResponseEntity<String> response = this.restTemplate.getForEntity(requestUrl, String.class);
            
            if (response.getStatusCode() == HttpStatus.OK) {
                airPolList = processAirPolResults( response.getBody() );
            } else {
                throw new IllegalStateException("Data fetch from external api failed. Status code: " + response.getStatusCode());
            }
        } catch (Exception e) {
            // To Do
        }

        return airPolList;
    }

    public List<AirPollution> getForecastAnalysis(Location location) {
        String requestUrl = FORECAST_API_URL;
        requestUrl += LATREF + location.getCoordinates().getLatitude();
        requestUrl += LONREF + location.getCoordinates().getLongitude();

        List<AirPollution> airPolList = null;

        try {
            ResponseEntity<String> response = this.restTemplate.getForEntity(requestUrl, String.class);
            
            if (response.getStatusCode() == HttpStatus.OK) {
                airPolList = processAirPolResults( response.getBody() );
            } else {
                throw new IllegalStateException("Data fetch from external api failed. Status code: " + response.getStatusCode());
            }
        } catch (Exception e) {
            // To Do
        }

        return airPolList;
    }

    public List<AirPollution> getHistoricalAnalysis(Location location, LocalDateTime start, LocalDateTime end) {
        String requestUrl = HISTORICAL_API_URL;
        requestUrl += LATREF + location.getCoordinates().getLatitude();
        requestUrl += LONREF + location.getCoordinates().getLongitude();
        requestUrl += "&start=" + converter.lDTtoEpoch(start);
        requestUrl += "&end=" + converter.lDTtoEpoch(end);

        List<AirPollution> airPolList = null;

        try {
            ResponseEntity<String> response = this.restTemplate.getForEntity(requestUrl, String.class);
            
            if (response.getStatusCode() == HttpStatus.OK) {
                airPolList = processAirPolResults( response.getBody() );
            } else {
                throw new IllegalStateException("Data fetch from external air pollution api failed. Status code: " + response.getStatusCode());
            }
        } catch (Exception e) {
            // To Do
        }

        return airPolList;
    }

    public List<AirPollution> processAirPolResults(String apiResponse) {
        ArrayList<AirPollution> airPollutionResults = new ArrayList<>();

        try {
            var parser = new JSONParser();

            var jsonResponse = (JSONObject) parser.parse(apiResponse);

            var resultsArray = (JSONArray) jsonResponse.get("list");

            Iterator<?> iterator = resultsArray.iterator();

            while (iterator.hasNext()) {
                var airPol = new AirPollution();

                var result = (JSONObject) iterator.next();

                var main = (JSONObject) result.get("main");

                var components = (JSONObject) result.get("components");

                airPol.setAqi(Integer.valueOf(main.get("aqi").toString()));

                airPol.setDtTimestamp(converter.epochtoLDT(Long.valueOf(result.get("dt").toString())));

                airPol.setComponents(new Components(
                    Double.valueOf(components.get("co").toString()),
                    Double.valueOf(components.get("no").toString()),
                    Double.valueOf(components.get("no2").toString()),
                    Double.valueOf(components.get("o3").toString()),
                    Double.valueOf(components.get("so2").toString()),
                    Double.valueOf(components.get("pm2_5").toString()),
                    Double.valueOf(components.get("pm10").toString()),
                    Double.valueOf(components.get("nh3").toString()))
                );

                airPollutionResults.add(airPol);
            }

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "There was an error retrieving air pollutions results. Please make sure the values inserted are correct.");
        }

        return airPollutionResults;
    }

}
