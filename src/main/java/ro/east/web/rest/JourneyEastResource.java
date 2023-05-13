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
import ro.east.repository.JourneyEastRepository;
import ro.east.service.JourneyEastService;
import ro.east.service.dto.JourneyEastDTO;
import ro.east.web.rest.errors.BadRequestAlertException;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link ro.east.domain.JourneyEast}.
 */
@RestController
@RequestMapping("/api")
public class JourneyEastResource {

    private final Logger log = LoggerFactory.getLogger(JourneyEastResource.class);

    private static final String ENTITY_NAME = "journeyEast";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final JourneyEastService journeyEastService;

    private final JourneyEastRepository journeyEastRepository;

    public JourneyEastResource(JourneyEastService journeyEastService, JourneyEastRepository journeyEastRepository) {
        this.journeyEastService = journeyEastService;
        this.journeyEastRepository = journeyEastRepository;
    }

    /**
     * {@code POST  /journey-easts} : Create a new journeyEast.
     *
     * @param journeyEastDTO the journeyEastDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new journeyEastDTO, or with status {@code 400 (Bad Request)} if the journeyEast has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/journey-easts")
    public ResponseEntity<JourneyEastDTO> createJourneyEast(@RequestBody JourneyEastDTO journeyEastDTO) throws URISyntaxException {
        log.debug("REST request to save JourneyEast : {}", journeyEastDTO);
        if (journeyEastDTO.getId() != null) {
            throw new BadRequestAlertException("A new journeyEast cannot already have an ID", ENTITY_NAME, "idexists");
        }
        JourneyEastDTO result = journeyEastService.save(journeyEastDTO);
        return ResponseEntity
            .created(new URI("/api/journey-easts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /journey-easts/:id} : Updates an existing journeyEast.
     *
     * @param id the id of the journeyEastDTO to save.
     * @param journeyEastDTO the journeyEastDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated journeyEastDTO,
     * or with status {@code 400 (Bad Request)} if the journeyEastDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the journeyEastDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/journey-easts/{id}")
    public ResponseEntity<JourneyEastDTO> updateJourneyEast(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody JourneyEastDTO journeyEastDTO
    ) throws URISyntaxException {
        log.debug("REST request to update JourneyEast : {}, {}", id, journeyEastDTO);
        if (journeyEastDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, journeyEastDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!journeyEastRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        JourneyEastDTO result = journeyEastService.update(journeyEastDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, journeyEastDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /journey-easts/:id} : Partial updates given fields of an existing journeyEast, field will ignore if it is null
     *
     * @param id the id of the journeyEastDTO to save.
     * @param journeyEastDTO the journeyEastDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated journeyEastDTO,
     * or with status {@code 400 (Bad Request)} if the journeyEastDTO is not valid,
     * or with status {@code 404 (Not Found)} if the journeyEastDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the journeyEastDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/journey-easts/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<JourneyEastDTO> partialUpdateJourneyEast(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody JourneyEastDTO journeyEastDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update JourneyEast partially : {}, {}", id, journeyEastDTO);
        if (journeyEastDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, journeyEastDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!journeyEastRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<JourneyEastDTO> result = journeyEastService.partialUpdate(journeyEastDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, journeyEastDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /journey-easts} : get all the journeyEasts.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of journeyEasts in body.
     */
    @GetMapping("/journey-easts")
    public ResponseEntity<List<JourneyEastDTO>> getAllJourneyEasts(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of JourneyEasts");
        Page<JourneyEastDTO> page = journeyEastService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /journey-easts/:id} : get the "id" journeyEast.
     *
     * @param id the id of the journeyEastDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the journeyEastDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/journey-easts/{id}")
    public ResponseEntity<JourneyEastDTO> getJourneyEast(@PathVariable Long id) {
        log.debug("REST request to get JourneyEast : {}", id);
        Optional<JourneyEastDTO> journeyEastDTO = journeyEastService.findOne(id);
        return ResponseUtil.wrapOrNotFound(journeyEastDTO);
    }

    /**
     * {@code DELETE  /journey-easts/:id} : delete the "id" journeyEast.
     *
     * @param id the id of the journeyEastDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/journey-easts/{id}")
    public ResponseEntity<Void> deleteJourneyEast(@PathVariable Long id) {
        log.debug("REST request to delete JourneyEast : {}", id);
        journeyEastService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
