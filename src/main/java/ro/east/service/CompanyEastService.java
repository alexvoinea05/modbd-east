package ro.east.service;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ro.east.service.dto.CompanyEastDTO;

/**
 * Service Interface for managing {@link ro.east.domain.CompanyEast}.
 */
public interface CompanyEastService {
    /**
     * Save a companyEast.
     *
     * @param companyEastDTO the entity to save.
     * @return the persisted entity.
     */
    CompanyEastDTO save(CompanyEastDTO companyEastDTO);

    /**
     * Updates a companyEast.
     *
     * @param companyEastDTO the entity to update.
     * @return the persisted entity.
     */
    CompanyEastDTO update(CompanyEastDTO companyEastDTO);

    /**
     * Partially updates a companyEast.
     *
     * @param companyEastDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CompanyEastDTO> partialUpdate(CompanyEastDTO companyEastDTO);

    /**
     * Get all the companyEasts.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CompanyEastDTO> findAll(Pageable pageable);

    /**
     * Get the "id" companyEast.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CompanyEastDTO> findOne(Long id);

    /**
     * Delete the "id" companyEast.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
