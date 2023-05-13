package ro.east.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import ro.east.domain.TicketEast;

/**
 * Spring Data JPA repository for the TicketEast entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TicketEastRepository extends JpaRepository<TicketEast, Long> {}
