package ro.east.service;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ro.east.service.dto.UserTypeEastDTO;

/**
 * Service Interface for managing {@link ro.east.domain.UserTypeEast}.
 */
public interface UserTypeEastService {
    /**
     * Save a userTypeEast.
     *
     * @param userTypeEastDTO the entity to save.
     * @return the persisted entity.
     */
    UserTypeEastDTO save(UserTypeEastDTO userTypeEastDTO);

    /**
     * Updates a userTypeEast.
     *
     * @param userTypeEastDTO the entity to update.
     * @return the persisted entity.
     */
    UserTypeEastDTO update(UserTypeEastDTO userTypeEastDTO);

    /**
     * Partially updates a userTypeEast.
     *
     * @param userTypeEastDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<UserTypeEastDTO> partialUpdate(UserTypeEastDTO userTypeEastDTO);

    /**
     * Get all the userTypeEasts.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<UserTypeEastDTO> findAll(Pageable pageable);

    /**
     * Get the "id" userTypeEast.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<UserTypeEastDTO> findOne(Long id);

    /**
     * Delete the "id" userTypeEast.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
