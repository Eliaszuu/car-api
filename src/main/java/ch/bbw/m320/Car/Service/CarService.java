package ch.bbw.m320.Car.Service;

import ch.bbw.m320.Car.Dto.CarDto;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CarService {

    private List<CarDto> cars = new ArrayList<>();
    private long currentId = 1;

    @GetMapping
    public List<CarDto> getAllCars() {
        return cars;
    }

    public CarDto getCarById(Integer id) {
        Optional<CarDto> car = cars.stream()
                .filter(c -> c.Id().equals(id))
                .findFirst();
        return car.orElse(null);
    }

    public CarDto addCar(CarDto car) {
      car.
    }

    public CarDto editCar(CarDto car) {
        if (cars.containsKey(car.getId())) {
            cars.put(car.getId(), car);
            return car;
        }
        return null;
    }

    // Delete a car by ID
    public void deleteCar(int carId) {
        cars.remove(carId);
    }
}
