package ro.east.service;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ro.east.service.dto.JourneyEastDTO;

/**
 * Service Interface for managing {@link ro.east.domain.JourneyEast}.
 */
public interface JourneyEastService {
    /**
     * Save a journeyEast.
     *
     * @param journeyEastDTO the entity to save.
     * @return the persisted entity.
     */
    JourneyEastDTO save(JourneyEastDTO journeyEastDTO);

    /**
     * Updates a journeyEast.
     *
     * @param journeyEastDTO the entity to update.
     * @return the persisted entity.
     */
    JourneyEastDTO update(JourneyEastDTO journeyEastDTO);

    /**
     * Partially updates a journeyEast.
     *
     * @param journeyEastDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<JourneyEastDTO> partialUpdate(JourneyEastDTO journeyEastDTO);

    /**
     * Get all the journeyEasts.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<JourneyEastDTO> findAll(Pageable pageable);

    /**
     * Get the "id" journeyEast.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<JourneyEastDTO> findOne(Long id);

    /**
     * Delete the "id" journeyEast.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
