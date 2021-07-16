package io.connected.swe.songchart.chart;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * 'Chart' Aggregate repository.
 *
 * Deal with database access and operations.
 * Since it is an extension of 'JpaRepository', the interface comes by default
 * with many CRUD operations. Operations like 'save', 'remove' and 'update' don't
 * need to be declared.
 */
@Repository
public interface ChartRepository extends JpaRepository<Chart, String> {

    /**
     * Find Chart by WeekId, it's unique identifier.
     *
     * We're using the @Query annotation here so we can fetch
     * 'hits' with this request, since that value is lazily fetched by default.
     * The reason why this query was necessary is the line `"JOIN FETCH c.hits h "`.
     * If it wasn't for that we would be able to rely on Spring's automatic query
     * building system, like on findAllByWeekIdAfter.
     */
    @Query("SELECT c FROM Chart c " +
            "JOIN FETCH c.hits h " +
            "WHERE c.weekId = :id " +
            "ORDER BY h.rank ASC ")
    Optional<Chart> findByWeekIdWithHitsOrderedByRank(@Param("id") LocalDate weekId);

    /**
     * The query for this method will be built automatically by Spring's data module.
     */
    List<Chart> findAllByWeekIdAfter(LocalDate weekId);
}
