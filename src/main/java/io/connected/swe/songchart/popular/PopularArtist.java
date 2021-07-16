package io.connected.swe.songchart.popular;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "popular_artist")
public class PopularArtist {

    @Id
    private String artist;

    private int birthdays;

    void incrementBirthday() {
        this.birthdays++;
    }
}
