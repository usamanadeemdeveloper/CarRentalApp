package org.carrental.Model;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class VehicleOwner {
    private Long id;
    private String ownerName;
    private Long phoneNo;
    private Long nicNo;
    private String address;
    private Float commission;
}
