package io.connected.swe.songchart.chart;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ChartServiceTest {

    ChartService sut;
    ChartRepository repository;

    @BeforeEach
    public void setup() {
        repository = Mockito.mock(ChartRepository.class);
        sut = new ChartService(
                repository
        );
    }

    @Test
    void testChartByDateGivenValidDate() {
        // 1959-12-05
        LocalDate expectedDate = LocalDate.now()
                .withYear(1959)
                .withMonth(12)
                .withDayOfMonth(5);
        Chart response = Mockito.mock(Chart.class);
        Mockito.when(repository.findByWeekIdWithHitsOrderedByRank(expectedDate))
                .thenReturn(Optional.of(response));

        // 1959-12-04
        LocalDate date = LocalDate.now()
                .withYear(1959)
                .withMonth(12)
                .withDayOfMonth(4);

        Optional<Chart> result = sut.chartByDate(date);
        assertTrue(result.isPresent());
        assertEquals(response, result.get());

    }

    @Test
    void testWeekIdByDateGivenValidDate() {
        // 1959-12-04
        LocalDate date = LocalDate.now()
                .withYear(1959)
                .withMonth(12)
                .withDayOfMonth(4);

        // 1959-12-05
        LocalDate expectedDate = LocalDate.now()
                .withYear(1959)
                .withMonth(12)
                .withDayOfMonth(5);

        LocalDate result = sut.weekIdByDate(date);
        assertEquals(expectedDate, result);

    }
}
