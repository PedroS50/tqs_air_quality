package detiua.tqs.pedro93221.air_quality.model;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

import java.util.Objects;

@Embeddable
public class Coordinates {

    @NotNull
    private double latitude;

    @NotNull
    private double longitude;

    public Coordinates(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getLatitude() {
        return this.latitude;
    }

    public double getLongitude() {
        return this.longitude;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Coordinates)) {
            return false;
        }
        Coordinates coordinates = (Coordinates) o;
        return latitude == coordinates.latitude && longitude == coordinates.longitude;
    }

    @Override
    public int hashCode() {
        return Objects.hash(latitude, longitude);
    }

}