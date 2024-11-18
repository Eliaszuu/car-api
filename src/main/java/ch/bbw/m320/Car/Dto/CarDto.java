package ch.bbw.m320.Car.Dto;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.Year;
import java.util.UUID;

@Data
@Entity
public class CarDto {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    String brand;
    String model;
    String color;
    Year year;
    String engine;
    Integer Ps;
}
