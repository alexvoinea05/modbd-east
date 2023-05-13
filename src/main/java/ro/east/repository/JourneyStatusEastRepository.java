package ro.east.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import ro.east.domain.JourneyStatusEast;

/**
 * Spring Data JPA repository for the JourneyStatusEast entity.
 */
@SuppressWarnings("unused")
@Repository
public interface JourneyStatusEastRepository extends JpaRepository<JourneyStatusEast, Long> {}
