package distributedsystems.a1.repositories;


import distributedsystems.a1.entities.Device;
import distributedsystems.a1.entities.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SensorRepo extends JpaRepository<Sensor, Long> {


    Optional<Sensor> findSensorByDevice(Device device);
}
