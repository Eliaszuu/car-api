package ch.bbw.m320.car.controller;

import ch.bbw.m320.car.dto.CarDto;
import org.assertj.core.api.WithAssertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.time.Year;
import java.time.ZonedDateTime;
import java.util.UUID;

@AutoConfigureWebTestClient
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CarControllerTest implements WithAssertions {

    @Autowired
    private WebTestClient webClient;

    CarDto testCarDto = new CarDto();

    @Order(1)
    void setUp() {
        testCarDto.setModel("Golf");
        testCarDto.setColor("blue");
        testCarDto.setYear(Year.of(2020));
        testCarDto.setEngine("V6");
        testCarDto.setPs(150);
    }


    @Order(2)
    @ParameterizedTest
    @ValueSource(strings = {"Volkswagen", "Audi", "Porsche"})
    void postValidDtos(String validBrand) {
        testCarDto.setBrand(validBrand);


        webClient.post()
                .uri("/api/cars")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(testCarDto)
                .exchange()
                .expectStatus().isCreated()
                .JsonPath
        ;
    }

    @Order(3)
    @Test
    void getAllCars() {
        webClient.get()
                .uri("/api/cars")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(CarDto.class)
                .hasSize(3)
        ;
    }

    @Test
    void getCarById() {
        var carId = testCarDto.getId();
        webClient.get()
                .uri("/api/cars/" + carId)
                .exchange()
                .expectStatus().isOk()
                .expectBody(CarDto.class)
                .consumeWith(response -> {
                    CarDto carResponse = response.getResponseBody();
                    assert carResponse != null;
                    assert carResponse.getId().equals(carId);
                    assert carResponse.getBrand().equals("Porsche");
                    assert carResponse.getModel().equals("Golf");
                    assert carResponse.getColor().equals("blue");
                    assert carResponse.getYear().equals(Year.of(2020));
                    assert carResponse.getEngine().equals("V6");
                    assert carResponse.getPs().equals(150);
                });
    }

    @Test
    void getCarByIdNotFound() {
        UUID nonExistentId = UUID.randomUUID();
        webClient.get()
                .uri("/api/cars/{id}", nonExistentId)
                .exchange()
                .expectStatus().isNoContent();
    }

    @Test
    void updateCar() {
        var carId = testCarDto.getId();

        CarDto updatedCarDto = new CarDto();
        updatedCarDto.setBrand("BMW");
        updatedCarDto.setModel("X5");
        updatedCarDto.setColor("black");
        updatedCarDto.setYear(Year.of(2021));
        updatedCarDto.setEngine("V8");
        updatedCarDto.setPs(250);

        webClient.put()
                .uri("/api/cars/"+ carId)
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
        webClient.post()
                .uri("/api/cars")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(testCarDto)
                .exchange()
                .expectStatus().isCreated();

        webClient.delete()
                .uri("/api/cars/{id}", carId)
                .exchange()
                .expectStatus().isNoContent();

        webClient.get()
                .uri("/api/cars/{id}", carId)
                .exchange()
                .expectStatus().isNoContent();
    }
}
