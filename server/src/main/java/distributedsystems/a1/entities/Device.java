package distributedsystems.a1.entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "devices")
public class Device implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long deviceID;

    @Column(nullable = false, length = 110)
    private String description;

    @Column(nullable = false, length = 110)
    private String location;

    @Column(nullable = false, length = 110)
    private Integer avg;

    @Column(nullable = false)
    private Integer max;

    @ManyToOne
    @JoinColumn(name = "userID")
    private User user;

    public Device(Long deviceID, String description, String location, Integer avg, Integer max, User user) {
        this.deviceID = deviceID;
        this.description = description;
        this.location = location;
        this.avg = avg;
        this.max = max;
        this.user = user;
    }

    public Device() {}

    public void setDeviceID(Long deviceID) { this.deviceID = deviceID; }

    public void setDescription(String description) { this.description = description; }

    public void setLocation(String location) { this.location = location; }

    public void setAvg(Integer avg) { this.avg = avg; }

    public void setMax(Integer max) { this.max = max; }

    public void setUser(User user) { this.user = user; }

    public Long getDeviceID() { return deviceID; }

    public String getDescription() { return description; }

    public String getLocation() { return location; }

    public Integer getAvg() { return avg; }

    public Integer getMax() { return max; }

    public User getUser() { return user; }


}
