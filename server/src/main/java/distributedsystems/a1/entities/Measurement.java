package distributedsystems.a1.entities;


import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "measurement")

public class Measurement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long measurementID;

    @Column(nullable = false, length = 100)
    private LocalDateTime timeStamp;


    @Column(nullable = false)
    private double value;

    @ManyToOne
    @JoinColumn(name = "sensorID")
    private Sensor sensor;

    public Measurement(Long measurementID, LocalDateTime timeStamp, double value, Sensor sensor) {
        this.measurementID = measurementID;
        this.timeStamp = timeStamp;
        this.value = value;
        this.sensor = sensor;
    }

    public Measurement() { }

    public Long getMeasurementID() { return measurementID; }

    public void setMeasurementID(Long measurementID) { this.measurementID = measurementID; }

    public LocalDateTime getTimeStamp() { return timeStamp; }

    public void setTimeStamp(LocalDateTime timeStamp) { this.timeStamp = timeStamp; }

    public double getValue() { return value; }

    public void setValue(double value) { this.value = value; }

    public Sensor getSensor() { return sensor; }

    public void setSensor(Sensor sensor) { this.sensor = sensor; }
}
