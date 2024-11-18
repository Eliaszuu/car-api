package ch.bbw.m320.Car.Controller;

import ch.bbw.m320.Car.Dto.CarDto;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin("*")
@RequestMapping("api/cars")
@RestController
public class CarController {

    @GetMapping
    public List<CarDto> cars() {

    }
}
