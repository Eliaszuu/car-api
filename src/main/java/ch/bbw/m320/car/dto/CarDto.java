package ch.bbw.m320.car.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Year;
import java.time.ZonedDateTime;
import java.util.UUID;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CarDto {
    private UUID id;
    private ZonedDateTime createTimestamp;
    private String brand;
    private String model;
    private String color;
    private Year year;
    private String engine;
    private Integer ps;
}
