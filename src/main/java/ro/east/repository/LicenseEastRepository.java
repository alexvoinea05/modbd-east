package ro.east.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import ro.east.domain.LicenseEast;

/**
 * Spring Data JPA repository for the LicenseEast entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LicenseEastRepository extends JpaRepository<LicenseEast, Long> {}
