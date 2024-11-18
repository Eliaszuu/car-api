package ch.bbw.m320.car.controller;

import ch.bbw.m320.car.dto.CarDto;
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
    public ResponseEntity<List<CarDto>> getAllCars() {
        List<CarDto> cars = service.getAllCars();
        if (cars.isEmpty()) {
            return noContent().build();
        } else {
            return ResponseEntity.ok().body(cars);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<CarDto> getCarById(@PathVariable UUID id) {
        CarDto carDto = service.getCarById(id);
        return carDto == null ? noContent().build() : ResponseEntity.status(HttpStatus.CREATED).body(service.getCarById(id));
    }

    @PostMapping
    public ResponseEntity<CarDto> createCar(@RequestBody CarDto carDto) {
        return  ResponseEntity.status(HttpStatus.CREATED).body(service.addCar(carDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CarDto> updateCar(@PathVariable UUID id, @RequestBody CarDto carDto) {
        return ResponseEntity.ok().body(service.editCar(carDto, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCar(@PathVariable UUID id) {
        service.deleteCar(id);
        return noContent().build();
    }
}
