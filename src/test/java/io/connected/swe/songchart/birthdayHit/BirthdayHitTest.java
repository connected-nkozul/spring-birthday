package io.connected.swe.songchart.birthdayHit;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
public class BirthdayHitTest {

    @DisplayName("test builder " +
            "given entity without property defined " +
            "then build entity with no properties")
    @Test
    void test_builder_givenBuilderWithNoProperty_thenBuildsEntityWithNoProperties() {
        BirthdayHit birthdayHit = BirthdayHit.builder().build();
        assertNotNull(birthdayHit);
    }

    @DisplayName("test getters" +
            "given entities built with all properties set " +
            "then return properties")
    @Test
    void test_getters_givenEntityBuiltWithAllProperties_thenReturnsProperties() {
        LocalDate birthday = LocalDate.now();
        LocalDate chartId = LocalDate.now();

        BirthdayHit birthdayHit = BirthdayHit.builder()
                .name("john")
                .birthday(birthday)
                .chartWeekId(chartId)
                .hitSong("hit")
                .hitArtist("artist")
                .build();
        assertEquals("john", birthdayHit.getName());
        assertEquals(birthday, birthdayHit.getBirthday());
        assertEquals(chartId, birthdayHit.getChartWeekId());
        assertEquals("hit", birthdayHit.getHitSong());
        assertEquals("artist", birthdayHit.getHitArtist());
    }

}
