package distributedsystems.a1.controller;

import distributedsystems.a1.DTO.DeviceDTO;
import distributedsystems.a1.DTO.SensorDTO;
import distributedsystems.a1.services.SensorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@CrossOrigin
@RequestMapping(value = "/sensor")
public class SensorController {

    private final SensorService sensorService;

    @Autowired
    public SensorController(SensorService sensorService) { this.sensorService = sensorService; }

    @GetMapping()
    public ResponseEntity<List<SensorDTO>> getSensors() {
        List<SensorDTO> dtos = sensorService.findSensors();
        for (SensorDTO dto : dtos) {
            Link sensorLink = linkTo(methodOn(SensorController.class).getSensor(dto.getSensorID())).withRel("sensorDetails");
            dto.add(sensorLink);
        }
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<Long> insertSensor(@Valid @RequestBody SensorDTO sensorDTO) {
        Long sensorID = sensorService.insertSensor(sensorDTO);
        return new ResponseEntity<>(sensorID, HttpStatus.CREATED);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<SensorDTO> getSensor(@PathVariable("id") Long sensorId) {
        SensorDTO dto = sensorService.findSensorById(sensorId);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Long> deleteSensor(@PathVariable("id") Long sensorID) {
        sensorService.deleteSensor(sensorID);
        return new ResponseEntity<>(sensorID, HttpStatus.OK);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<SensorDTO> updateSensor(@PathVariable("id") Long sensorID, @Valid @RequestBody SensorDTO sensorDTO) {
        sensorService.updateSensor(sensorID, sensorDTO);
        return new ResponseEntity<>( HttpStatus.OK);
    }
}
