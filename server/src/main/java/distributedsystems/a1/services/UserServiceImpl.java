package distributedsystems.a1.services;

import distributedsystems.a1.DTO.DeviceDTO;
import distributedsystems.a1.DTO.MeasurementDTO;
import distributedsystems.a1.DTO.SensorDTO;
import distributedsystems.a1.DTO.UserDTO;
import distributedsystems.a1.DTO.builder.DeviceBuilder;
import distributedsystems.a1.DTO.builder.UserBuilder;
import distributedsystems.a1.entities.Device;
import distributedsystems.a1.entities.User;
import distributedsystems.a1.repositories.DeviceRepo;
import distributedsystems.a1.repositories.UserRepo;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService{

    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);
    private UserRepo userRepo;
    private DeviceService deviceService;
    private  SensorService sensorService;
    private MeasurementService measurementService;

    @Autowired
    public UserServiceImpl(UserRepo userRepo, DeviceService deviceService, SensorService sensorService, MeasurementService measurementService) {

        this.userRepo = userRepo;
        this.deviceService = deviceService;
        this.sensorService = sensorService;
        this.measurementService = measurementService;

    }

    @Override
    public List<UserDTO> findUsers() {
        List<User> userList = userRepo.findAll();
        return userList.stream()
                .map(UserBuilder::toUserDTO)
                .collect(Collectors.toList());
    }

    @Override
    public UserDTO findPersonById(Long id) {
        Optional<User> userOptional = userRepo.findById(id);
        if (!userOptional.isPresent()) {
            LOGGER.error("User with id {} was not found in db", id);
            throw new ResourceNotFoundException(User.class.getSimpleName() + " with id: " + id);
        }
        return UserBuilder.toUserDTO(userOptional.get());

    }

    @Override
    public Long insertUser(UserDTO userDTO) {
        User user = UserBuilder.toEntity(userDTO);
        if(user.getAddress() == null){
            user.setAddress("null");
        }
        if(user.getBirthdate() == null){
            user.setBirthdate("null");
        }

        user = userRepo.save(user);
        LOGGER.debug("User with id {} was inserted in db", user.getUserID());
        return user.getUserID();
    }

    @Override
    public void deleteUser(Long userID){
        User user = userRepo.findById(userID).get();
        List<DeviceDTO>  devicesDTO = deviceService.findDevicesByUser(user);
        if(devicesDTO != null){
            List<Device> devices = devicesDTO.stream()
                    .map(deviceDTO -> DeviceBuilder.toEntity(deviceDTO, user))
                    .collect(Collectors.toList());
            for(Device dev : devices){
                deviceService.deleteDevice(dev.getDeviceID());
            }
        }
        userRepo.delete(user);
        LOGGER.debug("Client with id {} has benn deleted from db", user.getUserID());
    }
    @Override
    public UserDTO findUserByEmail(String email){
        User user= userRepo.findByEmail(email);
        return UserBuilder.toUserDTO(user);
    }

    @Override
    public UserDTO findByName(String name){
        Optional<User> userOpt = userRepo.findByName(name);
        User user = userOpt.get();
        return UserBuilder.toUserDTO(user);
    }

    @Override
    public Long updateUser(Long userId, UserDTO userDTO){
        Optional<User> optionalUser=userRepo.findById(userId);
        if(!optionalUser.isPresent()){
            LOGGER.error("User with id {} was not found in db", userId);
            throw new ResourceNotFoundException(User.class.getSimpleName() + " with id: " + userId);

        }

        User user = optionalUser.get();
        user.setPassword(userDTO.getPassword());
        user.setEmail(userDTO.getEmail());
        user.setName(userDTO.getName());
        user.setBirthdate(userDTO.getBirthdate());
        user.setRole(userDTO.getRole());
        user.setAddress(userDTO.getAddress());

        userRepo.save(user);

        LOGGER.debug("User with id {} was updated in db", userId);
        return user.getUserID();
    }

    public Boolean existsByName(String name){
        return userRepo.existsByName(name);
    }

    public Boolean existsByEmail(String email){
        return userRepo.existsByEmail(email);
    }


    public List<DeviceDTO> getDevicesForUser(Long userId){
        List<DeviceDTO> deviceDTOS = deviceService.findDevices();

        List<DeviceDTO> devicesForClient = new ArrayList<>();

        for(DeviceDTO deviceDTO:deviceDTOS){
            if(deviceDTO.getUserID() != null) {
                if (deviceDTO.getUserID().equals(userId)) {
                    devicesForClient.add(deviceDTO);
                }
            }
        }
        return  devicesForClient;
    }

    public List<SensorDTO> getSensorForUser(Long userId){
        List<DeviceDTO> deviceDTOS = deviceService.findDevices();
        List<SensorDTO> sensorDTOS = sensorService.findSensors();

        List<SensorDTO> sensorsForClient = new ArrayList<>();

        for(DeviceDTO deviceDTO:deviceDTOS){
            if(deviceDTO.getUserID() != null) {
                if (deviceDTO.getUserID().equals(userId)) {
                    for(SensorDTO sensorDTO:sensorDTOS){
                        if(sensorDTO.getDeviceID().equals(deviceDTO.getDeviceID())){
                            sensorsForClient.add(sensorDTO);
                        }
                    }
                }
            }
        }
        return  sensorsForClient;
    }


    public double[] getCurrData(Long userId){
        double[] energyConsumption = new double[24];
        List<SensorDTO> sensors = getSensorForUser(userId);
        List<MeasurementDTO> measurements = measurementService.findMeasurements();
        for(MeasurementDTO measurementDTO:measurements){
            for(SensorDTO sensorDTO:sensors){
                if(measurementDTO.getSensorID() == sensorDTO.getSensorID()) {
                    if ((measurementDTO.getTimeStamp().getDayOfMonth() == LocalDateTime.now().getDayOfMonth()) && (measurementDTO.getTimeStamp().getMonth() == LocalDateTime.now().getMonth())){
                        int hour = measurementDTO.getTimeStamp().getHour();
                        if (measurementDTO.getTimeStamp().getMinute() > 0) {
                            hour++;
                        }
                        if(hour == 24){
                            hour = 0;
                        }
                        energyConsumption[hour] = energyConsumption[hour] + measurementDTO.getValue();
                    }
                }
            }
        }
        return  energyConsumption;
    }

    public double[] getHisData(Long userId, int month, int day){
        double[] energyConsumption = new double[24];
        List<SensorDTO> sensors = getSensorForUser(userId);
        List<MeasurementDTO> measurements = measurementService.findMeasurements();
        for(MeasurementDTO measurementDTO:measurements){
            for(SensorDTO sensorDTO:sensors){
                if(measurementDTO.getSensorID() == sensorDTO.getSensorID()) {
                    if ((measurementDTO.getTimeStamp().getDayOfMonth() == day) && (measurementDTO.getTimeStamp().getMonthValue() == month)){
                        int hour = measurementDTO.getTimeStamp().getHour();
                        if (measurementDTO.getTimeStamp().getMinute() > 0) {
                            hour++;
                        }
                        if(hour == 24){
                            hour = 0;
                        }
                        energyConsumption[hour] = energyConsumption[hour] + measurementDTO.getValue();
                    }
                }
            }
        }
        return  energyConsumption;
    }

    public List<Double> getCurrC(Long userId){
        double[] aux = getCurrData(userId);
        List<Double> currentData = new ArrayList<>();
        for (double value : aux) {
            currentData.add(value);
        }
        return  currentData;
    }

    public List<Double> getHistC(Long userId, int month, int day){
        double[] aux = getHisData(userId, month, day);
        List<Double> historicalData = new ArrayList<>();
        for (double value : aux) {
            historicalData.add(value);
        }
        return historicalData;
    }

}
