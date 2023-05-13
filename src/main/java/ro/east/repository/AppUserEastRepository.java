package ro.east.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import ro.east.domain.AppUserEast;

/**
 * Spring Data JPA repository for the AppUserEast entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AppUserEastRepository extends JpaRepository<AppUserEast, Long> {}
