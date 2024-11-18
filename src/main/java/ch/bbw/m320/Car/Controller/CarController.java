package ch.bbw.m320.Car.Controller;

import ch.bbw.m320.Car.Dto.CarDto;
import ch.bbw.m320.Car.Service.CarService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@CrossOrigin("*")
@RequiredArgsConstructor
@RequestMapping("api/cars")
@RestController
public class CarController {

    private final CarService service;

    @GetMapping
    public List<CarDto> cars() {
        return service.getAllCars();
    }

    @GetMapping("/{id}")
    public CarDto car(@PathVariable UUID id) {
        return service.getCarById(id);
    }

    @PostMapping
    public CarDto createCar(@RequestBody CarDto carDto) {
        return service.addCar(carDto);
    }

    @PutMapping("/{id}")
    public CarDto updateCar(@PathVariable UUID id, @RequestBody CarDto carDto) {
       return service.editCar(carDto,id);
    }

    @DeleteMapping("/{id}")
    public void deleteCar(@PathVariable UUID id) {
        service.deleteCar(id);
    }
}
