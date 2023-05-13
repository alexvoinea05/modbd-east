package ro.east.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import ro.east.domain.TrainTypeEast;

/**
 * Spring Data JPA repository for the TrainTypeEast entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TrainTypeEastRepository extends JpaRepository<TrainTypeEast, Long> {}
