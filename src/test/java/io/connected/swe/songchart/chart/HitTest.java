package io.connected.swe.songchart.chart;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class HitTest {

    @Test
    void testGetterAndSetter() {
        Hit sut = new Hit();
        sut.setArtist("artist");
        sut.setSong("title");
        sut.setRank(1);
        assertEquals("artist", sut.getArtist());
        assertEquals("title", sut.getSong());
        assertEquals(1, sut.getRank());
    }

    @Test
    void testNewWithArgs() {
        Hit sut = new Hit("artist", "title", 1, 1, 1);
        assertEquals("artist", sut.getArtist());
        assertEquals("title", sut.getSong());
        assertEquals(1, sut.getRank());
    }

    @Test
    void testWithBuilder() {
        Hit sut = Hit.builder()
                .song("title")
                .artist("artist")
                .rank(1)
                .build();
        assertNotNull(sut);
        assertEquals("title", sut.getSong());
        assertEquals("artist", sut.getArtist());
        assertEquals(1, sut.getRank());
    }

}
