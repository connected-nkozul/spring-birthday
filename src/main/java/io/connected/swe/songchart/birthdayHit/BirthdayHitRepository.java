package io.connected.swe.songchart.birthdayHit;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BirthdayHitRepository extends JpaRepository<BirthdayHit, Long> {
}
