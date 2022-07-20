package distributedsystems.a1.controller;

import distributedsystems.a1.DTO.MeasurementDTO;
import distributedsystems.a1.services.MeasurementService;
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
@RequestMapping(value = "/measurement")
public class MeasurementController {

    private final MeasurementService measurementService;

    @Autowired
    public MeasurementController(MeasurementService measurementService) { this.measurementService = measurementService; }

    @GetMapping()
    public ResponseEntity<List<MeasurementDTO>> getMeasurements() {
        List<MeasurementDTO> dtos = measurementService.findMeasurements();
        for (MeasurementDTO dto : dtos) {
            Link measurementLink = linkTo(methodOn(MeasurementController.class).getMeasurement(dto.getMeasurementID())).withRel("measurementDetails");
            dto.add(measurementLink);
        }
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<Long> insertMeasurement(@Valid @RequestBody MeasurementDTO measurementDTO) {
        Long measurementID = measurementService.insertMeasurement(measurementDTO);
        return new ResponseEntity<>(measurementID, HttpStatus.CREATED);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<MeasurementDTO> getMeasurement(@PathVariable("id") Long measurementId) {
        MeasurementDTO dto = measurementService.findMeasurementById(measurementId);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }
}
