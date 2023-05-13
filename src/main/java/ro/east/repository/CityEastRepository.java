package ro.east.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import ro.east.domain.CityEast;

/**
 * Spring Data JPA repository for the CityEast entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CityEastRepository extends JpaRepository<CityEast, Long> {}
