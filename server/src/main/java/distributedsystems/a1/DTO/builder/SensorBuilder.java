package distributedsystems.a1.DTO.builder;

import distributedsystems.a1.DTO.DeviceDTO;
import distributedsystems.a1.DTO.SensorDTO;
import distributedsystems.a1.DTO.UserDTO;
import distributedsystems.a1.entities.Device;
import distributedsystems.a1.entities.Measurement;
import distributedsystems.a1.entities.Sensor;
import distributedsystems.a1.entities.User;
import org.modelmapper.ModelMapper;

public class SensorBuilder {

    private SensorBuilder(){}

    public static SensorDTO toSensorDTO(Sensor sensor){
        return new SensorDTO(
                sensor.getSensorID(),
                sensor.getDescription(),
                sensor.getMax(),
                sensor.getDevice().getDeviceID()
        );
    }

    public static Sensor toEntity(SensorDTO sensorDTO, Device device) {
        return new Sensor(
                sensorDTO.getSensorID(),
                sensorDTO.getDescription(),
                sensorDTO.getMax(),
                device
        );
    }
}
