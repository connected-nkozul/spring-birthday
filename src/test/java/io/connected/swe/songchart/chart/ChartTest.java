package io.connected.swe.songchart.chart;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ChartTest {

    List<Hit> hits;

    @BeforeEach
    void setup() {
        hits = hitList();
    }

    @Test
    void testGetterAndSetter(){
        Chart sut = new Chart();
        sut.setUrl("url");
        LocalDate date = LocalDate.now();
        sut.setWeekId(date);
        sut.setHits(hits);
        assertEquals(hits, sut.getHits());

        assertEquals("url", sut.getUrl());
        assertEquals(date, sut.getWeekId());

    }

    @Test
    void testNewWitArgs() {
        List<Hit> hits = hitList();
        LocalDate date = LocalDate.now();
        Chart sut = new Chart(date, "url", hits);
        assertNotNull(sut);
        assertEquals(date, sut.getWeekId());
        assertEquals("url", sut.getUrl());

    }

    @Test
    void testNewNoArgs() {
        Chart sut = new Chart();
        assertNotNull(sut);
    }

    @Test
    void testNewWithBuilder() {
        Hit hit1 = new Hit("artist1", "song1", 1, 1, 1);
        Hit hit2 = new Hit("artist2", "song2", 2, 2, 2);

        LocalDate date = LocalDate.now();
        Chart sut = Chart.builder()
                .weekId(date)
                .url("url")
                .hit(hit1)
                .hit(hit2)
                .build();

        assertNotNull(sut);
        assertEquals(date, sut.getWeekId());
        assertEquals("url", sut.getUrl());
        assertEquals(2, sut.getHits().size());
        assertEquals(hit1, sut.getHits().get(0));
        assertEquals(hit2, sut.getHits().get(1));
    }

    private List<Hit> hitList() {
        List<Hit> hits = new ArrayList<>();
        hits.add(new Hit());
        hits.add(new Hit());
        return hits;
    }

}
