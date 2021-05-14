package detiua.tqs.pedro93221.air_quality.repository;

import detiua.tqs.pedro93221.air_quality.model.*;

import org.springframework.stereotype.Repository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.client.RestTemplate;


import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import java.io.FileReader;


@Repository
public class LocationRepository {

    private final String GEOCODING_API_URL = "https://maps.googleapis.com/maps/api/geocode/json";

    private final String geocoding_apiKey = loadGKey();

    private RestTemplate restTemplate = new RestTemplate();

    private String loadGKey() {
        String path = "src/main/java/detiua/tqs/pedro93221/air_quality/keys.txt";

        try {
            JSONParser parser = new JSONParser();

            JSONObject jsonObject = (JSONObject) parser.parse(new FileReader(path));

            return (String) jsonObject.get("geocoding");

        } catch (Exception e) {
            return "";

        }
    }

    public Location getLocation(String address) {
        String requestUrl = GEOCODING_API_URL + "?key=" + geocoding_apiKey + "&address=" + address;
        String response = null;

        ResponseEntity<String> responseEnt = this.restTemplate.getForEntity(requestUrl, String.class);
            
        if (responseEnt.getStatusCode() == HttpStatus.OK) {
            response = responseEnt.getBody();
        } else {
            throw new IllegalStateException("Data fetch from external geocoding api failed. Status code: " + responseEnt.getStatusCode());
        }

        Location newLocation = null;
        try {

            JSONParser parser = new JSONParser();

            JSONObject jsonResponse = (JSONObject) parser.parse(response);

            JSONArray resultsArray = (JSONArray) jsonResponse.get("results");

            JSONObject result = (JSONObject) resultsArray.get(0);

            JSONObject geometry = (JSONObject) result.get("geometry");

            JSONObject location = (JSONObject) geometry.get("location");

            newLocation = new Location(address);
            newLocation.setCoordinates(new Coordinates(Double.valueOf(location.get("lat").toString()),
                    Double.valueOf(location.get("lng").toString())));

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Invalid location.");
        }

        return newLocation;
    }
}