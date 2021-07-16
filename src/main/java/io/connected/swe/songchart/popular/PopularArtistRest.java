package io.connected.swe.songchart.popular;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
public class PopularArtistRest {

    final PopularArtistService service;

    public PopularArtistRest(PopularArtistService service) {
        this.service = service;
    }

    @GetMapping("/api/popular")
    public PopularArtist getMostPopularArtist() {
        Optional<PopularArtist> mostPopular = service.findMostPopularArtist();
        return mostPopular.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Most Popular Artist Not Found"));
    }
}
