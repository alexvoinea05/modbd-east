package ro.east.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ro.east.repository.TicketEastRepository;
import ro.east.service.TicketEastService;
import ro.east.service.dto.TicketEastDTO;
import ro.east.web.rest.errors.BadRequestAlertException;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link ro.east.domain.TicketEast}.
 */
@RestController
@RequestMapping("/api")
public class TicketEastResource {

    private final Logger log = LoggerFactory.getLogger(TicketEastResource.class);

    private static final String ENTITY_NAME = "ticketEast";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TicketEastService ticketEastService;

    private final TicketEastRepository ticketEastRepository;

    public TicketEastResource(TicketEastService ticketEastService, TicketEastRepository ticketEastRepository) {
        this.ticketEastService = ticketEastService;
        this.ticketEastRepository = ticketEastRepository;
    }

    /**
     * {@code POST  /ticket-easts} : Create a new ticketEast.
     *
     * @param ticketEastDTO the ticketEastDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new ticketEastDTO, or with status {@code 400 (Bad Request)} if the ticketEast has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/ticket-easts")
    public ResponseEntity<TicketEastDTO> createTicketEast(@RequestBody TicketEastDTO ticketEastDTO) throws URISyntaxException {
        log.debug("REST request to save TicketEast : {}", ticketEastDTO);
        if (ticketEastDTO.getId() != null) {
            throw new BadRequestAlertException("A new ticketEast cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TicketEastDTO result = ticketEastService.save(ticketEastDTO);
        return ResponseEntity
            .created(new URI("/api/ticket-easts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /ticket-easts/:id} : Updates an existing ticketEast.
     *
     * @param id the id of the ticketEastDTO to save.
     * @param ticketEastDTO the ticketEastDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ticketEastDTO,
     * or with status {@code 400 (Bad Request)} if the ticketEastDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the ticketEastDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/ticket-easts/{id}")
    public ResponseEntity<TicketEastDTO> updateTicketEast(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TicketEastDTO ticketEastDTO
    ) throws URISyntaxException {
        log.debug("REST request to update TicketEast : {}, {}", id, ticketEastDTO);
        if (ticketEastDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ticketEastDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ticketEastRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        TicketEastDTO result = ticketEastService.update(ticketEastDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, ticketEastDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /ticket-easts/:id} : Partial updates given fields of an existing ticketEast, field will ignore if it is null
     *
     * @param id the id of the ticketEastDTO to save.
     * @param ticketEastDTO the ticketEastDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ticketEastDTO,
     * or with status {@code 400 (Bad Request)} if the ticketEastDTO is not valid,
     * or with status {@code 404 (Not Found)} if the ticketEastDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the ticketEastDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/ticket-easts/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TicketEastDTO> partialUpdateTicketEast(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TicketEastDTO ticketEastDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update TicketEast partially : {}, {}", id, ticketEastDTO);
        if (ticketEastDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ticketEastDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ticketEastRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TicketEastDTO> result = ticketEastService.partialUpdate(ticketEastDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, ticketEastDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /ticket-easts} : get all the ticketEasts.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of ticketEasts in body.
     */
    @GetMapping("/ticket-easts")
    public ResponseEntity<List<TicketEastDTO>> getAllTicketEasts(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of TicketEasts");
        Page<TicketEastDTO> page = ticketEastService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /ticket-easts/:id} : get the "id" ticketEast.
     *
     * @param id the id of the ticketEastDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the ticketEastDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/ticket-easts/{id}")
    public ResponseEntity<TicketEastDTO> getTicketEast(@PathVariable Long id) {
        log.debug("REST request to get TicketEast : {}", id);
        Optional<TicketEastDTO> ticketEastDTO = ticketEastService.findOne(id);
        return ResponseUtil.wrapOrNotFound(ticketEastDTO);
    }

    /**
     * {@code DELETE  /ticket-easts/:id} : delete the "id" ticketEast.
     *
     * @param id the id of the ticketEastDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/ticket-easts/{id}")
    public ResponseEntity<Void> deleteTicketEast(@PathVariable Long id) {
        log.debug("REST request to delete TicketEast : {}", id);
        ticketEastService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
