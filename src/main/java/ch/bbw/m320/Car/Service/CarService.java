package ch.bbw.m320.Car.Service;

import ch.bbw.m320.Car.Dto.CarDto;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
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
        cars.add(car);
        return car;
    }

    public CarDto editCar(CarDto car,UUID id) {
        cars.stream().filter(f -> f.getId().equals(id)).findFirst()
                .ifPresent(f -> {
                    f.setPs(car.getPs());
                    f.setColor(car.getColor());
                    f.setModel(car.getModel());
                    f.setYear(car.getYear());
                    f.setEngine(car.getEngine());
                    f.setBrand(car.getBrand());
                });
        return car;
    }

    // Delete a car by ID
    public void deleteCar(UUID id) {
        cars.stream().filter(f -> f.getId().equals(id)).findFirst()
                .ifPresent(f -> cars.remove(f));
    }
}
