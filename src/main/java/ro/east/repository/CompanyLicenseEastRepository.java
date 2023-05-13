package ro.east.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import ro.east.domain.CompanyLicenseEast;

/**
 * Spring Data JPA repository for the CompanyLicenseEast entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CompanyLicenseEastRepository extends JpaRepository<CompanyLicenseEast, Long> {}
