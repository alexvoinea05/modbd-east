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
import ro.east.repository.FuelTypeEastRepository;
import ro.east.service.FuelTypeEastService;
import ro.east.service.dto.FuelTypeEastDTO;
import ro.east.web.rest.errors.BadRequestAlertException;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link ro.east.domain.FuelTypeEast}.
 */
@RestController
@RequestMapping("/api")
public class FuelTypeEastResource {

    private final Logger log = LoggerFactory.getLogger(FuelTypeEastResource.class);

    private static final String ENTITY_NAME = "fuelTypeEast";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FuelTypeEastService fuelTypeEastService;

    private final FuelTypeEastRepository fuelTypeEastRepository;

    public FuelTypeEastResource(FuelTypeEastService fuelTypeEastService, FuelTypeEastRepository fuelTypeEastRepository) {
        this.fuelTypeEastService = fuelTypeEastService;
        this.fuelTypeEastRepository = fuelTypeEastRepository;
    }

    /**
     * {@code POST  /fuel-type-easts} : Create a new fuelTypeEast.
     *
     * @param fuelTypeEastDTO the fuelTypeEastDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new fuelTypeEastDTO, or with status {@code 400 (Bad Request)} if the fuelTypeEast has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/fuel-type-easts")
    public ResponseEntity<FuelTypeEastDTO> createFuelTypeEast(@RequestBody FuelTypeEastDTO fuelTypeEastDTO) throws URISyntaxException {
        log.debug("REST request to save FuelTypeEast : {}", fuelTypeEastDTO);
        if (fuelTypeEastDTO.getId() != null) {
            throw new BadRequestAlertException("A new fuelTypeEast cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FuelTypeEastDTO result = fuelTypeEastService.save(fuelTypeEastDTO);
        return ResponseEntity
            .created(new URI("/api/fuel-type-easts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /fuel-type-easts/:id} : Updates an existing fuelTypeEast.
     *
     * @param id the id of the fuelTypeEastDTO to save.
     * @param fuelTypeEastDTO the fuelTypeEastDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fuelTypeEastDTO,
     * or with status {@code 400 (Bad Request)} if the fuelTypeEastDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the fuelTypeEastDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/fuel-type-easts/{id}")
    public ResponseEntity<FuelTypeEastDTO> updateFuelTypeEast(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody FuelTypeEastDTO fuelTypeEastDTO
    ) throws URISyntaxException {
        log.debug("REST request to update FuelTypeEast : {}, {}", id, fuelTypeEastDTO);
        if (fuelTypeEastDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, fuelTypeEastDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!fuelTypeEastRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        FuelTypeEastDTO result = fuelTypeEastService.update(fuelTypeEastDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, fuelTypeEastDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /fuel-type-easts/:id} : Partial updates given fields of an existing fuelTypeEast, field will ignore if it is null
     *
     * @param id the id of the fuelTypeEastDTO to save.
     * @param fuelTypeEastDTO the fuelTypeEastDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fuelTypeEastDTO,
     * or with status {@code 400 (Bad Request)} if the fuelTypeEastDTO is not valid,
     * or with status {@code 404 (Not Found)} if the fuelTypeEastDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the fuelTypeEastDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/fuel-type-easts/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<FuelTypeEastDTO> partialUpdateFuelTypeEast(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody FuelTypeEastDTO fuelTypeEastDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update FuelTypeEast partially : {}, {}", id, fuelTypeEastDTO);
        if (fuelTypeEastDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, fuelTypeEastDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!fuelTypeEastRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<FuelTypeEastDTO> result = fuelTypeEastService.partialUpdate(fuelTypeEastDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, fuelTypeEastDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /fuel-type-easts} : get all the fuelTypeEasts.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of fuelTypeEasts in body.
     */
    @GetMapping("/fuel-type-easts")
    public ResponseEntity<List<FuelTypeEastDTO>> getAllFuelTypeEasts(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of FuelTypeEasts");
        Page<FuelTypeEastDTO> page = fuelTypeEastService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /fuel-type-easts/:id} : get the "id" fuelTypeEast.
     *
     * @param id the id of the fuelTypeEastDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the fuelTypeEastDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/fuel-type-easts/{id}")
    public ResponseEntity<FuelTypeEastDTO> getFuelTypeEast(@PathVariable Long id) {
        log.debug("REST request to get FuelTypeEast : {}", id);
        Optional<FuelTypeEastDTO> fuelTypeEastDTO = fuelTypeEastService.findOne(id);
        return ResponseUtil.wrapOrNotFound(fuelTypeEastDTO);
    }

    /**
     * {@code DELETE  /fuel-type-easts/:id} : delete the "id" fuelTypeEast.
     *
     * @param id the id of the fuelTypeEastDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/fuel-type-easts/{id}")
    public ResponseEntity<Void> deleteFuelTypeEast(@PathVariable Long id) {
        log.debug("REST request to delete FuelTypeEast : {}", id);
        fuelTypeEastService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
