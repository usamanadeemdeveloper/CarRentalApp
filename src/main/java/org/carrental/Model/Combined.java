package org.carrental.Model;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder

public class Combined {
    private Long id;
    private String ownerName;
    private String vehicleName;
    private Long phoneNo;
    private Long nicNo;
    private String address;
    private Float commission;
}
