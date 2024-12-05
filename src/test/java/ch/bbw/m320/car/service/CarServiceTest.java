package ch.bbw.m320.car.service;

import ch.bbw.m320.car.dto.CarDto;
import ch.bbw.m320.car.exception.CarNotFoundException;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Year;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@RequiredArgsConstructor
class CarServiceTest {

    private CarService service;

    private CarDto testCarDto;

    @BeforeEach
    void setUp() {
        testCarDto = new CarDto();
        testCarDto.setId(UUID.randomUUID());
        testCarDto.setCreateTimestamp(ZonedDateTime.now());
        testCarDto.setBrand("Volkswagen");
        testCarDto.setModel("Golf");
        testCarDto.setColor("blue");
        testCarDto.setYear(Year.of(2020));
        testCarDto.setEngine("V6");
        testCarDto.setPs(150);

        service = new CarService();
        service.addCar(testCarDto);
    }

    @Test
    void getAllCars() {
        List<CarDto> result = service.getAllCars(null);
        assertEquals(1, result.size());
        assertEquals(testCarDto, result.getFirst());
    }

    @Test
    void getCarById() {
        UUID id = testCarDto.getId();

        CarDto result = service.getCarById(id);
        assertEquals(testCarDto, result);
    }

    @Test
    void getCarByIdShouldGetException() {
        UUID randomId = UUID.randomUUID();

        assertThrows(CarNotFoundException.class, () -> service.getCarById(randomId));
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
        UUID id = testCarDto.getId();
        testCarDto.setColor("yellow");

        CarDto result = service.editCar(testCarDto, id);

        assertEquals("yellow", result.getColor());
    }

    @Test
    void deleteCar() {
        UUID id = testCarDto.getId();

        service.deleteCar(id);

        List<CarDto> result = service.getAllCars(null);
        assertEquals(0, result.size());
    }
}