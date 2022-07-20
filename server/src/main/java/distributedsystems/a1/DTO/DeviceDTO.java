package distributedsystems.a1.DTO;

import distributedsystems.a1.entities.User;
import org.springframework.hateoas.RepresentationModel;

public class DeviceDTO extends RepresentationModel<DeviceDTO> {

    public DeviceDTO(Long deviceID, String description, String location, Integer avg, Integer max, Long userID) {
        this.deviceID = deviceID;
        this.description = description;
        this.location = location;
        this.avg = avg;
        this.max = max;
        this.userID = userID;
    }

    private Long deviceID;

    private String description;

    private String location;

    private Integer avg;

    private Integer max;

    private Long userID;

    public void setDeviceID(Long deviceID) { this.deviceID = deviceID; }

    public void setDescription(String description) { this.description = description; }

    public void setLocation(String location) { this.location = location; }

    public void setAvg(Integer avg) { this.avg = avg; }

    public void setMax(Integer max) { this.max = max; }

    public void setUser(Long user) { this.userID = user; }

    public Long getDeviceID() { return deviceID; }

    public String getDescription() { return description; }

    public String getLocation() { return location; }

    public Integer getAvg() { return avg; }

    public Integer getMax() { return max; }

    public Long getUserID() { return userID; }

}
