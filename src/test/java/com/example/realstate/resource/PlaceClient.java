package com.example.realstate.resource;

import com.example.realstate.dto.PlaceDTO;
import io.micronaut.core.version.annotation.Version;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Delete;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.Put;
import io.micronaut.http.client.annotation.Client;

import java.util.List;

@Version("1")
@Client("/api/place")
public interface PlaceClient {

    @Get("/{id}")
    HttpResponse<PlaceDTO> findById(String id);

    @Get
    HttpResponse<List<PlaceDTO>> list();

    @Put
    HttpResponse<PlaceDTO> create(@Body PlaceDTO place);

    @Post("/{id}")
    HttpResponse<PlaceDTO> update(String id, @Body PlaceDTO place);

    @Delete("/{id}")
    HttpResponse<Void> delete(String id);
}
