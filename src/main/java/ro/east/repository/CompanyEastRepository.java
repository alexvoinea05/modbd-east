package ro.east.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import ro.east.domain.CompanyEast;

/**
 * Spring Data JPA repository for the CompanyEast entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CompanyEastRepository extends JpaRepository<CompanyEast, Long> {}
