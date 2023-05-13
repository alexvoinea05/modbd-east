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
import ro.east.repository.LicenseEastRepository;
import ro.east.service.LicenseEastService;
import ro.east.service.dto.LicenseEastDTO;
import ro.east.web.rest.errors.BadRequestAlertException;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link ro.east.domain.LicenseEast}.
 */
@RestController
@RequestMapping("/api")
public class LicenseEastResource {

    private final Logger log = LoggerFactory.getLogger(LicenseEastResource.class);

    private static final String ENTITY_NAME = "licenseEast";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LicenseEastService licenseEastService;

    private final LicenseEastRepository licenseEastRepository;

    public LicenseEastResource(LicenseEastService licenseEastService, LicenseEastRepository licenseEastRepository) {
        this.licenseEastService = licenseEastService;
        this.licenseEastRepository = licenseEastRepository;
    }

    /**
     * {@code POST  /license-easts} : Create a new licenseEast.
     *
     * @param licenseEastDTO the licenseEastDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new licenseEastDTO, or with status {@code 400 (Bad Request)} if the licenseEast has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/license-easts")
    public ResponseEntity<LicenseEastDTO> createLicenseEast(@RequestBody LicenseEastDTO licenseEastDTO) throws URISyntaxException {
        log.debug("REST request to save LicenseEast : {}", licenseEastDTO);
        if (licenseEastDTO.getId() != null) {
            throw new BadRequestAlertException("A new licenseEast cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LicenseEastDTO result = licenseEastService.save(licenseEastDTO);
        return ResponseEntity
            .created(new URI("/api/license-easts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /license-easts/:id} : Updates an existing licenseEast.
     *
     * @param id the id of the licenseEastDTO to save.
     * @param licenseEastDTO the licenseEastDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated licenseEastDTO,
     * or with status {@code 400 (Bad Request)} if the licenseEastDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the licenseEastDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/license-easts/{id}")
    public ResponseEntity<LicenseEastDTO> updateLicenseEast(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody LicenseEastDTO licenseEastDTO
    ) throws URISyntaxException {
        log.debug("REST request to update LicenseEast : {}, {}", id, licenseEastDTO);
        if (licenseEastDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, licenseEastDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!licenseEastRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        LicenseEastDTO result = licenseEastService.update(licenseEastDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, licenseEastDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /license-easts/:id} : Partial updates given fields of an existing licenseEast, field will ignore if it is null
     *
     * @param id the id of the licenseEastDTO to save.
     * @param licenseEastDTO the licenseEastDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated licenseEastDTO,
     * or with status {@code 400 (Bad Request)} if the licenseEastDTO is not valid,
     * or with status {@code 404 (Not Found)} if the licenseEastDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the licenseEastDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/license-easts/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<LicenseEastDTO> partialUpdateLicenseEast(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody LicenseEastDTO licenseEastDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update LicenseEast partially : {}, {}", id, licenseEastDTO);
        if (licenseEastDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, licenseEastDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!licenseEastRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<LicenseEastDTO> result = licenseEastService.partialUpdate(licenseEastDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, licenseEastDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /license-easts} : get all the licenseEasts.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of licenseEasts in body.
     */
    @GetMapping("/license-easts")
    public ResponseEntity<List<LicenseEastDTO>> getAllLicenseEasts(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of LicenseEasts");
        Page<LicenseEastDTO> page = licenseEastService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /license-easts/:id} : get the "id" licenseEast.
     *
     * @param id the id of the licenseEastDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the licenseEastDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/license-easts/{id}")
    public ResponseEntity<LicenseEastDTO> getLicenseEast(@PathVariable Long id) {
        log.debug("REST request to get LicenseEast : {}", id);
        Optional<LicenseEastDTO> licenseEastDTO = licenseEastService.findOne(id);
        return ResponseUtil.wrapOrNotFound(licenseEastDTO);
    }

    /**
     * {@code DELETE  /license-easts/:id} : delete the "id" licenseEast.
     *
     * @param id the id of the licenseEastDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/license-easts/{id}")
    public ResponseEntity<Void> deleteLicenseEast(@PathVariable Long id) {
        log.debug("REST request to delete LicenseEast : {}", id);
        licenseEastService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
