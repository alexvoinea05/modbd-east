package ro.east.service;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ro.east.service.dto.RailwayStationEastDTO;

/**
 * Service Interface for managing {@link ro.east.domain.RailwayStationEast}.
 */
public interface RailwayStationEastService {
    /**
     * Save a railwayStationEast.
     *
     * @param railwayStationEastDTO the entity to save.
     * @return the persisted entity.
     */
    RailwayStationEastDTO save(RailwayStationEastDTO railwayStationEastDTO);

    /**
     * Updates a railwayStationEast.
     *
     * @param railwayStationEastDTO the entity to update.
     * @return the persisted entity.
     */
    RailwayStationEastDTO update(RailwayStationEastDTO railwayStationEastDTO);

    /**
     * Partially updates a railwayStationEast.
     *
     * @param railwayStationEastDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<RailwayStationEastDTO> partialUpdate(RailwayStationEastDTO railwayStationEastDTO);

    /**
     * Get all the railwayStationEasts.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<RailwayStationEastDTO> findAll(Pageable pageable);

    /**
     * Get the "id" railwayStationEast.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<RailwayStationEastDTO> findOne(Long id);

    /**
     * Delete the "id" railwayStationEast.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
