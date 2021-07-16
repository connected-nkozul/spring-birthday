package io.connected.swe.songchart.popular;

import io.connected.swe.songchart.birthdayHit.BirthdayHitEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PopularArtistService {

    Logger logger = LoggerFactory.getLogger(PopularArtistService.class);

    private final PopularArtistRepository popularArtistRepository;

    public PopularArtistService(PopularArtistRepository popularArtistRepository) {
        this.popularArtistRepository = popularArtistRepository;
    }

    @EventListener
    void handleNewBirthdayHit(BirthdayHitEvent birthdayHitEvent) {
        logger.info("handleNewBirthdayHit: " + birthdayHitEvent);

        String artist = birthdayHitEvent.getBirthdayHit().getHitArtist();
        Optional<PopularArtist> savedPopularArtist = popularArtistRepository
                .findById(artist);

        if (savedPopularArtist.isPresent()) {
            incrementArtistBirthday(savedPopularArtist.get());
        } else {
            insertNewPopularArtist(artist);
        }
    }

    private void incrementArtistBirthday(PopularArtist savedPopularArtist) {
        savedPopularArtist.incrementBirthday();
        popularArtistRepository.save(savedPopularArtist);
    }

    private void insertNewPopularArtist(String artist) {
        PopularArtist popularArtist = PopularArtist.builder()
                .artist(artist)
                .birthdays(1)
                .build();
        popularArtistRepository.save(popularArtist);
    }

    public Optional<PopularArtist> findMostPopularArtist() {
        Page<PopularArtist> artistPage = popularArtistRepository
                .findMostPopular(PageRequest.of(0, 1));
        if (artistPage.getSize() == 1) {
            return Optional.of(artistPage.getContent().get(0));
        } else {
            return Optional.empty();
        }
    }
}
