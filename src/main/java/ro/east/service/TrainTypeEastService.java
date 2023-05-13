package ro.east.service;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ro.east.service.dto.TrainTypeEastDTO;

/**
 * Service Interface for managing {@link ro.east.domain.TrainTypeEast}.
 */
public interface TrainTypeEastService {
    /**
     * Save a trainTypeEast.
     *
     * @param trainTypeEastDTO the entity to save.
     * @return the persisted entity.
     */
    TrainTypeEastDTO save(TrainTypeEastDTO trainTypeEastDTO);

    /**
     * Updates a trainTypeEast.
     *
     * @param trainTypeEastDTO the entity to update.
     * @return the persisted entity.
     */
    TrainTypeEastDTO update(TrainTypeEastDTO trainTypeEastDTO);

    /**
     * Partially updates a trainTypeEast.
     *
     * @param trainTypeEastDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<TrainTypeEastDTO> partialUpdate(TrainTypeEastDTO trainTypeEastDTO);

    /**
     * Get all the trainTypeEasts.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TrainTypeEastDTO> findAll(Pageable pageable);

    /**
     * Get the "id" trainTypeEast.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TrainTypeEastDTO> findOne(Long id);

    /**
     * Delete the "id" trainTypeEast.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
