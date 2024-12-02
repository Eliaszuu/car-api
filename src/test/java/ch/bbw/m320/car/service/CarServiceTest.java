package ch.bbw.m320.car.service;

import ch.bbw.m320.car.dto.CarDto;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@RequiredArgsConstructor
class CarServiceTest {

    private CarService service;

    private UUID carId;

    @BeforeEach
    void setUp() {
        service = new CarService();

        // Arrange
        CarDto carDto = new CarDto();
        carDto.setBrand("Toyota");
        carDto.setModel("Corolla");
        carDto.setPs(100);
        carDto.setColor("black");

        // Act
        CarDto result = service.addCar(carDto);
        carId = result.getId();
    }

    @Test
    void getAllCars() {
        List<CarDto> result = service.getAllCars();

        assertEquals(1, result.size());
    }

    @Test
    void getCarById() {
        CarDto result = service.getCarById(carId);
        assertEquals("Toyota", result.getBrand());
    }

    @Test
    void addCar() {
        // Arrange
        CarDto carDto = new CarDto();
        carDto.setBrand("Toyota");
        carDto.setModel("Corolla");
        carDto.setPs(100);
        carDto.setColor("black");

        // Act
        CarDto result = service.addCar(carDto);

        // Assert
        assertNotNull(result.getId(), "ID should not be null");
        assertEquals("Toyota", result.getBrand(), "Brand should be 'Toyota'");
        assertEquals("Corolla", result.getModel(), "Model should be 'Corolla'");

        System.out.println(service.getCarById(result.getId()));
    }

    @Test
    void editCar() {
    }

    @Test
    void deleteCar() {

    }
}