package detiua.tqs.pedro93221.air_quality.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class AirPollution {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    public int aqi;

    @NotNull
    public LocalDateTime dtTimestamp;

    @Embedded
    @NotNull
    private Components components;

    public AirPollution() {}

    public AirPollution(int aqi, LocalDateTime dtTimestamp, Components components) {
        this.aqi = aqi;
        this.dtTimestamp = dtTimestamp;
        this.components = components;
    }

    public int getAqi() {
        return this.aqi;
    }

    public void setAqi(int aqi) {
        this.aqi = aqi;
    }

    public LocalDateTime getDtTimestamp() {
        return this.dtTimestamp;
    }

    public void setDtTimestamp(LocalDateTime dtTimestamp) {
        this.dtTimestamp = dtTimestamp;
    }

    public Components getComponents() {
        return this.components;
    }

    public void setComponents(Components components) {
        this.components = components;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof AirPollution)) {
            return false;
        }
        AirPollution airPollution = (AirPollution) o;
        return aqi == airPollution.aqi && dtTimestamp == airPollution.dtTimestamp && Objects.equals(components, airPollution.components);
    }

    @Override
    public int hashCode() {
        return Objects.hash(aqi, dtTimestamp, components);
    }

    @Override
    public String toString() {
        return "{" +
            " aqi='" + getAqi() + "'" +
            ", dtTimestamp='" + getDtTimestamp() + "'" +
            ", components='" + getComponents() + "'" +
            "}";
    }

}