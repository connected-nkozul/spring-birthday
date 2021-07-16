package io.connected.swe.songchart.chart;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Optional;

@SpringBootTest
public class ChartRepositoryIntegrationTest {

    @Autowired
    ChartRepository sut;

    Hit hit1, hit2, hit3;
    Chart chart;


    @BeforeEach
    void setup() {
        hit1 = Hit.builder()
                .artist("artist1")
                .song("title1")
                .rank(1)
                .build();
        hit2 = Hit.builder()
                .artist("artist2")
                .song("title2")
                .rank(2)
                .build();
        hit3 = Hit.builder()
                .artist("artist3")
                .song("title3")
                .rank(3)
                .build();

        chart = Chart.builder()
                .weekId(LocalDate.parse("2020-11-18"))
                .url("url")
                .hit(hit1)
                .hit(hit3)
                .hit(hit2)
                .build();
    }

    @Test
    void givenChartRepository_findByWeekIdWithHitsOrderedByRank_thenOK() {
        Chart savedChart = sut.save(chart);

        assertNotNull(savedChart);
        Optional<Chart> loadedChart = sut.findByWeekIdWithHitsOrderedByRank(LocalDate.parse("2020-11-18"));
        assertTrue(loadedChart.isPresent());
        assertEquals(hit1, loadedChart.get().getHits().get(0));
        assertEquals(hit2, loadedChart.get().getHits().get(1));
        assertEquals(hit3, loadedChart.get().getHits().get(2));
    }

}
