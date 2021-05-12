package detiua.tqs.pedro93221.air_quality.cache;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import detiua.tqs.pedro93221.air_quality.model.*;

public class Cache {

    private String type;
    private int ttl;
    private Map<Location, AirPollutionAnalysis> data;
    private Map<Location, Long> expirationDates;    

    public Cache(int ttl, String type) {
        this.ttl = ttl;
        this.type = type;
        data = new HashMap<>();
        expirationDates = new HashMap<>();
    }

    public boolean exists(Location location) {
        return data.containsKey(location) && System.currentTimeMillis() < expirationDates.get(location);
    }

    public AirPollutionAnalysis getAnalysis(Location location) {
        return data.get(location);
    }

    public void setAnalysis(Location location, AirPollutionAnalysis airPollutionAnalysis) {
        data.put(location, airPollutionAnalysis);
        expirationDates.put(location, System.currentTimeMillis()+this.ttl);
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getTtl() {
        return this.ttl;
    }

    public void setTtl(int ttl) {
        this.ttl = ttl;
    }

    public Map<Location,AirPollutionAnalysis> getData() {
        return this.data;
    }

    public void setData(Map<Location,AirPollutionAnalysis> data) {
        this.data = data;
    }

    public Map<Location,Long> getExpirationDates() {
        return this.expirationDates;
    }

    public void setExpirationDates(Map<Location,Long> expirationDates) {
        this.expirationDates = expirationDates;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Cache)) {
            return false;
        }
        Cache cache = (Cache) o;
        return Objects.equals(type, cache.type) && ttl == cache.ttl && Objects.equals(data, cache.data) && Objects.equals(expirationDates, cache.expirationDates);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, ttl, data, expirationDates);
    }

    @Override
    public String toString() {
        return "{" +
            " type='" + getType() + "'" +
            ", ttl='" + getTtl() + "'" +
            ", data='" + getData() + "'" +
            ", expirationDates='" + getExpirationDates() + "'" +
            "}";
    }

}
