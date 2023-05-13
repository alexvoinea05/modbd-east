package ro.east.service;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ro.east.service.dto.AppUserEastDTO;

/**
 * Service Interface for managing {@link ro.east.domain.AppUserEast}.
 */
public interface AppUserEastService {
    /**
     * Save a appUserEast.
     *
     * @param appUserEastDTO the entity to save.
     * @return the persisted entity.
     */
    AppUserEastDTO save(AppUserEastDTO appUserEastDTO);

    /**
     * Updates a appUserEast.
     *
     * @param appUserEastDTO the entity to update.
     * @return the persisted entity.
     */
    AppUserEastDTO update(AppUserEastDTO appUserEastDTO);

    /**
     * Partially updates a appUserEast.
     *
     * @param appUserEastDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<AppUserEastDTO> partialUpdate(AppUserEastDTO appUserEastDTO);

    /**
     * Get all the appUserEasts.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<AppUserEastDTO> findAll(Pageable pageable);

    /**
     * Get the "id" appUserEast.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AppUserEastDTO> findOne(Long id);

    /**
     * Delete the "id" appUserEast.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
