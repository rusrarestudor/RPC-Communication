package distributedsystems.a1.services;

import distributedsystems.a1.DTO.DeviceDTO;
import distributedsystems.a1.DTO.SensorDTO;
import distributedsystems.a1.entities.User;

import java.util.List;

public interface DeviceService {

    public List<DeviceDTO> findDevices();
    public DeviceDTO findDeviceById(Long id);
    public Long insertDevice(DeviceDTO deviceDTO);
    public void deleteDevice(Long id);
    public Long updateDevice(Long deviceId,DeviceDTO deviceDTO);
    public List<DeviceDTO> findDevicesByUser(User user);
}
