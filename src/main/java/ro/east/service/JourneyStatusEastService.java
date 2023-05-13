package ro.east.service;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ro.east.service.dto.JourneyStatusEastDTO;

/**
 * Service Interface for managing {@link ro.east.domain.JourneyStatusEast}.
 */
public interface JourneyStatusEastService {
    /**
     * Save a journeyStatusEast.
     *
     * @param journeyStatusEastDTO the entity to save.
     * @return the persisted entity.
     */
    JourneyStatusEastDTO save(JourneyStatusEastDTO journeyStatusEastDTO);

    /**
     * Updates a journeyStatusEast.
     *
     * @param journeyStatusEastDTO the entity to update.
     * @return the persisted entity.
     */
    JourneyStatusEastDTO update(JourneyStatusEastDTO journeyStatusEastDTO);

    /**
     * Partially updates a journeyStatusEast.
     *
     * @param journeyStatusEastDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<JourneyStatusEastDTO> partialUpdate(JourneyStatusEastDTO journeyStatusEastDTO);

    /**
     * Get all the journeyStatusEasts.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<JourneyStatusEastDTO> findAll(Pageable pageable);

    /**
     * Get the "id" journeyStatusEast.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<JourneyStatusEastDTO> findOne(Long id);

    /**
     * Delete the "id" journeyStatusEast.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
