package ro.east.service;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ro.east.service.dto.RailwayTypeEastDTO;

/**
 * Service Interface for managing {@link ro.east.domain.RailwayTypeEast}.
 */
public interface RailwayTypeEastService {
    /**
     * Save a railwayTypeEast.
     *
     * @param railwayTypeEastDTO the entity to save.
     * @return the persisted entity.
     */
    RailwayTypeEastDTO save(RailwayTypeEastDTO railwayTypeEastDTO);

    /**
     * Updates a railwayTypeEast.
     *
     * @param railwayTypeEastDTO the entity to update.
     * @return the persisted entity.
     */
    RailwayTypeEastDTO update(RailwayTypeEastDTO railwayTypeEastDTO);

    /**
     * Partially updates a railwayTypeEast.
     *
     * @param railwayTypeEastDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<RailwayTypeEastDTO> partialUpdate(RailwayTypeEastDTO railwayTypeEastDTO);

    /**
     * Get all the railwayTypeEasts.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<RailwayTypeEastDTO> findAll(Pageable pageable);

    /**
     * Get the "id" railwayTypeEast.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<RailwayTypeEastDTO> findOne(Long id);

    /**
     * Delete the "id" railwayTypeEast.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
