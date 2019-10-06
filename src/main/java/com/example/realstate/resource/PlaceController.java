package com.example.realstate.resource;

import com.example.realstate.dto.PlaceDTO;
import com.example.realstate.internal.service.PlaceService;
import io.micronaut.context.ApplicationContext;
import io.micronaut.core.version.annotation.Version;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Delete;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.Put;
import io.reactivex.Single;

import javax.inject.Inject;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@Version("1")
@Controller("/api/place")
public class PlaceController {
    @Inject
    ApplicationContext applicationContext;

    @Inject
    PlaceService placeService;

    @Get("/{id}")
    public Single<HttpResponse<PlaceDTO>> findById(String id) {
        Optional<PlaceDTO> optional = placeService.findById(id);
        if (optional.isPresent())
            return Single.just(HttpResponse.ok(optional.get()));
        return Single.just(HttpResponse.notFound());
    }

    @Get
    public Single<HttpResponse<List<PlaceDTO>>> list() {
        List<PlaceDTO> list = placeService.list();
        return Single.just(HttpResponse.ok(list));
    }

    @Put
    public Single<HttpResponse<PlaceDTO>> create(@Body Single<PlaceDTO> single) {
        return single.map(place -> {
            Optional<PlaceDTO> optional = placeService.createPlace(place);
            if (optional.isPresent()) {
                return HttpResponse.created(optional.get(), URI.create("/api/place/" + place.getId()));
            }
            return HttpResponse.badRequest();
        });
    }

    @Post("/{id}")
    public Single<HttpResponse<PlaceDTO>> update(String id, @Body Single<PlaceDTO> single) {
        return single.map(place -> {
            Optional<PlaceDTO> optional = placeService.updatePlace(place);
            if (optional.isPresent())
                return HttpResponse.ok(optional.get());
            return HttpResponse.notFound();
        });
    }

    @Delete("/{id}")
    public Single<HttpResponse<Void>> delete(String id) {
        Optional<PlaceDTO> optional = placeService.findById(id);
        optional.ifPresent(place -> placeService.delete(place));
        return Single.just(HttpResponse.status(HttpStatus.NO_CONTENT));
    }
}
