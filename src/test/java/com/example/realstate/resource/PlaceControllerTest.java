package com.example.realstate.resource;

import com.example.realstate.dto.LocationDTO;
import com.example.realstate.dto.PlaceAddressDTO;
import com.example.realstate.dto.PlaceDTO;
import com.example.realstate.internal.service.PlaceService;
import com.example.realstate.internal.service.PlaceServiceImpl;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.runtime.server.EmbeddedServer;
import io.micronaut.test.annotation.MicronautTest;
import io.micronaut.test.annotation.MockBean;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SuppressWarnings({"unused", "OptionalGetWithoutIsPresent"})
@MicronautTest
class PlaceControllerTest {
    @Inject
    private EmbeddedServer server;

    @Inject
    private PlaceClient client;

    @Inject
    private PlaceService placeService;

    @Test
    void findById_ok() {
        final PlaceDTO expectedPlace = createPlaceForTest();
        final String ID = expectedPlace.getId();
        when(placeService.findById(eq(ID))).thenReturn(Optional.of(expectedPlace));
        HttpResponse<PlaceDTO> response = client.findById(ID);
        assertEquals(HttpStatus.OK, response.status());
        assertEquals(expectedPlace, response.body());
        verify(placeService).findById(eq(ID));
    }

    @Test
    void findById_notFound() {
        final String ID = UUID.randomUUID().toString();
        when(placeService.findById(eq(ID))).thenReturn(Optional.empty());
        HttpResponse<PlaceDTO> response = client.findById(ID);
        assertEquals(HttpStatus.NOT_FOUND, response.status());
        verify(placeService).findById(eq(ID));
    }

    @Test
    void list() {
        List<PlaceDTO> expectedList = createPlaceListForTest();
        when(placeService.list()).thenReturn(expectedList);
        HttpResponse<List<PlaceDTO>> response = client.list();
        assertEquals(HttpStatus.OK, response.status());
        assertEquals(expectedList, response.body());
        verify(placeService).list();
    }

    @Test
    void create() {
        final PlaceDTO expectedPlace = createPlaceForTest();
        final String ID = expectedPlace.getId();
        expectedPlace.setId(null);
        when(placeService.createPlace(eq(expectedPlace))).thenAnswer(invocation -> {
            PlaceDTO argument = invocation.getArgument(0, PlaceDTO.class);
            argument.setId(ID);
            return Optional.of(argument);
        });
        HttpResponse<PlaceDTO> response = client.create(expectedPlace);
        expectedPlace.setId(ID);
        assertEquals(HttpStatus.CREATED, response.status());
        assertEquals(expectedPlace, response.body());
        verify(placeService).createPlace(any(PlaceDTO.class));
    }

    @Test
    void update() {
        final PlaceDTO expectedPlace = createPlaceForTest();
        final String ID = expectedPlace.getId();
        when(placeService.updatePlace(eq(expectedPlace))).thenReturn(Optional.of(expectedPlace));
        HttpResponse<PlaceDTO> response = client.update(ID, expectedPlace);
        assertEquals(HttpStatus.OK, response.status());
        assertEquals(expectedPlace, response.getBody().get());
        verify(placeService).updatePlace(any(PlaceDTO.class));
    }

    @Test
    void delete_ExistingPlace() {
        final PlaceDTO place = createPlaceForTest();
        final String ID = place.getId();
        when(placeService.findById(eq(ID))).thenReturn(Optional.of(place));
        when(placeService.updatePlace(any(PlaceDTO.class))).thenReturn(Optional.of(place));
        HttpResponse<Void> response = client.delete(ID);
        assertEquals(HttpStatus.NO_CONTENT, response.status());
        verify(placeService).findById(eq(ID));
        verify(placeService).delete(any(PlaceDTO.class));
    }

    @Test
    void delete_NonExisting() {
        final String ID = UUID.randomUUID().toString();
        when(placeService.findById(eq(ID))).thenReturn(Optional.empty());
        HttpResponse<Void> response = client.delete(ID);
        assertEquals(HttpStatus.NO_CONTENT, response.status());
        verify(placeService).findById(eq(ID));
        verify(placeService, never()).delete(any(PlaceDTO.class));
    }

    @MockBean(PlaceServiceImpl.class)
    public PlaceService placeService() {
        return mock(PlaceService.class);
    }

    private List<PlaceDTO> createPlaceListForTest() {
        return IntStream.range(0, 5)
                .mapToObj(i -> createPlaceForTest())
                .collect(Collectors.toList());
    }

    private PlaceDTO createPlaceForTest() {
        PlaceDTO place = new PlaceDTO();
        place.setId(UUID.randomUUID().toString());
        place.setTitle("The White House");
        place.setAddress(createAddressForTest());
        place.setLocation(createLocationForTest());
        return place;
    }

    private PlaceAddressDTO createAddressForTest() {
        PlaceAddressDTO placeAddress = new PlaceAddressDTO();
        placeAddress.setStreet("Pennsylvania Ave NW");
        placeAddress.setExtNumber("1600");
        placeAddress.setCounty("Washington");
        placeAddress.setStateCode("DC");
        placeAddress.setZipCode("20500");
        return placeAddress;
    }

    private LocationDTO createLocationForTest() {
        LocationDTO location = new LocationDTO();
        location.setLat(1.0D);
        location.setLng(2.0D);
        return location;
    }
}