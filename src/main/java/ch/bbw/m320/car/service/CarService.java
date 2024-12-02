package ch.bbw.m320.car.service;

import ch.bbw.m320.car.dto.CarDto;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class CarService {

    private List<CarDto> cars = new ArrayList<>();

    @GetMapping
    public List<CarDto> getAllCars() {
        return cars;
    }

    public CarDto getCarById(UUID id) {
        Optional<CarDto> car = cars.stream()
                .filter(c -> c.getId().equals(id))
                .findFirst();
        return car.orElse(null);
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
                    return existingCar; // Return the updated car
                })
                .orElse(null); // Return null if no car with the given ID is found
    }


    public void deleteCar(UUID id) {
        cars.stream().filter(f -> f.getId().equals(id)).findFirst()
                .ifPresent(f -> cars.remove(f));
    }
}
