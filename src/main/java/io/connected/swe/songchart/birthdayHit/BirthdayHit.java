package io.connected.swe.songchart.birthdayHit;

import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Builder
@ToString
@Entity
@Table(name = "birthday_hit")
public class BirthdayHit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    private String name;

    @NotNull
    private LocalDate birthday;

    @NotNull
    @Column(name = "chart_week_id")
    private LocalDate chartWeekId;

    @NotNull
    @Column(name = "hit_song")
    private String hitSong;

    @NotNull
    @Column(name = "hit_artist")
    private String hitArtist;

}
