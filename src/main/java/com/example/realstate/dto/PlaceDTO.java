package com.example.realstate.dto;

import lombok.Data;

@Data
public class PlaceDTO {
    private String id;
    private String title;
    private PlaceAddressDTO address;
    private LocationDTO location;
}
