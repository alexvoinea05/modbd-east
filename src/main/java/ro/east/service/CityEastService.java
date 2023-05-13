package ro.east.service;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ro.east.service.dto.CityEastDTO;

/**
 * Service Interface for managing {@link ro.east.domain.CityEast}.
 */
public interface CityEastService {
    /**
     * Save a cityEast.
     *
     * @param cityEastDTO the entity to save.
     * @return the persisted entity.
     */
    CityEastDTO save(CityEastDTO cityEastDTO);

    /**
     * Updates a cityEast.
     *
     * @param cityEastDTO the entity to update.
     * @return the persisted entity.
     */
    CityEastDTO update(CityEastDTO cityEastDTO);

    /**
     * Partially updates a cityEast.
     *
     * @param cityEastDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CityEastDTO> partialUpdate(CityEastDTO cityEastDTO);

    /**
     * Get all the cityEasts.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CityEastDTO> findAll(Pageable pageable);

    /**
     * Get the "id" cityEast.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CityEastDTO> findOne(Long id);

    /**
     * Delete the "id" cityEast.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
