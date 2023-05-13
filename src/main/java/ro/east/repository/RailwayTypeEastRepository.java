package ro.east.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import ro.east.domain.RailwayTypeEast;

/**
 * Spring Data JPA repository for the RailwayTypeEast entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RailwayTypeEastRepository extends JpaRepository<RailwayTypeEast, Long> {}
