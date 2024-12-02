package ch.bbw.m320.car.service;

import ch.bbw.m320.car.dto.CarDto;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class CarService {

    private final List<CarDto> cars = new ArrayList<>();

    @GetMapping
    public List<CarDto> getAllCars() {
        return cars;
    }

    public CarDto getCarById(UUID id) {
        return cars.stream()
                .filter(c -> c.getId().equals(id))
                .findFirst().orElseThrow(() -> new IllegalArgumentException("Car with id " + id + " not found"));
    }

    public CarDto addCar(CarDto car) {
        car.setCreateTimestamp(ZonedDateTime.now());
        car.setId(UUID.randomUUID());
        cars.add(car);
        return car;
    }

    public CarDto editCar(CarDto car, UUID id) {
        return cars.stream()
                .filter(f -> f.getId().equals(id))
                .findFirst()
                .map(existingCar -> {
                    existingCar.setPs(car.getPs());
                    existingCar.setColor(car.getColor());
                    existingCar.setModel(car.getModel());
                    existingCar.setYear(car.getYear());
                    existingCar.setEngine(car.getEngine());
                    existingCar.setBrand(car.getBrand());
                    car.setCreateTimestamp(existingCar.getCreateTimestamp());
                    car.setId(id);
                    return existingCar;
                })
                .orElseThrow(() -> new IllegalArgumentException("Car with id " + id + " not found"));
    }

    public void deleteCar(UUID id) {
        cars.stream().filter(f -> f.getId().equals(id)).findFirst()
                .ifPresent(cars::remove);
    }
}
