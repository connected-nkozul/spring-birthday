package io.connected.swe.songchart.chart;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * 'Hit' is part of 'Chart' Aggregate.
 *
 * This is an Value Object. It doesn't contain an ID,
 * and it can be duplicated in the database.
 * @see Chart
 *
 * Database operations are controled by the aggregate root through @{@link ChartRepository}.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
// Sets this object as embeddable, something really close to DDD concept of Value Object
@Embeddable
public class Hit {

    private String artist;

    private String song;

    @Column(name = "week_rank")
    private int rank;

    @Column(name = "peak_position")
    private int peakPosition;

    @Column(name = "weeks_on_chart")
    private int weeksOnChart;

}
