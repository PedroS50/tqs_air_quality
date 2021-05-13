package detiua.tqs.pedro93221.air_quality.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import java.util.Objects;

@Entity
@Table(name = "location")
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Embedded
    @NotNull
    private Coordinates coordinates;

    @NotNull
    private String address;

    public Location() {
    }

    public Location(String address) {
        this.address = address;
    }

    public Location(Coordinates coordinates, String address) {
        this.coordinates = coordinates;
        this.address = address;
    }

    public Coordinates getCoordinates() {
        return this.coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Location)) {
            return false;
        }
        Location location = (Location) o;
        return Objects.equals(coordinates, location.coordinates) && Objects.equals(address, location.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(coordinates, address);
    }

    @Override
    public String toString() {
        return "{" + " coordinates='" + getCoordinates() + "'" + ", address='" + getAddress() + "'" + "}";
    }

}
