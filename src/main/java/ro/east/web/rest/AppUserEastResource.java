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
import ro.east.repository.AppUserEastRepository;
import ro.east.service.AppUserEastService;
import ro.east.service.dto.AppUserEastDTO;
import ro.east.web.rest.errors.BadRequestAlertException;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link ro.east.domain.AppUserEast}.
 */
@RestController
@RequestMapping("/api")
public class AppUserEastResource {

    private final Logger log = LoggerFactory.getLogger(AppUserEastResource.class);

    private static final String ENTITY_NAME = "appUserEast";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AppUserEastService appUserEastService;

    private final AppUserEastRepository appUserEastRepository;

    public AppUserEastResource(AppUserEastService appUserEastService, AppUserEastRepository appUserEastRepository) {
        this.appUserEastService = appUserEastService;
        this.appUserEastRepository = appUserEastRepository;
    }

    /**
     * {@code POST  /app-user-easts} : Create a new appUserEast.
     *
     * @param appUserEastDTO the appUserEastDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new appUserEastDTO, or with status {@code 400 (Bad Request)} if the appUserEast has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/app-user-easts")
    public ResponseEntity<AppUserEastDTO> createAppUserEast(@RequestBody AppUserEastDTO appUserEastDTO) throws URISyntaxException {
        log.debug("REST request to save AppUserEast : {}", appUserEastDTO);
        if (appUserEastDTO.getId() != null) {
            throw new BadRequestAlertException("A new appUserEast cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AppUserEastDTO result = appUserEastService.save(appUserEastDTO);
        return ResponseEntity
            .created(new URI("/api/app-user-easts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /app-user-easts/:id} : Updates an existing appUserEast.
     *
     * @param id the id of the appUserEastDTO to save.
     * @param appUserEastDTO the appUserEastDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated appUserEastDTO,
     * or with status {@code 400 (Bad Request)} if the appUserEastDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the appUserEastDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/app-user-easts/{id}")
    public ResponseEntity<AppUserEastDTO> updateAppUserEast(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AppUserEastDTO appUserEastDTO
    ) throws URISyntaxException {
        log.debug("REST request to update AppUserEast : {}, {}", id, appUserEastDTO);
        if (appUserEastDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, appUserEastDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!appUserEastRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        AppUserEastDTO result = appUserEastService.update(appUserEastDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, appUserEastDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /app-user-easts/:id} : Partial updates given fields of an existing appUserEast, field will ignore if it is null
     *
     * @param id the id of the appUserEastDTO to save.
     * @param appUserEastDTO the appUserEastDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated appUserEastDTO,
     * or with status {@code 400 (Bad Request)} if the appUserEastDTO is not valid,
     * or with status {@code 404 (Not Found)} if the appUserEastDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the appUserEastDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/app-user-easts/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AppUserEastDTO> partialUpdateAppUserEast(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AppUserEastDTO appUserEastDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update AppUserEast partially : {}, {}", id, appUserEastDTO);
        if (appUserEastDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, appUserEastDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!appUserEastRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AppUserEastDTO> result = appUserEastService.partialUpdate(appUserEastDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, appUserEastDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /app-user-easts} : get all the appUserEasts.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of appUserEasts in body.
     */
    @GetMapping("/app-user-easts")
    public ResponseEntity<List<AppUserEastDTO>> getAllAppUserEasts(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of AppUserEasts");
        Page<AppUserEastDTO> page = appUserEastService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /app-user-easts/:id} : get the "id" appUserEast.
     *
     * @param id the id of the appUserEastDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the appUserEastDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/app-user-easts/{id}")
    public ResponseEntity<AppUserEastDTO> getAppUserEast(@PathVariable Long id) {
        log.debug("REST request to get AppUserEast : {}", id);
        Optional<AppUserEastDTO> appUserEastDTO = appUserEastService.findOne(id);
        return ResponseUtil.wrapOrNotFound(appUserEastDTO);
    }

    /**
     * {@code DELETE  /app-user-easts/:id} : delete the "id" appUserEast.
     *
     * @param id the id of the appUserEastDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/app-user-easts/{id}")
    public ResponseEntity<Void> deleteAppUserEast(@PathVariable Long id) {
        log.debug("REST request to delete AppUserEast : {}", id);
        appUserEastService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
