package detiua.tqs.pedro93221.air_quality.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import java.util.List;
import java.util.Objects;

public class AirPollutionAnalysis {

    @NotNull
    private List<AirPollution> airPollution;

    @NotNull
    @OneToOne(targetEntity=Location.class, cascade = CascadeType.ALL)
    private Location location;

    public AirPollutionAnalysis() {
    }

    public AirPollutionAnalysis(List<AirPollution> airPollution, Location location) {
        this.airPollution = airPollution;
        this.location = location;
    }

    public List<AirPollution> getAirPollution() {
        return this.airPollution;
    }

    public void setAirPollution(List<AirPollution> airPollution) {
        this.airPollution = airPollution;
    }

    public Location getLocation() {
        return this.location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public AirPollutionAnalysis airPollution(List<AirPollution> airPollution) {
        setAirPollution(airPollution);
        return this;
    }

    public AirPollutionAnalysis location(Location location) {
        setLocation(location);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof AirPollutionAnalysis)) {
            return false;
        }
        AirPollutionAnalysis airPollutionAnalysis = (AirPollutionAnalysis) o;
        return Objects.equals(airPollution, airPollutionAnalysis.airPollution) && Objects.equals(location, airPollutionAnalysis.location);
    }

    @Override
    public int hashCode() {
        return Objects.hash(airPollution, location);
    }

    @Override
    public String toString() {
        return "{" +
            " location='" + getLocation() + "'" +
            ", airPollution='" + getAirPollution() + "'" +
            "}";
    }

}
