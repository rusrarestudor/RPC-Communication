package distributedsystems.a1.repositories;

import distributedsystems.a1.entities.Device;
import distributedsystems.a1.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DeviceRepo extends JpaRepository<Device, Long> {

    List<Optional<Device>> findDevicesByUser(User user);
}
