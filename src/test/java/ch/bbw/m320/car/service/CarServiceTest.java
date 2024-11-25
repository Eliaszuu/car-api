package ch.bbw.m320.car.service;

import ch.bbw.m320.car.dto.CarDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CarServiceTest {

    private CarService service;
    private List<CarDto> cars;

    @BeforeEach
    void setUp() {
        cars = new ArrayList<>();
        service = new CarService();
    }

    @Test
    void getAllCars() {

    }

    @Test
    void getCarById() {

    }

    @Test
    void addCar() {
        // Arrange
        CarDto carDto = new CarDto();
        carDto.setBrand("Toyota");
        carDto.setModel("Corolla");

        // Act
        CarDto result = service.addCar(carDto);

        // Assert
        assertNotNull(result.getId(), "ID should not be null");
        assertNotNull(result.getCreateTimestamp(), "CreateTimestamp should not be null");
        assertEquals(1, cars.size(), "Car list size should be 1");
        assertTrue(cars.contains(result), "Car list should contain the added car");
        assertEquals("Toyota", result.getBrand(), "Brand should be 'Toyota'");
        assertEquals("Corolla", result.getModel(), "Model should be 'Corolla'");
    }

    @Test
    void editCar() {
    }

    @Test
    void deleteCar() {
    }
}