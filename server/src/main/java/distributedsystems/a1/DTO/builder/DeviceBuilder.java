package distributedsystems.a1.DTO.builder;

import distributedsystems.a1.DTO.DeviceDTO;
import distributedsystems.a1.DTO.UserDTO;
import distributedsystems.a1.entities.Device;
import distributedsystems.a1.entities.User;
import org.modelmapper.ModelMapper;

public class DeviceBuilder {

    private DeviceBuilder(){ }

    public static DeviceDTO toDeviceDTO(Device device){
        return new DeviceDTO(
                device.getDeviceID(),
                device.getDescription(),
                device.getLocation(),
                device.getAvg(),
                device.getMax(),
                device.getUser().getUserID()
        );
    }

    public static Device toEntity(DeviceDTO deviceDTO, User user) {
        return new Device(
                deviceDTO.getDeviceID(),
                deviceDTO.getDescription(),
                deviceDTO.getLocation(),
                deviceDTO.getAvg(),
                deviceDTO.getMax(),
                user
        );
    }

}
