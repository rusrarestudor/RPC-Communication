package distributedsystems.a1.entities;

import javax.persistence.*;

@Entity
@Table(name = "sensor")

public class Sensor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long sensorID;

    @Column(nullable = false, length = 100)
    private String description;

    @Column(nullable = false)
    private double max;

    @OneToOne
    @JoinColumn(name = "deviceID", unique = true)
    private Device device;

    public Sensor(Long sensorID, String description, double max, Device device) {
        this.sensorID = sensorID;
        this.description = description;
        this.max = max;
        this.device = device;
    }

    public Sensor() { }

    public Long getSensorID() { return sensorID; }

    public void setSensorID(Long sensorID) { this.sensorID = sensorID; }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

    public double getMax() { return max; }

    public void setMax(double max) { this.max = max; }

    public Device getDevice() { return device; }

    public void setDevice(Device device) { this.device = device; }
}
