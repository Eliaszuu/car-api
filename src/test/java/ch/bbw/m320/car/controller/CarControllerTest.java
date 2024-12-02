package ch.bbw.m320.car.controller;

import ch.bbw.m320.car.dto.CarDto;
import ch.bbw.m320.car.service.CarService;
import org.assertj.core.api.WithAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.time.Year;
import java.time.ZonedDateTime;
import java.util.UUID;

@WebFluxTest
@ExtendWith(SpringExtension.class)
class CarControllerTest implements WithAssertions {

    @Autowired
    private WebTestClient webClient;

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
    }

    // Test: GET /api/cars - Get all cars
    @Test
    void getAllCars() {
        webClient.get()
                .uri("/api/cars")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(CarDto.class)
                .hasSize(1)  // Adjust based on the number of cars in your test setup
                .contains(testCarDto);
    }

    // Test: GET /api/cars/{id} - Get a car by ID
    @Test
    void getCarById() {
        UUID carId = testCarDto.getId();
        // Assuming the car is already in the database (either in memory or mock data)
        webClient.get()
                .uri("/api/cars/{id}", carId)
                .exchange()
                .expectStatus().isOk()
                .expectBody(CarDto.class)
                .consumeWith(response -> {
                    CarDto carResponse = response.getResponseBody();
                    assert carResponse != null;
                    assert carResponse.getId().equals(carId);
                    assert carResponse.getBrand().equals("Volkswagen");
                    assert carResponse.getModel().equals("Golf");
                    assert carResponse.getColor().equals("blue");
                    assert carResponse.getYear().equals(Year.of(2020));
                    assert carResponse.getEngine().equals("V6");
                    assert carResponse.getPs().equals(150);
                });
    }

    // Test: GET /api/cars/{id} - Get car by ID not found
    @Test
    void getCarByIdNotFound() {
        UUID nonExistentId = UUID.randomUUID();
        webClient.get()
                .uri("/api/cars/{id}", nonExistentId)
                .exchange()
                .expectStatus().isNoContent();
    }

    // Test: POST /api/cars - Create a new car
    @Test
    void createCar() {
        webClient.post()
                .uri("/api/cars")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(testCarDto)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(CarDto.class)
                .consumeWith(response -> {
                    CarDto createdCar = response.getResponseBody();
                    assert createdCar != null;
                    assert createdCar.getId() != null;
                    assert createdCar.getBrand().equals(testCarDto.getBrand());
                    assert createdCar.getModel().equals(testCarDto.getModel());
                    assert createdCar.getColor().equals(testCarDto.getColor());
                    assert createdCar.getYear().equals(testCarDto.getYear());
                    assert createdCar.getEngine().equals(testCarDto.getEngine());
                    assert createdCar.getPs().equals(testCarDto.getPs());
                });
    }

    // Test: PUT /api/cars/{id} - Update a car
    @Test
    void updateCar() {
        UUID carId = testCarDto.getId();
        CarDto updatedCarDto = new CarDto();
        updatedCarDto.setId(carId);
        updatedCarDto.setBrand("BMW");
        updatedCarDto.setModel("X5");
        updatedCarDto.setColor("black");
        updatedCarDto.setYear(Year.of(2021));
        updatedCarDto.setEngine("V8");
        updatedCarDto.setPs(250);

        webClient.put()
                .uri("/api/cars/{id}", carId)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(updatedCarDto)
                .exchange()
                .expectStatus().isOk()
                .expectBody(CarDto.class)
                .consumeWith(response -> {
                    CarDto updatedCar = response.getResponseBody();
                    assert updatedCar != null;
                    assert updatedCar.getBrand().equals(updatedCarDto.getBrand());
                    assert updatedCar.getModel().equals(updatedCarDto.getModel());
                    assert updatedCar.getColor().equals(updatedCarDto.getColor());
                    assert updatedCar.getYear().equals(updatedCarDto.getYear());
                    assert updatedCar.getEngine().equals(updatedCarDto.getEngine());
                    assert updatedCar.getPs().equals(updatedCarDto.getPs());
                });
    }

    // Test: DELETE /api/cars/{id} - Delete a car
    @Test
    void deleteCar() {
        UUID carId = testCarDto.getId();
        // First create the car (if not already created)
        webClient.post()
                .uri("/api/cars")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(testCarDto)
                .exchange()
                .expectStatus().isCreated();

        // Now, delete the car
        webClient.delete()
                .uri("/api/cars/{id}", carId)
                .exchange()
                .expectStatus().isNoContent();

        // Verify that the car is deleted (try fetching it and expect a "No Content" response)
        webClient.get()
                .uri("/api/cars/{id}", carId)
                .exchange()
                .expectStatus().isNoContent();
    }
}
