package org.carrental.Model;

import lombok.*;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder

public class Booking {
    private Long id;
    private String customerID;
    private String customerName;
    private String vehicleName;
    private String OwnerName;
    private String vehicleId;
    private Date datePicker;
    private Date endDate;
    private Double price;
    private String bookingStatus;
    private Date completeBooking;
    private Float commission;
    private Double totalAmount;
}
