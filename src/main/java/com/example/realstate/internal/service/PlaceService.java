package com.example.realstate.internal.service;

import com.example.realstate.dto.PlaceDTO;

import java.util.List;
import java.util.Optional;

public interface PlaceService {

    Optional<PlaceDTO> findById(String id);

    List<PlaceDTO> list();

    Optional<PlaceDTO> createPlace(PlaceDTO place);

    Optional<PlaceDTO> updatePlace(PlaceDTO place);

    int delete(PlaceDTO place);
}
