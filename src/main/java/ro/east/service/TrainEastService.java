package ro.east.service;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ro.east.service.dto.TrainEastDTO;

/**
 * Service Interface for managing {@link ro.east.domain.TrainEast}.
 */
public interface TrainEastService {
    /**
     * Save a trainEast.
     *
     * @param trainEastDTO the entity to save.
     * @return the persisted entity.
     */
    TrainEastDTO save(TrainEastDTO trainEastDTO);

    /**
     * Updates a trainEast.
     *
     * @param trainEastDTO the entity to update.
     * @return the persisted entity.
     */
    TrainEastDTO update(TrainEastDTO trainEastDTO);

    /**
     * Partially updates a trainEast.
     *
     * @param trainEastDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<TrainEastDTO> partialUpdate(TrainEastDTO trainEastDTO);

    /**
     * Get all the trainEasts.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TrainEastDTO> findAll(Pageable pageable);

    /**
     * Get the "id" trainEast.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TrainEastDTO> findOne(Long id);

    /**
     * Delete the "id" trainEast.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
