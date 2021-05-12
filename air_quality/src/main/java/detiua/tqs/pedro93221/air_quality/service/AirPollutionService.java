package detiua.tqs.pedro93221.air_quality.service;

import detiua.tqs.pedro93221.air_quality.model.*;

import org.springframework.web.client.RestTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import java.io.FileReader;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;

@Service
@Transactional
public class AirPollutionService {

    private final String openweathermap_apiKey = loadOWMKey();
    private final String geocoding_apiKey = loadGKey();

    private final String FORECAST_API_URL = "http://api.openweathermap.org/data/2.5/air_pollution/forecast";
    private final String CURRENT_API_URL = "http://api.openweathermap.org/data/2.5/air_pollution";
    private final String HISTORICAL_API_URL = "http://api.openweathermap.org/data/2.5/air_pollution/history";
    private final String GEOCODING_API_URL = "https://maps.googleapis.com/maps/api/geocode/json";

    private RestTemplate restTemplate = new RestTemplate();

    private String loadOWMKey() {
        String path = "src/main/java/detiua/tqs/pedro93221/air_quality/keys.txt";

        try {
            JSONParser parser = new JSONParser();

            JSONObject jsonObject = (JSONObject) parser.parse( new FileReader(path) );

            return (String) jsonObject.get("openweathermap");

        } catch (Exception e) {
            return "";

        }
    }

    private String loadGKey() {
        String path = "src/main/java/detiua/tqs/pedro93221/air_quality/keys.txt";

        try {
            JSONParser parser = new JSONParser();

            JSONObject jsonObject = (JSONObject) parser.parse( new FileReader(path) );

            return (String) jsonObject.get("geocoding");

        } catch (Exception e) {
            return "";

        }
    }

    public AirPollutionAnalysis getCurrentAirPollution(Location location) {
        String requestUrl = CURRENT_API_URL + "?appid=" + openweathermap_apiKey;
        requestUrl += "&lat=" + location.getCoordinates().getLatitude();
        requestUrl += "&lon=" + location.getCoordinates().getLongitude();

        String response = restTemplate.getForObject(requestUrl, String.class);

        List<AirPollution> results = processAirPolResults(response);

        return new AirPollutionAnalysis(results, location);
    }

    public AirPollutionAnalysis getForecastAirPollution(Location location) {
        String requestUrl = FORECAST_API_URL + "?appid=" + openweathermap_apiKey;
        requestUrl += "&lat=" + location.getCoordinates().getLatitude();
        requestUrl += "&lon=" + location.getCoordinates().getLongitude();

        String response = restTemplate.getForObject(requestUrl, String.class);

        List<AirPollution> results = processAirPolResults(response);

        return new AirPollutionAnalysis(results, location);
    }

    public AirPollutionAnalysis getHistoricalAirPollution(Location location, LocalDateTime start, LocalDateTime end) {
        String requestUrl = HISTORICAL_API_URL + "?appid=" + openweathermap_apiKey;
        requestUrl += "&lat=" + location.getCoordinates().getLatitude();
        requestUrl += "&lon=" + location.getCoordinates().getLongitude();
        requestUrl += "&start=" + LDTtoEpoch(start);
        requestUrl += "&end=" + LDTtoEpoch(end);

        String response = restTemplate.getForObject(requestUrl, String.class);

        List<AirPollution> results = processAirPolResults(response);

        return new AirPollutionAnalysis(results, location);
    }

    public Location getLocation(String address) {
        String requestUrl = GEOCODING_API_URL + "?key=" + geocoding_apiKey + "&address=" + address;

        String response = restTemplate.getForObject(requestUrl, String.class);

        Location newLocation = new Location(address);
        try {
            JSONParser parser = new JSONParser();
            
            JSONObject jsonResponse = (JSONObject) parser.parse( response );

            JSONArray resultsArray = (JSONArray) jsonResponse.get("results");

            JSONObject result = (JSONObject) resultsArray.get(0);

            JSONObject geometry = (JSONObject) result.get("geometry");

            JSONObject location = (JSONObject) geometry.get("location");

            newLocation.setCoordinates( new Coordinates( Double.valueOf(location.get("lat").toString()), Double.valueOf(location.get("lng").toString()) ) );

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Invalid location.");
        }
        
        return newLocation;
    }

    public List<AirPollution> processAirPolResults(String apiResponse) {
        ArrayList<AirPollution> airPollutionResults = new ArrayList<>();
        try {
            JSONParser parser = new JSONParser();
            
            JSONObject jsonResponse = (JSONObject) parser.parse( apiResponse );

            JSONArray resultsArray = (JSONArray) jsonResponse.get("list");

            Iterator<?> iterator = resultsArray.iterator();

            while (iterator.hasNext()) {
                AirPollution airPol = new AirPollution();

                JSONObject result = (JSONObject) iterator.next();

                JSONObject main = (JSONObject) result.get("main");

                JSONObject components = (JSONObject) result.get("components");

                airPol.setAqi( Integer.valueOf(main.get("aqi").toString()) );

                airPol.setDtTimestamp( EpochtoLDT( Long.valueOf(result.get("dt").toString()) ) );

                airPol.setComponents( new Components(
                    Double.valueOf( components.get("co").toString()     ),
                    Double.valueOf( components.get("no").toString()     ),
                    Double.valueOf( components.get("no2").toString()    ),
                    Double.valueOf( components.get("o3").toString()     ),
                    Double.valueOf( components.get("so2").toString()    ),
                    Double.valueOf( components.get("pm2_5").toString()  ),
                    Double.valueOf( components.get("pm10").toString()   ),
                    Double.valueOf( components.get("nh3").toString()    ))
                );

                airPollutionResults.add(airPol);
            }

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "There was an error retrieving air pollutions results. Please make sure the value inserted are correct.");
        }

        return airPollutionResults;
    }

    public long LDTtoEpoch(LocalDateTime timestamp) {
        return timestamp.toEpochSecond(ZoneOffset.UTC);
    }

    public LocalDateTime EpochtoLDT(long timestamp) {
        return LocalDateTime.ofEpochSecond(timestamp, 0, ZoneOffset.UTC);
    }
}