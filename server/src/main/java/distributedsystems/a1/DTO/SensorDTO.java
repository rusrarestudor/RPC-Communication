package distributedsystems.a1.DTO;

import distributedsystems.a1.entities.Device;
import org.springframework.hateoas.RepresentationModel;

public class SensorDTO extends RepresentationModel<SensorDTO> {

    public SensorDTO(Long sensorID, String description, double max, Long deviceID) {
        this.sensorID = sensorID;
        this.description = description;
        this.max = max;
        this.device = deviceID;
    }

    private Long sensorID;

    private String description;

    private double max;

    private Long device;

    public Long getSensorID() { return sensorID; }

    public void setSensorID(Long sensorID) { this.sensorID = sensorID; }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

    public double getMax() { return max; }

    public void setMax(double max) { this.max = max; }

    public Long getDeviceID() { return device; }

    public void setDeviceID(Long device) { this.device = device; }
}
