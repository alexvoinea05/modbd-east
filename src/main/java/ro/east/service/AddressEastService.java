package ro.east.service;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ro.east.service.dto.AddressEastDTO;

/**
 * Service Interface for managing {@link ro.east.domain.AddressEast}.
 */
public interface AddressEastService {
    /**
     * Save a addressEast.
     *
     * @param addressEastDTO the entity to save.
     * @return the persisted entity.
     */
    AddressEastDTO save(AddressEastDTO addressEastDTO);

    /**
     * Updates a addressEast.
     *
     * @param addressEastDTO the entity to update.
     * @return the persisted entity.
     */
    AddressEastDTO update(AddressEastDTO addressEastDTO);

    /**
     * Partially updates a addressEast.
     *
     * @param addressEastDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<AddressEastDTO> partialUpdate(AddressEastDTO addressEastDTO);

    /**
     * Get all the addressEasts.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<AddressEastDTO> findAll(Pageable pageable);

    /**
     * Get the "id" addressEast.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AddressEastDTO> findOne(Long id);

    /**
     * Delete the "id" addressEast.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
