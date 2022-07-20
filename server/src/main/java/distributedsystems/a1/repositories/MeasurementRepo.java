package distributedsystems.a1.repositories;

import distributedsystems.a1.entities.Measurement;
import distributedsystems.a1.entities.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MeasurementRepo extends JpaRepository<Measurement, Long> {

    List<Optional<Measurement>> findMeasurementBySensor(Sensor sensor);
}
