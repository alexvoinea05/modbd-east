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
import ro.east.repository.CityEastRepository;
import ro.east.service.CityEastService;
import ro.east.service.dto.CityEastDTO;
import ro.east.web.rest.errors.BadRequestAlertException;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link ro.east.domain.CityEast}.
 */
@RestController
@RequestMapping("/api")
public class CityEastResource {

    private final Logger log = LoggerFactory.getLogger(CityEastResource.class);

    private static final String ENTITY_NAME = "cityEast";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CityEastService cityEastService;

    private final CityEastRepository cityEastRepository;

    public CityEastResource(CityEastService cityEastService, CityEastRepository cityEastRepository) {
        this.cityEastService = cityEastService;
        this.cityEastRepository = cityEastRepository;
    }

    /**
     * {@code POST  /city-easts} : Create a new cityEast.
     *
     * @param cityEastDTO the cityEastDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cityEastDTO, or with status {@code 400 (Bad Request)} if the cityEast has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/city-easts")
    public ResponseEntity<CityEastDTO> createCityEast(@RequestBody CityEastDTO cityEastDTO) throws URISyntaxException {
        log.debug("REST request to save CityEast : {}", cityEastDTO);
        if (cityEastDTO.getId() != null) {
            throw new BadRequestAlertException("A new cityEast cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CityEastDTO result = cityEastService.save(cityEastDTO);
        return ResponseEntity
            .created(new URI("/api/city-easts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /city-easts/:id} : Updates an existing cityEast.
     *
     * @param id the id of the cityEastDTO to save.
     * @param cityEastDTO the cityEastDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cityEastDTO,
     * or with status {@code 400 (Bad Request)} if the cityEastDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cityEastDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/city-easts/{id}")
    public ResponseEntity<CityEastDTO> updateCityEast(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CityEastDTO cityEastDTO
    ) throws URISyntaxException {
        log.debug("REST request to update CityEast : {}, {}", id, cityEastDTO);
        if (cityEastDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, cityEastDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!cityEastRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CityEastDTO result = cityEastService.update(cityEastDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, cityEastDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /city-easts/:id} : Partial updates given fields of an existing cityEast, field will ignore if it is null
     *
     * @param id the id of the cityEastDTO to save.
     * @param cityEastDTO the cityEastDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cityEastDTO,
     * or with status {@code 400 (Bad Request)} if the cityEastDTO is not valid,
     * or with status {@code 404 (Not Found)} if the cityEastDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the cityEastDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/city-easts/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CityEastDTO> partialUpdateCityEast(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CityEastDTO cityEastDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update CityEast partially : {}, {}", id, cityEastDTO);
        if (cityEastDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, cityEastDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!cityEastRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CityEastDTO> result = cityEastService.partialUpdate(cityEastDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, cityEastDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /city-easts} : get all the cityEasts.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cityEasts in body.
     */
    @GetMapping("/city-easts")
    public ResponseEntity<List<CityEastDTO>> getAllCityEasts(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of CityEasts");
        Page<CityEastDTO> page = cityEastService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /city-easts/:id} : get the "id" cityEast.
     *
     * @param id the id of the cityEastDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cityEastDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/city-easts/{id}")
    public ResponseEntity<CityEastDTO> getCityEast(@PathVariable Long id) {
        log.debug("REST request to get CityEast : {}", id);
        Optional<CityEastDTO> cityEastDTO = cityEastService.findOne(id);
        return ResponseUtil.wrapOrNotFound(cityEastDTO);
    }

    /**
     * {@code DELETE  /city-easts/:id} : delete the "id" cityEast.
     *
     * @param id the id of the cityEastDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/city-easts/{id}")
    public ResponseEntity<Void> deleteCityEast(@PathVariable Long id) {
        log.debug("REST request to delete CityEast : {}", id);
        cityEastService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
