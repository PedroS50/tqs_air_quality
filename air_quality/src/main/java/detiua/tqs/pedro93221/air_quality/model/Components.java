package detiua.tqs.pedro93221.air_quality.model;

import javax.persistence.Embeddable;

import java.util.Objects;

@Embeddable
public class Components {

    private Double co;
    private Double no;
    private Double no2;
    private Double o3;
    private Double so3;
    private Double pm2_5;
    private Double pm10;
    private Double nh3;

    public Components(Double co, Double no, Double no2, Double o3, Double so3, Double pm2_5, Double pm10, Double nh3) {
        this.co = co;
        this.no = no;
        this.no2 = no2;
        this.o3 = o3;
        this.so3 = so3;
        this.pm2_5 = pm2_5;
        this.pm10 = pm10;
        this.nh3 = nh3;
    }

    public Double getCo() {
        return this.co;
    }

    public Double getNo() {
        return this.no;
    }

    public Double getNo2() {
        return this.no2;
    }

    public Double getO3() {
        return this.o3;
    }

    public Double getSo3() {
        return this.so3;
    }

    public Double getPm2_5() {
        return this.pm2_5;
    }

    public Double getPm10() {
        return this.pm10;
    }

    public Double getNh3() {
        return this.nh3;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Components)) {
            return false;
        }
        var components = (Components) o;
        return co.equals(components.co) && no.equals(components.no) && no2.equals(components.no2) && o3.equals(components.o3
               ) && so3.equals(components.so3) && pm2_5.equals(components.pm2_5) && pm10.equals(components.pm10
               ) && nh3.equals(components.nh3);
    }

    @Override
    public int hashCode() {
        return Objects.hash(co, no, no2, o3, so3, pm2_5, pm10, nh3);
    }

}