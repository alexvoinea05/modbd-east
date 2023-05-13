package ro.east.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import ro.east.domain.JourneyEast;

/**
 * Spring Data JPA repository for the JourneyEast entity.
 */
@SuppressWarnings("unused")
@Repository
public interface JourneyEastRepository extends JpaRepository<JourneyEast, Long> {}
