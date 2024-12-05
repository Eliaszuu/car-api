package ch.bbw.m320.car.service;

import ch.bbw.m320.car.dto.CarDto;
import ch.bbw.m320.car.exception.CarNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class CarService {

    private final List<CarDto> cars = new ArrayList<>();

    @GetMapping
    public List<CarDto> getAllCars(String brand) {
        if (brand != null) {
            return cars.stream()
                    .filter(car -> car.getBrand().equalsIgnoreCase(brand))
                    .collect(Collectors.toList());
        }
        return cars;
    }

    public CarDto getCarById(UUID id) {
        return cars.stream()
                .filter(c -> c.getId().equals(id))
                .findFirst().orElseThrow(() -> new CarNotFoundException("Car with id " + id + " not found"));
    }

    public CarDto addCar(CarDto car) {
        if (car.getId() == null) {
            car.setId(UUID.randomUUID());
        }
        if (car.getCreateTimestamp() == null) {
            car.setCreateTimestamp(ZonedDateTime.now());
        }
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
                .orElseThrow(() -> new CarNotFoundException("Car with id " + id + " not found"));
    }


    public void deleteCar(UUID id) {
        cars.stream().filter(f -> f.getId().equals(id)).findFirst()
                .ifPresent(cars::remove);
    }
}
