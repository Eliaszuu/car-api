package ch.bbw.m320.car.controller;

import java.time.Year;
import java.time.ZonedDateTime;
import java.util.UUID;

import ch.bbw.m320.car.dto.CarDto;
import org.assertj.core.api.WithAssertions;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

@AutoConfigureWebTestClient
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CarControllerTest implements WithAssertions {

    @Autowired
    private WebTestClient webClient;

    private UUID uuid;
    private UUID uuid2;

    @BeforeEach
    void setUp() {
        // Car DTOs
        uuid = UUID.randomUUID();
        uuid2 = UUID.randomUUID();

        CarDto car1 = CarDto.builder()
                .id(uuid)
                .createTimestamp(ZonedDateTime.now())
                .brand("Toyota")
                .model("Corolla")
                .color("Red")
                .year(Year.of(2022))
                .engine("Hybrid")
                .ps(122)
                .build();

        CarDto car2 = CarDto.builder()
                .id(uuid2)
                .createTimestamp(ZonedDateTime.now())
                .brand("Ford")
                .model("Focus")
                .color("Blue")
                .year(Year.of(2021))
                .engine("Diesel")
                .ps(150)
                .build();

        // Creating car entries in the system
        webClient.post()
                .uri("/api/cars")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(car1)
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .jsonPath("$.id").exists();

        webClient.post()
                .uri("/api/cars")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(car2)
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .jsonPath("$.id").exists();
    }

    @ParameterizedTest
    @ValueSource(strings = {"Volkswagen", "Audi", "Porsche"})
    void postValidDtos(String validBrand) {

        // Creating a new CarDto
        CarDto carDto = CarDto.builder()
                .id(UUID.randomUUID())
                .createTimestamp(ZonedDateTime.now())
                .brand(validBrand)
                .model("Model S")
                .color("White")
                .year(Year.now())
                .engine("Electric")
                .ps(250)
                .build();

        webClient.post()
                .uri("/api/cars")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(carDto)
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .jsonPath("$.id").exists();
    }

    @Test
    void getAllCars() {
        webClient.get()
                .uri("/api/cars")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(CarDto.class)
                .hasSize(7);
    }

    @Test
    void getCarById() {
        webClient.get()
                .uri("/api/cars/" + uuid2)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.createTimestamp").isNotEmpty()
                .jsonPath("$.brand").isEqualTo("Ford")
                .jsonPath("$.model").isEqualTo("Focus")
                .jsonPath("$.color").isEqualTo("Blue")
                .jsonPath("$.year").isEqualTo(2021)
                .jsonPath("$.engine").isEqualTo("Diesel")
                .jsonPath("$.ps").isEqualTo(150);
    }

    @Test
    void getCarByIdNotFound() {
        UUID nonExistentId = UUID.randomUUID();
        webClient.get()
                .uri("/api/cars/{id}", nonExistentId)
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    void getCarByBrand() {
        webClient.get()
                .uri("/api/cars?brand=Ford")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.[0].brand").isEqualTo("Ford")
        ;
    }

    @Test
    void updateCar() {
        CarDto updatedCarDto = CarDto.builder()
                .id(uuid)
                .brand("BMW")
                .model("X5")
                .color("Black")
                .year(Year.of(2021))
                .engine("V8")
                .ps(250)
                .build();

        webClient.put()
                .uri("/api/cars/" + uuid)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(updatedCarDto)
                .exchange()
                .expectStatus().isOk()
                .expectBody(CarDto.class)
                .consumeWith(response -> {
                    CarDto updatedCar = response.getResponseBody();

                    // counter-intuitive: but in java we never use the keyword assert!
                    // instead we use junit asserts:
                    Assertions.assertNotNull(updatedCar);
                    // or we go with my favourite AssertJ for which the interface WithAssertions is already on the class
                    assertThat(updatedCar).isNotNull();

                    assert updatedCar != null;
                    assert updatedCar.getBrand().equals(updatedCarDto.getBrand());
                    assert updatedCar.getModel().equals(updatedCarDto.getModel());
                    assert updatedCar.getColor().equals(updatedCarDto.getColor());
                    assert updatedCar.getYear().equals(updatedCarDto.getYear());
                    assert updatedCar.getEngine().equals(updatedCarDto.getEngine());
                    assert updatedCar.getPs().equals(updatedCarDto.getPs());
                });
    }

    @Test
    void deleteCar() {
        webClient.delete()
                .uri("/api/cars/" + uuid)
                .exchange()
                .expectStatus().isNoContent();

        webClient.get()
                .uri("/api/cars/" + uuid)
                .exchange()
                .expectStatus().isNotFound();
    }
}
