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
import ro.east.repository.JourneyStatusEastRepository;
import ro.east.service.JourneyStatusEastService;
import ro.east.service.dto.JourneyStatusEastDTO;
import ro.east.web.rest.errors.BadRequestAlertException;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link ro.east.domain.JourneyStatusEast}.
 */
@RestController
@RequestMapping("/api")
public class JourneyStatusEastResource {

    private final Logger log = LoggerFactory.getLogger(JourneyStatusEastResource.class);

    private static final String ENTITY_NAME = "journeyStatusEast";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final JourneyStatusEastService journeyStatusEastService;

    private final JourneyStatusEastRepository journeyStatusEastRepository;

    public JourneyStatusEastResource(
        JourneyStatusEastService journeyStatusEastService,
        JourneyStatusEastRepository journeyStatusEastRepository
    ) {
        this.journeyStatusEastService = journeyStatusEastService;
        this.journeyStatusEastRepository = journeyStatusEastRepository;
    }

    /**
     * {@code POST  /journey-status-easts} : Create a new journeyStatusEast.
     *
     * @param journeyStatusEastDTO the journeyStatusEastDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new journeyStatusEastDTO, or with status {@code 400 (Bad Request)} if the journeyStatusEast has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/journey-status-easts")
    public ResponseEntity<JourneyStatusEastDTO> createJourneyStatusEast(@RequestBody JourneyStatusEastDTO journeyStatusEastDTO)
        throws URISyntaxException {
        log.debug("REST request to save JourneyStatusEast : {}", journeyStatusEastDTO);
        if (journeyStatusEastDTO.getId() != null) {
            throw new BadRequestAlertException("A new journeyStatusEast cannot already have an ID", ENTITY_NAME, "idexists");
        }
        JourneyStatusEastDTO result = journeyStatusEastService.save(journeyStatusEastDTO);
        return ResponseEntity
            .created(new URI("/api/journey-status-easts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /journey-status-easts/:id} : Updates an existing journeyStatusEast.
     *
     * @param id the id of the journeyStatusEastDTO to save.
     * @param journeyStatusEastDTO the journeyStatusEastDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated journeyStatusEastDTO,
     * or with status {@code 400 (Bad Request)} if the journeyStatusEastDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the journeyStatusEastDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/journey-status-easts/{id}")
    public ResponseEntity<JourneyStatusEastDTO> updateJourneyStatusEast(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody JourneyStatusEastDTO journeyStatusEastDTO
    ) throws URISyntaxException {
        log.debug("REST request to update JourneyStatusEast : {}, {}", id, journeyStatusEastDTO);
        if (journeyStatusEastDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, journeyStatusEastDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!journeyStatusEastRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        JourneyStatusEastDTO result = journeyStatusEastService.update(journeyStatusEastDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, journeyStatusEastDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /journey-status-easts/:id} : Partial updates given fields of an existing journeyStatusEast, field will ignore if it is null
     *
     * @param id the id of the journeyStatusEastDTO to save.
     * @param journeyStatusEastDTO the journeyStatusEastDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated journeyStatusEastDTO,
     * or with status {@code 400 (Bad Request)} if the journeyStatusEastDTO is not valid,
     * or with status {@code 404 (Not Found)} if the journeyStatusEastDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the journeyStatusEastDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/journey-status-easts/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<JourneyStatusEastDTO> partialUpdateJourneyStatusEast(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody JourneyStatusEastDTO journeyStatusEastDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update JourneyStatusEast partially : {}, {}", id, journeyStatusEastDTO);
        if (journeyStatusEastDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, journeyStatusEastDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!journeyStatusEastRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<JourneyStatusEastDTO> result = journeyStatusEastService.partialUpdate(journeyStatusEastDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, journeyStatusEastDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /journey-status-easts} : get all the journeyStatusEasts.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of journeyStatusEasts in body.
     */
    @GetMapping("/journey-status-easts")
    public ResponseEntity<List<JourneyStatusEastDTO>> getAllJourneyStatusEasts(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of JourneyStatusEasts");
        Page<JourneyStatusEastDTO> page = journeyStatusEastService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /journey-status-easts/:id} : get the "id" journeyStatusEast.
     *
     * @param id the id of the journeyStatusEastDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the journeyStatusEastDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/journey-status-easts/{id}")
    public ResponseEntity<JourneyStatusEastDTO> getJourneyStatusEast(@PathVariable Long id) {
        log.debug("REST request to get JourneyStatusEast : {}", id);
        Optional<JourneyStatusEastDTO> journeyStatusEastDTO = journeyStatusEastService.findOne(id);
        return ResponseUtil.wrapOrNotFound(journeyStatusEastDTO);
    }

    /**
     * {@code DELETE  /journey-status-easts/:id} : delete the "id" journeyStatusEast.
     *
     * @param id the id of the journeyStatusEastDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/journey-status-easts/{id}")
    public ResponseEntity<Void> deleteJourneyStatusEast(@PathVariable Long id) {
        log.debug("REST request to delete JourneyStatusEast : {}", id);
        journeyStatusEastService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
