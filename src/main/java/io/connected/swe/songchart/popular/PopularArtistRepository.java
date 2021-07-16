package io.connected.swe.songchart.popular;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PopularArtistRepository extends JpaRepository<PopularArtist, String> {

    @Query("SELECT a FROM PopularArtist a ORDER BY a.birthdays DESC")
    Page<PopularArtist> findMostPopular(Pageable pageable);

}
