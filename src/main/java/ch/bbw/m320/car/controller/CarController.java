package ch.bbw.m320.car.controller;

import static org.springframework.http.ResponseEntity.noContent;

import java.util.List;
import java.util.UUID;

import ch.bbw.m320.car.dto.CarDto;
import ch.bbw.m320.car.exception.CarNotFoundException;
import ch.bbw.m320.car.service.CarService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("*")
@RequiredArgsConstructor
@RequestMapping("api/cars")
@RestController
public class CarController {

    private final CarService service;

    @GetMapping
    public List<CarDto> getAllCars(@RequestParam(required = false) String brand) {
        List<CarDto> cars = service.getAllCars(brand);
        if (cars.isEmpty()) { // this is uncommon: for list-responses prefer returning an empty list instead.
            throw new CarNotFoundException("No Cars found");
        }
        return cars;
    }

    @GetMapping("/{id}")
    public ResponseEntity<CarDto> getCarById(@PathVariable UUID id) {
        CarDto carDto = service.getCarById(id);
        return carDto == null ? noContent().build() : ResponseEntity.status(HttpStatus.OK).body(service.getCarById(id));
    }

    @PostMapping
    public ResponseEntity<CarDto> createCar(@RequestBody CarDto carDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.addCar(carDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CarDto> updateCar(@PathVariable UUID id, @RequestBody CarDto carDto) {
        CarDto result = service.editCar(carDto, id);
        // prefer the exception driven approach (CarNotFoundException) here, as this allows getting rid of ResponseEntity, too
        return result == null ? ResponseEntity.notFound().build() : ResponseEntity.ok().body(service.editCar(carDto, id));
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deleteCar(@PathVariable UUID id) {
        service.deleteCar(id);
    }
}
