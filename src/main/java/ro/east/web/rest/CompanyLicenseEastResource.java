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
import ro.east.repository.CompanyLicenseEastRepository;
import ro.east.service.CompanyLicenseEastService;
import ro.east.service.dto.CompanyLicenseEastDTO;
import ro.east.web.rest.errors.BadRequestAlertException;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link ro.east.domain.CompanyLicenseEast}.
 */
@RestController
@RequestMapping("/api")
public class CompanyLicenseEastResource {

    private final Logger log = LoggerFactory.getLogger(CompanyLicenseEastResource.class);

    private static final String ENTITY_NAME = "companyLicenseEast";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CompanyLicenseEastService companyLicenseEastService;

    private final CompanyLicenseEastRepository companyLicenseEastRepository;

    public CompanyLicenseEastResource(
        CompanyLicenseEastService companyLicenseEastService,
        CompanyLicenseEastRepository companyLicenseEastRepository
    ) {
        this.companyLicenseEastService = companyLicenseEastService;
        this.companyLicenseEastRepository = companyLicenseEastRepository;
    }

    /**
     * {@code POST  /company-license-easts} : Create a new companyLicenseEast.
     *
     * @param companyLicenseEastDTO the companyLicenseEastDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new companyLicenseEastDTO, or with status {@code 400 (Bad Request)} if the companyLicenseEast has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/company-license-easts")
    public ResponseEntity<CompanyLicenseEastDTO> createCompanyLicenseEast(@RequestBody CompanyLicenseEastDTO companyLicenseEastDTO)
        throws URISyntaxException {
        log.debug("REST request to save CompanyLicenseEast : {}", companyLicenseEastDTO);
        if (companyLicenseEastDTO.getId() != null) {
            throw new BadRequestAlertException("A new companyLicenseEast cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CompanyLicenseEastDTO result = companyLicenseEastService.save(companyLicenseEastDTO);
        return ResponseEntity
            .created(new URI("/api/company-license-easts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /company-license-easts/:id} : Updates an existing companyLicenseEast.
     *
     * @param id the id of the companyLicenseEastDTO to save.
     * @param companyLicenseEastDTO the companyLicenseEastDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated companyLicenseEastDTO,
     * or with status {@code 400 (Bad Request)} if the companyLicenseEastDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the companyLicenseEastDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/company-license-easts/{id}")
    public ResponseEntity<CompanyLicenseEastDTO> updateCompanyLicenseEast(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CompanyLicenseEastDTO companyLicenseEastDTO
    ) throws URISyntaxException {
        log.debug("REST request to update CompanyLicenseEast : {}, {}", id, companyLicenseEastDTO);
        if (companyLicenseEastDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, companyLicenseEastDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!companyLicenseEastRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CompanyLicenseEastDTO result = companyLicenseEastService.update(companyLicenseEastDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, companyLicenseEastDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /company-license-easts/:id} : Partial updates given fields of an existing companyLicenseEast, field will ignore if it is null
     *
     * @param id the id of the companyLicenseEastDTO to save.
     * @param companyLicenseEastDTO the companyLicenseEastDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated companyLicenseEastDTO,
     * or with status {@code 400 (Bad Request)} if the companyLicenseEastDTO is not valid,
     * or with status {@code 404 (Not Found)} if the companyLicenseEastDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the companyLicenseEastDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/company-license-easts/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CompanyLicenseEastDTO> partialUpdateCompanyLicenseEast(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CompanyLicenseEastDTO companyLicenseEastDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update CompanyLicenseEast partially : {}, {}", id, companyLicenseEastDTO);
        if (companyLicenseEastDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, companyLicenseEastDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!companyLicenseEastRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CompanyLicenseEastDTO> result = companyLicenseEastService.partialUpdate(companyLicenseEastDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, companyLicenseEastDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /company-license-easts} : get all the companyLicenseEasts.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of companyLicenseEasts in body.
     */
    @GetMapping("/company-license-easts")
    public ResponseEntity<List<CompanyLicenseEastDTO>> getAllCompanyLicenseEasts(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of CompanyLicenseEasts");
        Page<CompanyLicenseEastDTO> page = companyLicenseEastService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /company-license-easts/:id} : get the "id" companyLicenseEast.
     *
     * @param id the id of the companyLicenseEastDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the companyLicenseEastDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/company-license-easts/{id}")
    public ResponseEntity<CompanyLicenseEastDTO> getCompanyLicenseEast(@PathVariable Long id) {
        log.debug("REST request to get CompanyLicenseEast : {}", id);
        Optional<CompanyLicenseEastDTO> companyLicenseEastDTO = companyLicenseEastService.findOne(id);
        return ResponseUtil.wrapOrNotFound(companyLicenseEastDTO);
    }

    /**
     * {@code DELETE  /company-license-easts/:id} : delete the "id" companyLicenseEast.
     *
     * @param id the id of the companyLicenseEastDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/company-license-easts/{id}")
    public ResponseEntity<Void> deleteCompanyLicenseEast(@PathVariable Long id) {
        log.debug("REST request to delete CompanyLicenseEast : {}", id);
        companyLicenseEastService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
