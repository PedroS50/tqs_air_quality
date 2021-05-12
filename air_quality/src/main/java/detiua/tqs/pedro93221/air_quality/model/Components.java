package detiua.tqs.pedro93221.air_quality.model;

import javax.persistence.Embeddable;

import java.util.Objects;

@Embeddable
public class Components {

    private double co;
    private double no;
    private double no2;
    private double o3;
    private double so3;
    private double pm2_5;
    private double pm10;
    private double nh3;

    public Components(double co, double no, double no2, double o3, double so3, double pm2_5, double pm10, double nh3) {
        this.co = co;
        this.no = no;
        this.no2 = no2;
        this.o3 = o3;
        this.so3 = so3;
        this.pm2_5 = pm2_5;
        this.pm10 = pm10;
        this.nh3 = nh3;
    }

    public double getCo() {
        return this.co;
    }

    public void setCo(double co) {
        this.co = co;
    }

    public double getNo() {
        return this.no;
    }

    public void setNo(double no) {
        this.no = no;
    }

    public double getNo2() {
        return this.no2;
    }

    public void setNo2(double no2) {
        this.no2 = no2;
    }

    public double getO3() {
        return this.o3;
    }

    public void setO3(double o3) {
        this.o3 = o3;
    }

    public double getSo3() {
        return this.so3;
    }

    public void setSo3(double so3) {
        this.so3 = so3;
    }

    public double getPm2_5() {
        return this.pm2_5;
    }

    public void setPm2_5(double pm2_5) {
        this.pm2_5 = pm2_5;
    }

    public double getPm10() {
        return this.pm10;
    }

    public void setPm10(double pm10) {
        this.pm10 = pm10;
    }

    public double getNh3() {
        return this.nh3;
    }

    public void setNh3(double nh3) {
        this.nh3 = nh3;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Components)) {
            return false;
        }
        Components components = (Components) o;
        return co == components.co && no == components.no && no2 == components.no2 && o3 == components.o3 && so3 == components.so3 && pm2_5 == components.pm2_5 && pm10 == components.pm10 && nh3 == components.nh3;
    }

    @Override
    public int hashCode() {
        return Objects.hash(co, no, no2, o3, so3, pm2_5, pm10, nh3);
    }

    @Override
    public String toString() {
        return "{" +
            " co='" + getCo() + "'" +
            ", no='" + getNo() + "'" +
            ", no2='" + getNo2() + "'" +
            ", o3='" + getO3() + "'" +
            ", so3='" + getSo3() + "'" +
            ", pm2_5='" + getPm2_5() + "'" +
            ", pm10='" + getPm10() + "'" +
            ", nh3='" + getNh3() + "'" +
            "}";
    }

}