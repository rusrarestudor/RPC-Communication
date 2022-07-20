package distributedsystems.a1.DTO.builder;

import distributedsystems.a1.DTO.DeviceDTO;
import distributedsystems.a1.DTO.MeasurementDTO;
import distributedsystems.a1.DTO.SensorDTO;
import distributedsystems.a1.DTO.UserDTO;
import distributedsystems.a1.entities.Device;
import distributedsystems.a1.entities.Measurement;
import distributedsystems.a1.entities.Sensor;
import distributedsystems.a1.entities.User;
import org.modelmapper.ModelMapper;

public class MeasurementBuilder {
    private MeasurementBuilder() {
    }

    public static MeasurementDTO toMeasurementDTO(Measurement measurement) {
        return new MeasurementDTO(
                measurement.getMeasurementID(),
                measurement.getTimeStamp(),
                measurement.getValue(),
                measurement.getSensor().getSensorID()
                );
    }

    public static Measurement toEntity( MeasurementDTO  measurementDTO, Sensor sensor) {
        return new Measurement(
                measurementDTO.getMeasurementID(),
                measurementDTO.getTimeStamp(),
                measurementDTO.getValue(),
                sensor
        );
    }
}
