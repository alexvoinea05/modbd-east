package ro.east.service;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ro.east.service.dto.LicenseEastDTO;

/**
 * Service Interface for managing {@link ro.east.domain.LicenseEast}.
 */
public interface LicenseEastService {
    /**
     * Save a licenseEast.
     *
     * @param licenseEastDTO the entity to save.
     * @return the persisted entity.
     */
    LicenseEastDTO save(LicenseEastDTO licenseEastDTO);

    /**
     * Updates a licenseEast.
     *
     * @param licenseEastDTO the entity to update.
     * @return the persisted entity.
     */
    LicenseEastDTO update(LicenseEastDTO licenseEastDTO);

    /**
     * Partially updates a licenseEast.
     *
     * @param licenseEastDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<LicenseEastDTO> partialUpdate(LicenseEastDTO licenseEastDTO);

    /**
     * Get all the licenseEasts.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<LicenseEastDTO> findAll(Pageable pageable);

    /**
     * Get the "id" licenseEast.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<LicenseEastDTO> findOne(Long id);

    /**
     * Delete the "id" licenseEast.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
