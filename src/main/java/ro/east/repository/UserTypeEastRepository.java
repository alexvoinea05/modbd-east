package ro.east.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import ro.east.domain.UserTypeEast;

/**
 * Spring Data JPA repository for the UserTypeEast entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UserTypeEastRepository extends JpaRepository<UserTypeEast, Long> {}
