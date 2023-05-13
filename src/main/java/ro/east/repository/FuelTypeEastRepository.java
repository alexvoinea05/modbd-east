package ro.east.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import ro.east.domain.FuelTypeEast;

/**
 * Spring Data JPA repository for the FuelTypeEast entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FuelTypeEastRepository extends JpaRepository<FuelTypeEast, Long> {}
