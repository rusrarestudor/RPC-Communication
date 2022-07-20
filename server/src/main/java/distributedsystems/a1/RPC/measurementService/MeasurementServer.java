package distributedsystems.a1.RPC.measurementService;

import java.io.Serializable;

import static java.lang.String.format;

public class MeasurementServer implements Serializable {
    private String value;
    private String day;
    private String hour;

    public MeasurementServer(String value, String day, String hour) {
        this.value = value;
        this.day = day;
        this.hour = hour;
    }

    public String getDay() {
        return day;
    }

    public String getHour() {return hour;}

    public String getValue() {
        return value;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public void setHour(String hour) {this.hour = hour;}

    public void setValue(String value) {
        this.value = value;
    }
}
