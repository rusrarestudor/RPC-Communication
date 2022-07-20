package client;

import java.io.Serializable;

public class Measurement implements Serializable {
    private String value;
    private String day;
    private String hour;

    public Measurement(String value, String day, String hour) {
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
