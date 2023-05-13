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
import ro.east.repository.UserTypeEastRepository;
import ro.east.service.UserTypeEastService;
import ro.east.service.dto.UserTypeEastDTO;
import ro.east.web.rest.errors.BadRequestAlertException;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link ro.east.domain.UserTypeEast}.
 */
@RestController
@RequestMapping("/api")
public class UserTypeEastResource {

    private final Logger log = LoggerFactory.getLogger(UserTypeEastResource.class);

    private static final String ENTITY_NAME = "userTypeEast";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UserTypeEastService userTypeEastService;

    private final UserTypeEastRepository userTypeEastRepository;

    public UserTypeEastResource(UserTypeEastService userTypeEastService, UserTypeEastRepository userTypeEastRepository) {
        this.userTypeEastService = userTypeEastService;
        this.userTypeEastRepository = userTypeEastRepository;
    }

    /**
     * {@code POST  /user-type-easts} : Create a new userTypeEast.
     *
     * @param userTypeEastDTO the userTypeEastDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new userTypeEastDTO, or with status {@code 400 (Bad Request)} if the userTypeEast has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/user-type-easts")
    public ResponseEntity<UserTypeEastDTO> createUserTypeEast(@RequestBody UserTypeEastDTO userTypeEastDTO) throws URISyntaxException {
        log.debug("REST request to save UserTypeEast : {}", userTypeEastDTO);
        if (userTypeEastDTO.getId() != null) {
            throw new BadRequestAlertException("A new userTypeEast cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UserTypeEastDTO result = userTypeEastService.save(userTypeEastDTO);
        return ResponseEntity
            .created(new URI("/api/user-type-easts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /user-type-easts/:id} : Updates an existing userTypeEast.
     *
     * @param id the id of the userTypeEastDTO to save.
     * @param userTypeEastDTO the userTypeEastDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated userTypeEastDTO,
     * or with status {@code 400 (Bad Request)} if the userTypeEastDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the userTypeEastDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/user-type-easts/{id}")
    public ResponseEntity<UserTypeEastDTO> updateUserTypeEast(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody UserTypeEastDTO userTypeEastDTO
    ) throws URISyntaxException {
        log.debug("REST request to update UserTypeEast : {}, {}", id, userTypeEastDTO);
        if (userTypeEastDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, userTypeEastDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!userTypeEastRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        UserTypeEastDTO result = userTypeEastService.update(userTypeEastDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, userTypeEastDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /user-type-easts/:id} : Partial updates given fields of an existing userTypeEast, field will ignore if it is null
     *
     * @param id the id of the userTypeEastDTO to save.
     * @param userTypeEastDTO the userTypeEastDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated userTypeEastDTO,
     * or with status {@code 400 (Bad Request)} if the userTypeEastDTO is not valid,
     * or with status {@code 404 (Not Found)} if the userTypeEastDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the userTypeEastDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/user-type-easts/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<UserTypeEastDTO> partialUpdateUserTypeEast(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody UserTypeEastDTO userTypeEastDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update UserTypeEast partially : {}, {}", id, userTypeEastDTO);
        if (userTypeEastDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, userTypeEastDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!userTypeEastRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<UserTypeEastDTO> result = userTypeEastService.partialUpdate(userTypeEastDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, userTypeEastDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /user-type-easts} : get all the userTypeEasts.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of userTypeEasts in body.
     */
    @GetMapping("/user-type-easts")
    public ResponseEntity<List<UserTypeEastDTO>> getAllUserTypeEasts(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of UserTypeEasts");
        Page<UserTypeEastDTO> page = userTypeEastService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /user-type-easts/:id} : get the "id" userTypeEast.
     *
     * @param id the id of the userTypeEastDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the userTypeEastDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/user-type-easts/{id}")
    public ResponseEntity<UserTypeEastDTO> getUserTypeEast(@PathVariable Long id) {
        log.debug("REST request to get UserTypeEast : {}", id);
        Optional<UserTypeEastDTO> userTypeEastDTO = userTypeEastService.findOne(id);
        return ResponseUtil.wrapOrNotFound(userTypeEastDTO);
    }

    /**
     * {@code DELETE  /user-type-easts/:id} : delete the "id" userTypeEast.
     *
     * @param id the id of the userTypeEastDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/user-type-easts/{id}")
    public ResponseEntity<Void> deleteUserTypeEast(@PathVariable Long id) {
        log.debug("REST request to delete UserTypeEast : {}", id);
        userTypeEastService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
