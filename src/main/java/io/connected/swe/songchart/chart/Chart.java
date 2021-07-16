package io.connected.swe.songchart.chart;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

/**
 * 'Chart' Aggregate Root.
 *
 * This class id the Root Entity of the Aggregate that contains other elements.
 * @see Hit
 *
 * Database operations are controled by @{@link ChartRepository}
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
// Set Chart object as an Entity. An entity needs an unique @Id
@Entity
@Table(name = "chart")
public class Chart {

    @Id
    @Column(name = "week_id")
    private LocalDate weekId;

    private String url;

    // Lombok annotation a way to add items to a list in the object's builder
    @Singular
    // Establish relationship one to many between an entity(Chart) and a value object (Hit)
    // By default, collection items are fetched in a LAZY manner.
    @ElementCollection()
    // Hit SQL table settings
    @CollectionTable(
            name = "hit",
            joinColumns = @JoinColumn(name = "week_id"),
            foreignKey = @ForeignKey(name = "hits_week_id_fk")
    )
    private List<Hit> hits;

}
