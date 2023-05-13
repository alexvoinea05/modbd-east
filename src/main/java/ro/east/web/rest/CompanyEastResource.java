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
import ro.east.repository.CompanyEastRepository;
import ro.east.service.CompanyEastService;
import ro.east.service.dto.CompanyEastDTO;
import ro.east.web.rest.errors.BadRequestAlertException;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link ro.east.domain.CompanyEast}.
 */
@RestController
@RequestMapping("/api")
public class CompanyEastResource {

    private final Logger log = LoggerFactory.getLogger(CompanyEastResource.class);

    private static final String ENTITY_NAME = "companyEast";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CompanyEastService companyEastService;

    private final CompanyEastRepository companyEastRepository;

    public CompanyEastResource(CompanyEastService companyEastService, CompanyEastRepository companyEastRepository) {
        this.companyEastService = companyEastService;
        this.companyEastRepository = companyEastRepository;
    }

    /**
     * {@code POST  /company-easts} : Create a new companyEast.
     *
     * @param companyEastDTO the companyEastDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new companyEastDTO, or with status {@code 400 (Bad Request)} if the companyEast has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/company-easts")
    public ResponseEntity<CompanyEastDTO> createCompanyEast(@RequestBody CompanyEastDTO companyEastDTO) throws URISyntaxException {
        log.debug("REST request to save CompanyEast : {}", companyEastDTO);
        if (companyEastDTO.getId() != null) {
            throw new BadRequestAlertException("A new companyEast cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CompanyEastDTO result = companyEastService.save(companyEastDTO);
        return ResponseEntity
            .created(new URI("/api/company-easts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /company-easts/:id} : Updates an existing companyEast.
     *
     * @param id the id of the companyEastDTO to save.
     * @param companyEastDTO the companyEastDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated companyEastDTO,
     * or with status {@code 400 (Bad Request)} if the companyEastDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the companyEastDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/company-easts/{id}")
    public ResponseEntity<CompanyEastDTO> updateCompanyEast(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CompanyEastDTO companyEastDTO
    ) throws URISyntaxException {
        log.debug("REST request to update CompanyEast : {}, {}", id, companyEastDTO);
        if (companyEastDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, companyEastDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!companyEastRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CompanyEastDTO result = companyEastService.update(companyEastDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, companyEastDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /company-easts/:id} : Partial updates given fields of an existing companyEast, field will ignore if it is null
     *
     * @param id the id of the companyEastDTO to save.
     * @param companyEastDTO the companyEastDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated companyEastDTO,
     * or with status {@code 400 (Bad Request)} if the companyEastDTO is not valid,
     * or with status {@code 404 (Not Found)} if the companyEastDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the companyEastDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/company-easts/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CompanyEastDTO> partialUpdateCompanyEast(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CompanyEastDTO companyEastDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update CompanyEast partially : {}, {}", id, companyEastDTO);
        if (companyEastDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, companyEastDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!companyEastRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CompanyEastDTO> result = companyEastService.partialUpdate(companyEastDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, companyEastDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /company-easts} : get all the companyEasts.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of companyEasts in body.
     */
    @GetMapping("/company-easts")
    public ResponseEntity<List<CompanyEastDTO>> getAllCompanyEasts(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of CompanyEasts");
        Page<CompanyEastDTO> page = companyEastService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /company-easts/:id} : get the "id" companyEast.
     *
     * @param id the id of the companyEastDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the companyEastDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/company-easts/{id}")
    public ResponseEntity<CompanyEastDTO> getCompanyEast(@PathVariable Long id) {
        log.debug("REST request to get CompanyEast : {}", id);
        Optional<CompanyEastDTO> companyEastDTO = companyEastService.findOne(id);
        return ResponseUtil.wrapOrNotFound(companyEastDTO);
    }

    /**
     * {@code DELETE  /company-easts/:id} : delete the "id" companyEast.
     *
     * @param id the id of the companyEastDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/company-easts/{id}")
    public ResponseEntity<Void> deleteCompanyEast(@PathVariable Long id) {
        log.debug("REST request to delete CompanyEast : {}", id);
        companyEastService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
