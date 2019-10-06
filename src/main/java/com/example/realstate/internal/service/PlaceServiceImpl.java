package com.example.realstate.internal.service;

import com.example.realstate.dto.PlaceDTO;

import javax.inject.Named;
import javax.inject.Singleton;
import java.util.List;
import java.util.Optional;

@Named("placeService")
@Singleton
public class PlaceServiceImpl implements PlaceService {
    public Optional<PlaceDTO> findById(String id) {
        return Optional.empty();
    }

    public List<PlaceDTO> list() {
        return null;
    }

    public Optional<PlaceDTO> createPlace(PlaceDTO place) {
        return Optional.empty();
    }

    public Optional<PlaceDTO> updatePlace(PlaceDTO place) {
        return Optional.empty();
    }

    public int delete(PlaceDTO place) {
        return 0;
    }
}
