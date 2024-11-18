package ch.bbw.m320.Car.Dto;

import lombok.Getter;
import lombok.Setter;

import java.time.Year;

@Getter
@Setter
public record CarDto(Integer Id,String brand, String model, String color, Year year, String engine, Integer Ps) {
}
