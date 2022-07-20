package distributedsystems.a1.services;


import distributedsystems.a1.DTO.SensorDTO;
import distributedsystems.a1.entities.Device;

import java.util.List;

public interface SensorService {
    public List<SensorDTO> findSensors();
    public SensorDTO findSensorById(Long id);
    public Long insertSensor(SensorDTO sensorDTO);
    public void deleteSensor(Long sensorID);
    public Long updateSensor(Long deviceId, SensorDTO sensorDTO);
    public Long findSensorIDByDevice(Long deviceID);
}
