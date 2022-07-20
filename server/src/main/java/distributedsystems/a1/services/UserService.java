package distributedsystems.a1.services;

import distributedsystems.a1.DTO.DeviceDTO;
import distributedsystems.a1.DTO.SensorDTO;
import distributedsystems.a1.DTO.UserDTO;
import distributedsystems.a1.entities.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    public List<UserDTO> findUsers();
    public UserDTO findPersonById(Long id);
    public Long insertUser(UserDTO userDTO);
    public Long updateUser(Long userId,UserDTO userDTO);
    public void deleteUser(Long userID);
    public UserDTO findUserByEmail(String email);
    public UserDTO findByName(String username);
    public Boolean existsByName(String name);

    public Boolean existsByEmail(String email);
    public double[] getCurrData(Long userId);
    public List<Double> getCurrC(Long userId);
    public List<SensorDTO> getSensorForUser(Long userId);
    public List<DeviceDTO> getDevicesForUser(Long userId);
    public double[] getHisData(Long userId, int month, int day);
    public List<Double> getHistC(Long userId, int month, int day);

}
