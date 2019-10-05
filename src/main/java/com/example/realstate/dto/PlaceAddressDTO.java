package com.example.realstate.dto;

import lombok.Data;

@Data
public class PlaceAddressDTO {
    private String street;
    private String extNumber;
    private String intNumber;
    private String neighborhood;
    private String county;
    private String stateCode;
    private String zipCode;
    private String country;
}
