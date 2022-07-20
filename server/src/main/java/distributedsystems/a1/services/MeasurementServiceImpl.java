package distributedsystems.a1.services;

import distributedsystems.a1.DTO.DeviceDTO;
import distributedsystems.a1.DTO.MeasurementDTO;
import distributedsystems.a1.DTO.builder.MeasurementBuilder;
import distributedsystems.a1.entities.Device;
import distributedsystems.a1.entities.Measurement;
import distributedsystems.a1.entities.Sensor;
import distributedsystems.a1.repositories.MeasurementRepo;
import distributedsystems.a1.repositories.SensorRepo;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class MeasurementServiceImpl implements  MeasurementService{

    private static final Logger LOGGER = LoggerFactory.getLogger(MeasurementService.class);
    private MeasurementRepo measurementRepo;
    private SensorRepo sensorRepo;

    @Autowired
    public MeasurementServiceImpl(MeasurementRepo measurementRepo, SensorRepo sensorRepo) {
        this.measurementRepo = measurementRepo;
        this.sensorRepo = sensorRepo;

    }

    @Override
    public List<MeasurementDTO> findMeasurements() {
        List<Measurement> measurementList = measurementRepo.findAll();
        return measurementList.stream()
                .map(MeasurementBuilder::toMeasurementDTO)
                .collect(Collectors.toList());
    }

    @Override
    public MeasurementDTO findMeasurementById(Long id) {
        Optional<Measurement> measurementOptional = measurementRepo.findById(id);
        if (!measurementOptional.isPresent()) {
            LOGGER.error("Measurement with id {} was not found in db", id);
            throw new ResourceNotFoundException(Measurement.class.getSimpleName() + " with id: " + id);
        }
        return MeasurementBuilder.toMeasurementDTO(measurementOptional.get());
    }

    @Override
    public Long insertMeasurement(MeasurementDTO measurementDTO) {
        Sensor sensor = sensorRepo.findById(measurementDTO.getSensorID()).get();
        Measurement measurement = MeasurementBuilder.toEntity(measurementDTO, sensor);
        measurement = measurementRepo.save(measurement);
        LOGGER.debug("Measurement with id {} was inserted in db", measurement.getMeasurementID());
        return measurement.getMeasurementID();
    }

    @Override
    public void deleteMeasurement(Long measurementID){
        if(!measurementRepo.findById(measurementID).isPresent()){
            LOGGER.error("Measurement with id {} was not found in db", measurementID);
            throw new ResourceNotFoundException(Measurement.class.getSimpleName() + " with id: " + measurementID);
        }
        measurementRepo.deleteById(measurementID);
    }

    @Override
    public List<MeasurementDTO> findMeasurementsBySensor(Sensor sesnor){
        List<Optional<Measurement>> measurementOptionals = measurementRepo.findMeasurementBySensor(sesnor);
        if (measurementOptionals == null) {
            return null;
        }
        List<MeasurementDTO> measurementsDTO = new ArrayList<MeasurementDTO>();

        for(Optional<Measurement> measurementOptional : measurementOptionals){
            measurementsDTO.add(MeasurementBuilder.toMeasurementDTO(measurementOptional.get()));
        }
        return measurementsDTO;
    };

    @Override
    public MeasurementDTO findLast(){
        List<Measurement> measurementList = measurementRepo.findAll();
        return MeasurementBuilder.toMeasurementDTO(measurementList.get(measurementList.size() - 1));
    }

    @Override
    public void generateMeasurementForSensor(Long idSensor, int days){
        List<MeasurementDTO> measurementsDTO = new ArrayList<MeasurementDTO>();
        measurementsDTO = findMeasurementsBySensor(sensorRepo.getById(idSensor));

        if (measurementsDTO != null) {
            for(MeasurementDTO measurementOptional : measurementsDTO){
                deleteMeasurement(measurementOptional.getMeasurementID());
            }
        }
        LocalDateTime todayDate = LocalDateTime.now();
        while(days > 0) {

            for (long h = 0; h <= 23; h++) {

                LocalDateTime pDay = todayDate.minusDays(days -1);
                LocalDateTime eachHour = pDay.minusHours(h);

                Random r = new Random();
                double randomValue = 0 + (100 - 0) * r.nextDouble();

                MeasurementDTO measurementDTO = new MeasurementDTO(null, eachHour, randomValue, idSensor);

                insertMeasurement(measurementDTO);
            }
            days--;
        }


    }

}
