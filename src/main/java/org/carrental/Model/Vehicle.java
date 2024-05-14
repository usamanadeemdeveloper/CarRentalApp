package org.carrental.Model;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder

public class Vehicle {
    private Long id;
    private String vehicleName;
    private Integer model;
    private String brand;
    private String color;
    private String  ownerId;
    private String bookingStatus;
    private Integer totalBookings;
    private Double profit;
}
