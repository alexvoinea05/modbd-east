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
import ro.east.repository.RailwayTypeEastRepository;
import ro.east.service.RailwayTypeEastService;
import ro.east.service.dto.RailwayTypeEastDTO;
import ro.east.web.rest.errors.BadRequestAlertException;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link ro.east.domain.RailwayTypeEast}.
 */
@RestController
@RequestMapping("/api")
public class RailwayTypeEastResource {

    private final Logger log = LoggerFactory.getLogger(RailwayTypeEastResource.class);

    private static final String ENTITY_NAME = "railwayTypeEast";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RailwayTypeEastService railwayTypeEastService;

    private final RailwayTypeEastRepository railwayTypeEastRepository;

    public RailwayTypeEastResource(RailwayTypeEastService railwayTypeEastService, RailwayTypeEastRepository railwayTypeEastRepository) {
        this.railwayTypeEastService = railwayTypeEastService;
        this.railwayTypeEastRepository = railwayTypeEastRepository;
    }

    /**
     * {@code POST  /railway-type-easts} : Create a new railwayTypeEast.
     *
     * @param railwayTypeEastDTO the railwayTypeEastDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new railwayTypeEastDTO, or with status {@code 400 (Bad Request)} if the railwayTypeEast has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/railway-type-easts")
    public ResponseEntity<RailwayTypeEastDTO> createRailwayTypeEast(@RequestBody RailwayTypeEastDTO railwayTypeEastDTO)
        throws URISyntaxException {
        log.debug("REST request to save RailwayTypeEast : {}", railwayTypeEastDTO);
        if (railwayTypeEastDTO.getId() != null) {
            throw new BadRequestAlertException("A new railwayTypeEast cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RailwayTypeEastDTO result = railwayTypeEastService.save(railwayTypeEastDTO);
        return ResponseEntity
            .created(new URI("/api/railway-type-easts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /railway-type-easts/:id} : Updates an existing railwayTypeEast.
     *
     * @param id the id of the railwayTypeEastDTO to save.
     * @param railwayTypeEastDTO the railwayTypeEastDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated railwayTypeEastDTO,
     * or with status {@code 400 (Bad Request)} if the railwayTypeEastDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the railwayTypeEastDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/railway-type-easts/{id}")
    public ResponseEntity<RailwayTypeEastDTO> updateRailwayTypeEast(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody RailwayTypeEastDTO railwayTypeEastDTO
    ) throws URISyntaxException {
        log.debug("REST request to update RailwayTypeEast : {}, {}", id, railwayTypeEastDTO);
        if (railwayTypeEastDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, railwayTypeEastDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!railwayTypeEastRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        RailwayTypeEastDTO result = railwayTypeEastService.update(railwayTypeEastDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, railwayTypeEastDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /railway-type-easts/:id} : Partial updates given fields of an existing railwayTypeEast, field will ignore if it is null
     *
     * @param id the id of the railwayTypeEastDTO to save.
     * @param railwayTypeEastDTO the railwayTypeEastDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated railwayTypeEastDTO,
     * or with status {@code 400 (Bad Request)} if the railwayTypeEastDTO is not valid,
     * or with status {@code 404 (Not Found)} if the railwayTypeEastDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the railwayTypeEastDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/railway-type-easts/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<RailwayTypeEastDTO> partialUpdateRailwayTypeEast(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody RailwayTypeEastDTO railwayTypeEastDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update RailwayTypeEast partially : {}, {}", id, railwayTypeEastDTO);
        if (railwayTypeEastDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, railwayTypeEastDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!railwayTypeEastRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<RailwayTypeEastDTO> result = railwayTypeEastService.partialUpdate(railwayTypeEastDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, railwayTypeEastDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /railway-type-easts} : get all the railwayTypeEasts.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of railwayTypeEasts in body.
     */
    @GetMapping("/railway-type-easts")
    public ResponseEntity<List<RailwayTypeEastDTO>> getAllRailwayTypeEasts(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of RailwayTypeEasts");
        Page<RailwayTypeEastDTO> page = railwayTypeEastService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /railway-type-easts/:id} : get the "id" railwayTypeEast.
     *
     * @param id the id of the railwayTypeEastDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the railwayTypeEastDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/railway-type-easts/{id}")
    public ResponseEntity<RailwayTypeEastDTO> getRailwayTypeEast(@PathVariable Long id) {
        log.debug("REST request to get RailwayTypeEast : {}", id);
        Optional<RailwayTypeEastDTO> railwayTypeEastDTO = railwayTypeEastService.findOne(id);
        return ResponseUtil.wrapOrNotFound(railwayTypeEastDTO);
    }

    /**
     * {@code DELETE  /railway-type-easts/:id} : delete the "id" railwayTypeEast.
     *
     * @param id the id of the railwayTypeEastDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/railway-type-easts/{id}")
    public ResponseEntity<Void> deleteRailwayTypeEast(@PathVariable Long id) {
        log.debug("REST request to delete RailwayTypeEast : {}", id);
        railwayTypeEastService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
