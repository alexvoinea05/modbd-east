package ro.east.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import ro.east.domain.AddressEast;

/**
 * Spring Data JPA repository for the AddressEast entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AddressEastRepository extends JpaRepository<AddressEast, Long> {}
