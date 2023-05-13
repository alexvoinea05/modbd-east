package ro.east.service;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ro.east.service.dto.CompanyLicenseEastDTO;

/**
 * Service Interface for managing {@link ro.east.domain.CompanyLicenseEast}.
 */
public interface CompanyLicenseEastService {
    /**
     * Save a companyLicenseEast.
     *
     * @param companyLicenseEastDTO the entity to save.
     * @return the persisted entity.
     */
    CompanyLicenseEastDTO save(CompanyLicenseEastDTO companyLicenseEastDTO);

    /**
     * Updates a companyLicenseEast.
     *
     * @param companyLicenseEastDTO the entity to update.
     * @return the persisted entity.
     */
    CompanyLicenseEastDTO update(CompanyLicenseEastDTO companyLicenseEastDTO);

    /**
     * Partially updates a companyLicenseEast.
     *
     * @param companyLicenseEastDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CompanyLicenseEastDTO> partialUpdate(CompanyLicenseEastDTO companyLicenseEastDTO);

    /**
     * Get all the companyLicenseEasts.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CompanyLicenseEastDTO> findAll(Pageable pageable);

    /**
     * Get the "id" companyLicenseEast.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CompanyLicenseEastDTO> findOne(Long id);

    /**
     * Delete the "id" companyLicenseEast.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
