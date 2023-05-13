package ro.east.service;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ro.east.service.dto.DistrictEastDTO;

/**
 * Service Interface for managing {@link ro.east.domain.DistrictEast}.
 */
public interface DistrictEastService {
    /**
     * Save a districtEast.
     *
     * @param districtEastDTO the entity to save.
     * @return the persisted entity.
     */
    DistrictEastDTO save(DistrictEastDTO districtEastDTO);

    /**
     * Updates a districtEast.
     *
     * @param districtEastDTO the entity to update.
     * @return the persisted entity.
     */
    DistrictEastDTO update(DistrictEastDTO districtEastDTO);

    /**
     * Partially updates a districtEast.
     *
     * @param districtEastDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<DistrictEastDTO> partialUpdate(DistrictEastDTO districtEastDTO);

    /**
     * Get all the districtEasts.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<DistrictEastDTO> findAll(Pageable pageable);

    /**
     * Get the "id" districtEast.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DistrictEastDTO> findOne(Long id);

    /**
     * Delete the "id" districtEast.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
