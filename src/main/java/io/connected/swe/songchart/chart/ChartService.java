package io.connected.swe.songchart.chart;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.Locale;
import java.util.Optional;

@Service
public class ChartService {

    ChartRepository chartRepository;

    public ChartService(ChartRepository chartRepository) {
        this.chartRepository = chartRepository;
    }

    public Optional<Chart> chartByDate(LocalDate date) {
        LocalDate weekId = weekIdByDate(date);
        return chartRepository.findByWeekIdWithHitsOrderedByRank(weekId);
    }

    /**
     * Week ID is always Saturday of a given week.
     */
    protected LocalDate weekIdByDate(LocalDate date) {
        WeekFields weekFields = WeekFields.of(Locale.CANADA);
        TemporalField woy = weekFields.weekOfWeekBasedYear();
        int weekNumber = date.get(woy);
        int year = date.getYear();

        return LocalDate.now()
                .withYear(year)
                .with(weekFields.weekOfYear(), weekNumber)
                .with(weekFields.dayOfWeek(), 7);
    }

}
