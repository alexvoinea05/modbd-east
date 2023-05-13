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
import ro.east.repository.RailwayStationEastRepository;
import ro.east.service.RailwayStationEastService;
import ro.east.service.dto.RailwayStationEastDTO;
import ro.east.web.rest.errors.BadRequestAlertException;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link ro.east.domain.RailwayStationEast}.
 */
@RestController
@RequestMapping("/api")
public class RailwayStationEastResource {

    private final Logger log = LoggerFactory.getLogger(RailwayStationEastResource.class);

    private static final String ENTITY_NAME = "railwayStationEast";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RailwayStationEastService railwayStationEastService;

    private final RailwayStationEastRepository railwayStationEastRepository;

    public RailwayStationEastResource(
        RailwayStationEastService railwayStationEastService,
        RailwayStationEastRepository railwayStationEastRepository
    ) {
        this.railwayStationEastService = railwayStationEastService;
        this.railwayStationEastRepository = railwayStationEastRepository;
    }

    /**
     * {@code POST  /railway-station-easts} : Create a new railwayStationEast.
     *
     * @param railwayStationEastDTO the railwayStationEastDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new railwayStationEastDTO, or with status {@code 400 (Bad Request)} if the railwayStationEast has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/railway-station-easts")
    public ResponseEntity<RailwayStationEastDTO> createRailwayStationEast(@RequestBody RailwayStationEastDTO railwayStationEastDTO)
        throws URISyntaxException {
        log.debug("REST request to save RailwayStationEast : {}", railwayStationEastDTO);
        if (railwayStationEastDTO.getId() != null) {
            throw new BadRequestAlertException("A new railwayStationEast cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RailwayStationEastDTO result = railwayStationEastService.save(railwayStationEastDTO);
        return ResponseEntity
            .created(new URI("/api/railway-station-easts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /railway-station-easts/:id} : Updates an existing railwayStationEast.
     *
     * @param id the id of the railwayStationEastDTO to save.
     * @param railwayStationEastDTO the railwayStationEastDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated railwayStationEastDTO,
     * or with status {@code 400 (Bad Request)} if the railwayStationEastDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the railwayStationEastDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/railway-station-easts/{id}")
    public ResponseEntity<RailwayStationEastDTO> updateRailwayStationEast(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody RailwayStationEastDTO railwayStationEastDTO
    ) throws URISyntaxException {
        log.debug("REST request to update RailwayStationEast : {}, {}", id, railwayStationEastDTO);
        if (railwayStationEastDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, railwayStationEastDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!railwayStationEastRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        RailwayStationEastDTO result = railwayStationEastService.update(railwayStationEastDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, railwayStationEastDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /railway-station-easts/:id} : Partial updates given fields of an existing railwayStationEast, field will ignore if it is null
     *
     * @param id the id of the railwayStationEastDTO to save.
     * @param railwayStationEastDTO the railwayStationEastDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated railwayStationEastDTO,
     * or with status {@code 400 (Bad Request)} if the railwayStationEastDTO is not valid,
     * or with status {@code 404 (Not Found)} if the railwayStationEastDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the railwayStationEastDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/railway-station-easts/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<RailwayStationEastDTO> partialUpdateRailwayStationEast(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody RailwayStationEastDTO railwayStationEastDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update RailwayStationEast partially : {}, {}", id, railwayStationEastDTO);
        if (railwayStationEastDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, railwayStationEastDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!railwayStationEastRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<RailwayStationEastDTO> result = railwayStationEastService.partialUpdate(railwayStationEastDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, railwayStationEastDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /railway-station-easts} : get all the railwayStationEasts.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of railwayStationEasts in body.
     */
    @GetMapping("/railway-station-easts")
    public ResponseEntity<List<RailwayStationEastDTO>> getAllRailwayStationEasts(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of RailwayStationEasts");
        Page<RailwayStationEastDTO> page = railwayStationEastService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /railway-station-easts/:id} : get the "id" railwayStationEast.
     *
     * @param id the id of the railwayStationEastDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the railwayStationEastDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/railway-station-easts/{id}")
    public ResponseEntity<RailwayStationEastDTO> getRailwayStationEast(@PathVariable Long id) {
        log.debug("REST request to get RailwayStationEast : {}", id);
        Optional<RailwayStationEastDTO> railwayStationEastDTO = railwayStationEastService.findOne(id);
        return ResponseUtil.wrapOrNotFound(railwayStationEastDTO);
    }

    /**
     * {@code DELETE  /railway-station-easts/:id} : delete the "id" railwayStationEast.
     *
     * @param id the id of the railwayStationEastDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/railway-station-easts/{id}")
    public ResponseEntity<Void> deleteRailwayStationEast(@PathVariable Long id) {
        log.debug("REST request to delete RailwayStationEast : {}", id);
        railwayStationEastService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
