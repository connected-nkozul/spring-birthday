package io.connected.swe.songchart.birthdayHit;

import io.connected.swe.songchart.chart.Chart;
import io.connected.swe.songchart.chart.ChartNotFoundException;
import io.connected.swe.songchart.chart.ChartService;
import io.connected.swe.songchart.chart.Hit;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BirthdayHitService {

    private final BirthdayHitRepository repository;
    private final ChartService chartService;
    private final ApplicationEventPublisher publisher;

    public BirthdayHitService(BirthdayHitRepository repository,
                              ChartService chartService,
                              ApplicationEventPublisher publisher
    ) {
        this.repository = repository;
        this.chartService = chartService;
        this.publisher = publisher;
    }

    public Long saveBirthday(BirthdayDto birthday) throws ChartNotFoundException {
        Optional<Chart> chart = chartService.chartByDate(birthday.getBirthday());
        if (chart.isEmpty()) {
            throw new ChartNotFoundException();
        }

        BirthdayHit birthdayHit = buildBirthdayHitFromBirthdayAndChart(birthday, chart.get());
        BirthdayHit savedHit = repository.save(birthdayHit);
        publishSaveEvent(savedHit);
        return savedHit.getId();
    }

    private BirthdayHit buildBirthdayHitFromBirthdayAndChart(BirthdayDto birthday, Chart chart) {
        Hit hit = chart.getHits().get(0);
        return BirthdayHit.builder()
                .birthday(birthday.getBirthday())
                .name(birthday.getName())
                .chartWeekId(chart.getWeekId())
                .hitArtist(hit.getArtist())
                .hitSong(hit.getSong())
                .build();
    }

    private void publishSaveEvent(BirthdayHit savedHit) {
        publisher.publishEvent(
                BirthdayHitEvent.builder()
                        .birthdayHit(savedHit)
                        .build());
    }
}
