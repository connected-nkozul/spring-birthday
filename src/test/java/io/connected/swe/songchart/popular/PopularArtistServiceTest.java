package io.connected.swe.songchart.popular;

import io.connected.swe.songchart.birthdayHit.BirthdayHit;
import io.connected.swe.songchart.birthdayHit.BirthdayHitEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
public class PopularArtistServiceTest {

    PopularArtistService sut;

    @Mock
    PopularArtistRepository repositoryMock;

    @Captor
    ArgumentCaptor<PopularArtist> captor;

    @BeforeEach
    void setUp() {
        sut = new PopularArtistService(repositoryMock);
    }

    @DisplayName("test handleNewBirthdayHit " +
            "given birthDayHit and " +
            "new artist " +
            "then insert new artist with counter 1")
    @Test
    void test_handleNewBirthdayHit_given_birthDayHit_then_insertArtist() {
        BirthdayHitEvent eventMock = mock(BirthdayHitEvent.class);
        BirthdayHit hitMock = mock(BirthdayHit.class);
        when(eventMock.getBirthdayHit()).thenReturn(hitMock);
        when(hitMock.getHitArtist()).thenReturn("artist");

        when(repositoryMock.findById("artist")).thenReturn(Optional.empty());

        sut.handleNewBirthdayHit(eventMock);
        verify(repositoryMock).save(captor.capture());
        assertEquals("artist", captor.getValue().getArtist());
        assertEquals(1, captor.getValue().getBirthdays());
    }

    @DisplayName("test handleNewBirthdayHit " +
            "given birthDayHit and " +
            "artist already registered  " +
            "then increment counter and save")
    @Test
    void test_handleNewBirthdayHit_given_birthDayHit_then_incrementArtist() {
        BirthdayHitEvent eventMock = mock(BirthdayHitEvent.class);
        BirthdayHit hitMock = mock(BirthdayHit.class);
        when(eventMock.getBirthdayHit()).thenReturn(hitMock);
        when(hitMock.getHitArtist()).thenReturn("artist");

        PopularArtist popularArtis = PopularArtist.builder()
                .artist("artist")
                .birthdays(1)
                .build();
        when(repositoryMock.findById("artist")).thenReturn(Optional.of(popularArtis));

        sut.handleNewBirthdayHit(eventMock);
        verify(repositoryMock).save(captor.capture());
        assertEquals("artist", captor.getValue().getArtist());
        assertEquals(2, captor.getValue().getBirthdays());
    }

    @Test
    void test_getPopularArtist_given_repositoryWithEntries_then_returnPopularArtist() {
        PopularArtist popularArtist = PopularArtist.builder()
                .artist("artist")
                .birthdays(10)
                .build();
        Page<PopularArtist> page = new PageImpl<>(Collections.singletonList(popularArtist));
        when(repositoryMock.findMostPopular(any(Pageable.class))).thenReturn(page);

        Optional<PopularArtist> result = sut.findMostPopularArtist();
        assertTrue(result.isPresent());
        assertEquals("artist", result.get().getArtist());
        assertEquals(10, result.get().getBirthdays());
    }

    @Test
    void test_getPopularArtist_given_repositoryEmpty_then_return_emptyOptional() {
        when(repositoryMock.findMostPopular(any(Pageable.class))).thenReturn(Page.empty());

        Optional<PopularArtist> result = sut.findMostPopularArtist();
        assertFalse(result.isPresent());
    }

}
