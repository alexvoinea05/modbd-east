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
import ro.east.repository.DistrictEastRepository;
import ro.east.service.DistrictEastService;
import ro.east.service.dto.DistrictEastDTO;
import ro.east.web.rest.errors.BadRequestAlertException;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link ro.east.domain.DistrictEast}.
 */
@RestController
@RequestMapping("/api")
public class DistrictEastResource {

    private final Logger log = LoggerFactory.getLogger(DistrictEastResource.class);

    private static final String ENTITY_NAME = "districtEast";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DistrictEastService districtEastService;

    private final DistrictEastRepository districtEastRepository;

    public DistrictEastResource(DistrictEastService districtEastService, DistrictEastRepository districtEastRepository) {
        this.districtEastService = districtEastService;
        this.districtEastRepository = districtEastRepository;
    }

    /**
     * {@code POST  /district-easts} : Create a new districtEast.
     *
     * @param districtEastDTO the districtEastDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new districtEastDTO, or with status {@code 400 (Bad Request)} if the districtEast has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/district-easts")
    public ResponseEntity<DistrictEastDTO> createDistrictEast(@RequestBody DistrictEastDTO districtEastDTO) throws URISyntaxException {
        log.debug("REST request to save DistrictEast : {}", districtEastDTO);
        if (districtEastDTO.getId() != null) {
            throw new BadRequestAlertException("A new districtEast cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DistrictEastDTO result = districtEastService.save(districtEastDTO);
        return ResponseEntity
            .created(new URI("/api/district-easts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /district-easts/:id} : Updates an existing districtEast.
     *
     * @param id the id of the districtEastDTO to save.
     * @param districtEastDTO the districtEastDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated districtEastDTO,
     * or with status {@code 400 (Bad Request)} if the districtEastDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the districtEastDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/district-easts/{id}")
    public ResponseEntity<DistrictEastDTO> updateDistrictEast(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody DistrictEastDTO districtEastDTO
    ) throws URISyntaxException {
        log.debug("REST request to update DistrictEast : {}, {}", id, districtEastDTO);
        if (districtEastDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, districtEastDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!districtEastRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        DistrictEastDTO result = districtEastService.update(districtEastDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, districtEastDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /district-easts/:id} : Partial updates given fields of an existing districtEast, field will ignore if it is null
     *
     * @param id the id of the districtEastDTO to save.
     * @param districtEastDTO the districtEastDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated districtEastDTO,
     * or with status {@code 400 (Bad Request)} if the districtEastDTO is not valid,
     * or with status {@code 404 (Not Found)} if the districtEastDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the districtEastDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/district-easts/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<DistrictEastDTO> partialUpdateDistrictEast(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody DistrictEastDTO districtEastDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update DistrictEast partially : {}, {}", id, districtEastDTO);
        if (districtEastDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, districtEastDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!districtEastRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<DistrictEastDTO> result = districtEastService.partialUpdate(districtEastDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, districtEastDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /district-easts} : get all the districtEasts.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of districtEasts in body.
     */
    @GetMapping("/district-easts")
    public ResponseEntity<List<DistrictEastDTO>> getAllDistrictEasts(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of DistrictEasts");
        Page<DistrictEastDTO> page = districtEastService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /district-easts/:id} : get the "id" districtEast.
     *
     * @param id the id of the districtEastDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the districtEastDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/district-easts/{id}")
    public ResponseEntity<DistrictEastDTO> getDistrictEast(@PathVariable Long id) {
        log.debug("REST request to get DistrictEast : {}", id);
        Optional<DistrictEastDTO> districtEastDTO = districtEastService.findOne(id);
        return ResponseUtil.wrapOrNotFound(districtEastDTO);
    }

    /**
     * {@code DELETE  /district-easts/:id} : delete the "id" districtEast.
     *
     * @param id the id of the districtEastDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/district-easts/{id}")
    public ResponseEntity<Void> deleteDistrictEast(@PathVariable Long id) {
        log.debug("REST request to delete DistrictEast : {}", id);
        districtEastService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
