package ch.bbw.m320.car.controller;

import ch.bbw.m320.car.dto.CarDto;
import ch.bbw.m320.car.exception.CarNotFoundException;
import ch.bbw.m320.car.service.CarService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static org.springframework.http.ResponseEntity.noContent;

@CrossOrigin("*")
@RequiredArgsConstructor
@RequestMapping("api/cars")
@RestController
public class CarController {

    private final CarService service;

    @GetMapping
    public ResponseEntity<List<CarDto>> getAllCars(@RequestParam(required = false) String brand) {
        List<CarDto> cars = service.getAllCars(brand);
        if (cars.isEmpty()) {
            throw new CarNotFoundException("No Cars found");
        } else {
            return ResponseEntity.ok().body(cars);
        }
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
        return result == null ? ResponseEntity.notFound().build() : ResponseEntity.ok().body(service.editCar(carDto, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCar(@PathVariable UUID id) {
        service.deleteCar(id);
        return noContent().build();
    }
}
