package ro.east.service;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ro.east.service.dto.TicketEastDTO;

/**
 * Service Interface for managing {@link ro.east.domain.TicketEast}.
 */
public interface TicketEastService {
    /**
     * Save a ticketEast.
     *
     * @param ticketEastDTO the entity to save.
     * @return the persisted entity.
     */
    TicketEastDTO save(TicketEastDTO ticketEastDTO);

    /**
     * Updates a ticketEast.
     *
     * @param ticketEastDTO the entity to update.
     * @return the persisted entity.
     */
    TicketEastDTO update(TicketEastDTO ticketEastDTO);

    /**
     * Partially updates a ticketEast.
     *
     * @param ticketEastDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<TicketEastDTO> partialUpdate(TicketEastDTO ticketEastDTO);

    /**
     * Get all the ticketEasts.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TicketEastDTO> findAll(Pageable pageable);

    /**
     * Get the "id" ticketEast.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TicketEastDTO> findOne(Long id);

    /**
     * Delete the "id" ticketEast.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
