package distributedsystems.a1.DTO.builder;

import distributedsystems.a1.DTO.DeviceDTO;
import distributedsystems.a1.DTO.SensorDTO;
import distributedsystems.a1.DTO.UserDTO;
import distributedsystems.a1.entities.Device;
import distributedsystems.a1.entities.Sensor;
import distributedsystems.a1.entities.User;
import org.modelmapper.ModelMapper;

public class UserBuilder {

    private UserBuilder() {
    }

    public static UserDTO toUserDTO(User user) {
        return new UserDTO(
                user.getUserID(),
                user.getEmail(),
                user.getPassword(),
                user.getName(),
                user.getBirthdate(),
                user.getAddress(),
                user.getRole()
        );
    }

    public static User toEntity(UserDTO userDTO) {
        return new User(
                userDTO.getUserID(),
                userDTO.getEmail(),
                userDTO.getPassword(),
                userDTO.getName(),
                userDTO.getBirthdate(),
                userDTO.getAddress(),
                userDTO.getRole()
        );
    }

}
