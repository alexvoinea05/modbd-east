package ro.east.service;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ro.east.service.dto.FuelTypeEastDTO;

/**
 * Service Interface for managing {@link ro.east.domain.FuelTypeEast}.
 */
public interface FuelTypeEastService {
    /**
     * Save a fuelTypeEast.
     *
     * @param fuelTypeEastDTO the entity to save.
     * @return the persisted entity.
     */
    FuelTypeEastDTO save(FuelTypeEastDTO fuelTypeEastDTO);

    /**
     * Updates a fuelTypeEast.
     *
     * @param fuelTypeEastDTO the entity to update.
     * @return the persisted entity.
     */
    FuelTypeEastDTO update(FuelTypeEastDTO fuelTypeEastDTO);

    /**
     * Partially updates a fuelTypeEast.
     *
     * @param fuelTypeEastDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<FuelTypeEastDTO> partialUpdate(FuelTypeEastDTO fuelTypeEastDTO);

    /**
     * Get all the fuelTypeEasts.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<FuelTypeEastDTO> findAll(Pageable pageable);

    /**
     * Get the "id" fuelTypeEast.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<FuelTypeEastDTO> findOne(Long id);

    /**
     * Delete the "id" fuelTypeEast.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
