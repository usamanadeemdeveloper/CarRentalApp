package org.carrental.Model;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class Customer {
    private Long id;
    private String name;
    private Long phoneNo;
    private Long nicNo;
    private String address;
    private Long referencePhoneNo;
    private String status;
}
