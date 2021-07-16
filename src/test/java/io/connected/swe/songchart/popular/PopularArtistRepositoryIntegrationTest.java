package io.connected.swe.songchart.popular;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest()
@AutoConfigureMockMvc
public class PopularArtistRepositoryIntegrationTest {

    @Autowired
    PopularArtistRepository sut;

    @BeforeEach
    void setup() {
        sut.deleteAll();
    }

    @Test
    void test_findMostPopularArtist_given_multipleRegistries_then_returnPopularArtist() {
        PopularArtist popularArtist1 = PopularArtist.builder()
                .artist("artist1")
                .birthdays(10)
                .build();
        PopularArtist popularArtist2 = PopularArtist.builder()
                .artist("artist2")
                .birthdays(20)
                .build();
        sut.save(popularArtist1);
        sut.save(popularArtist2);

        Page<PopularArtist> mostPopular = sut.findMostPopular(PageRequest.of(0, 1));

        assertEquals(1, mostPopular.getSize());
        assertEquals("artist2", mostPopular.getContent().get(0).getArtist());
        assertEquals(20, mostPopular.getContent().get(0).getBirthdays());
    }

}
