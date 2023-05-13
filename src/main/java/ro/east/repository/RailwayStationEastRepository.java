package ro.east.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import ro.east.domain.RailwayStationEast;

/**
 * Spring Data JPA repository for the RailwayStationEast entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RailwayStationEastRepository extends JpaRepository<RailwayStationEast, Long> {}
