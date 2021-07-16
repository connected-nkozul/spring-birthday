package io.connected.swe.songchart.birthdayHit;

import io.connected.swe.songchart.chart.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class BirthdayHitServiceTest {

    BirthdayHitService sut;

    LocalDate birthdayDate;
    BirthdayDto birthdayDto;

    @Mock
    BirthdayHitRepository repository;

    @Mock
    ChartService chartService;

    @Mock
    ApplicationEventPublisher publisher;

    @BeforeEach
    void setUp() {
        sut = new BirthdayHitService(repository, chartService, publisher);

        birthdayDate = LocalDate.now()
                .withYear(1982)
                .withMonth(12)
                .withDayOfMonth(6);
        birthdayDto = BirthdayDto.builder()
                .name("Name")
                .birthday(birthdayDate)
                .build();
    }

    @Test
    void test_saveBirthday_given_validBirthday_thenReturnValidId() {
        Chart chartMock = mock(Chart.class);
        LocalDate chartDate = LocalDate.now()
                .withYear(1982)
                .withMonth(12)
                .withDayOfMonth(5);
        when(chartMock.getWeekId()).thenReturn(chartDate);

        Hit hitMock = mock(Hit.class);
        when(hitMock.getArtist()).thenReturn("artist");
        when(hitMock.getSong()).thenReturn("song");
        when(chartMock.getHits()).thenReturn(Collections.singletonList(hitMock));

        Optional<Chart> chartResponse = Optional.of(chartMock);
        when(chartService.chartByDate(Mockito.any(LocalDate.class)))
                .thenReturn(chartResponse);

        BirthdayHit birthdayHitSavedMock = mock(BirthdayHit.class);
        when(birthdayHitSavedMock.getId()).thenReturn(11L);
        when(repository.save(any(BirthdayHit.class))).thenReturn(birthdayHitSavedMock);
        try {
            Long result = sut.saveBirthday(birthdayDto);
            assertEquals(11L, result);
        } catch (ChartNotFoundException e) {
            fail();
        }
    }

    @Test
    void test_saveBirthday_given_chartNotFound_throws_chartNotFoundException() {
        when(chartService.chartByDate(Mockito.any(LocalDate.class))).thenReturn(Optional.empty());
        assertThrows(ChartNotFoundException.class, () -> sut.saveBirthday(birthdayDto));
    }

}
