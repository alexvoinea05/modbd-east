package ro.east.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import ro.east.domain.TrainEast;

/**
 * Spring Data JPA repository for the TrainEast entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TrainEastRepository extends JpaRepository<TrainEast, Long> {}
