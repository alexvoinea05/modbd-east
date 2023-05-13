package ro.east.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import ro.east.domain.DistrictEast;

/**
 * Spring Data JPA repository for the DistrictEast entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DistrictEastRepository extends JpaRepository<DistrictEast, Long> {}
