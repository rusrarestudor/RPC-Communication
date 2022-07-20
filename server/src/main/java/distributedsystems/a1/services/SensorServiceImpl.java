package distributedsystems.a1.services;

import distributedsystems.a1.DTO.DeviceDTO;
import distributedsystems.a1.DTO.MeasurementDTO;
import distributedsystems.a1.DTO.SensorDTO;
import distributedsystems.a1.DTO.builder.MeasurementBuilder;
import distributedsystems.a1.DTO.builder.SensorBuilder;
import distributedsystems.a1.entities.Device;
import distributedsystems.a1.entities.Measurement;
import distributedsystems.a1.entities.Sensor;
import distributedsystems.a1.repositories.DeviceRepo;
import distributedsystems.a1.repositories.SensorRepo;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SensorServiceImpl implements SensorService{

    private static final Logger LOGGER = LoggerFactory.getLogger(SensorService.class);
    private SensorRepo sensorRepo;
    private DeviceRepo deviceRepo;
    private MeasurementService measurementService;

    @Autowired
    public SensorServiceImpl(SensorRepo sensorRepo, DeviceRepo deviceRepo, MeasurementService measurementService) {
        this.sensorRepo = sensorRepo;
        this.deviceRepo = deviceRepo;
        this.measurementService = measurementService;
    }

    @Override
    public List<SensorDTO> findSensors() {
        List<Sensor> sensorList = sensorRepo.findAll();
        return sensorList.stream()
                .map(SensorBuilder::toSensorDTO)
                .collect(Collectors.toList());
    }

    @Override
    public SensorDTO findSensorById(Long id) {
        Optional<Sensor> sensorOptional = sensorRepo.findById(id);
        if (!sensorOptional.isPresent()) {
            LOGGER.error("Sensor with id {} was not found in db", id);
            throw new ResourceNotFoundException(Sensor.class.getSimpleName() + " with id: " + id);
        }
        return SensorBuilder.toSensorDTO(sensorOptional.get());
    }

    @Override
    public Long insertSensor(SensorDTO sensorDTO) {
        Device device = deviceRepo.findById(sensorDTO.getDeviceID()).get();
        Sensor sensor = SensorBuilder.toEntity(sensorDTO, device);
        sensor = sensorRepo.save(sensor);
        LOGGER.debug("Sensor with id {} was inserted in db", sensor.getSensorID());
        return sensor.getSensorID();
    }

    @Override
    public void deleteSensor(Long sensorID){
        Sensor sensor = sensorRepo.findById(sensorID).get();
        List<MeasurementDTO>  measurementsDTO = measurementService.findMeasurementsBySensor(sensor);
        if(measurementsDTO != null){
            List<Measurement> measurements = measurementsDTO.stream()
                    .map(measurementDTO -> MeasurementBuilder.toEntity(measurementDTO, sensor))
                    .collect(Collectors.toList());
            for(Measurement mes : measurements){
                measurementService.deleteMeasurement(mes.getMeasurementID());
            }
        }
        sensorRepo.delete(sensor);
        LOGGER.debug("Sensor with id {} has benn deleted from db", sensor.getSensorID());
    }

    @Override
    public Long updateSensor(Long deviceId, SensorDTO sensorDTO){
        Optional<Sensor> optionalSensor = sensorRepo.findById(deviceId);
        if(!optionalSensor.isPresent()){
            LOGGER.error("Sensor with id {} was not found in db", deviceId);
            throw new ResourceNotFoundException(Device.class.getSimpleName() + " with id: " + deviceId);

        }

        Sensor sensor = optionalSensor.get();

        sensor.setDescription(sensorDTO.getDescription());
        sensor.setMax(sensorDTO.getMax());

        sensorRepo.save(sensor);

        LOGGER.debug("Sensor with id {} was updated in db", sensor.getSensorID());
        return sensor.getSensorID();
    }

    @Override
    public Long findSensorIDByDevice(Long deviceID) {
        List<SensorDTO> allSensors = findSensors();
        for(SensorDTO senosrDTO : allSensors){
            if(senosrDTO.getDeviceID() == deviceID){
                return senosrDTO.getSensorID();
            }
        }
        return null;
    }
}
