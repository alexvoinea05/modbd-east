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
import ro.east.repository.TrainTypeEastRepository;
import ro.east.service.TrainTypeEastService;
import ro.east.service.dto.TrainTypeEastDTO;
import ro.east.web.rest.errors.BadRequestAlertException;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link ro.east.domain.TrainTypeEast}.
 */
@RestController
@RequestMapping("/api")
public class TrainTypeEastResource {

    private final Logger log = LoggerFactory.getLogger(TrainTypeEastResource.class);

    private static final String ENTITY_NAME = "trainTypeEast";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TrainTypeEastService trainTypeEastService;

    private final TrainTypeEastRepository trainTypeEastRepository;

    public TrainTypeEastResource(TrainTypeEastService trainTypeEastService, TrainTypeEastRepository trainTypeEastRepository) {
        this.trainTypeEastService = trainTypeEastService;
        this.trainTypeEastRepository = trainTypeEastRepository;
    }

    /**
     * {@code POST  /train-type-easts} : Create a new trainTypeEast.
     *
     * @param trainTypeEastDTO the trainTypeEastDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new trainTypeEastDTO, or with status {@code 400 (Bad Request)} if the trainTypeEast has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/train-type-easts")
    public ResponseEntity<TrainTypeEastDTO> createTrainTypeEast(@RequestBody TrainTypeEastDTO trainTypeEastDTO) throws URISyntaxException {
        log.debug("REST request to save TrainTypeEast : {}", trainTypeEastDTO);
        if (trainTypeEastDTO.getId() != null) {
            throw new BadRequestAlertException("A new trainTypeEast cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TrainTypeEastDTO result = trainTypeEastService.save(trainTypeEastDTO);
        return ResponseEntity
            .created(new URI("/api/train-type-easts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /train-type-easts/:id} : Updates an existing trainTypeEast.
     *
     * @param id the id of the trainTypeEastDTO to save.
     * @param trainTypeEastDTO the trainTypeEastDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated trainTypeEastDTO,
     * or with status {@code 400 (Bad Request)} if the trainTypeEastDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the trainTypeEastDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/train-type-easts/{id}")
    public ResponseEntity<TrainTypeEastDTO> updateTrainTypeEast(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TrainTypeEastDTO trainTypeEastDTO
    ) throws URISyntaxException {
        log.debug("REST request to update TrainTypeEast : {}, {}", id, trainTypeEastDTO);
        if (trainTypeEastDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, trainTypeEastDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!trainTypeEastRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        TrainTypeEastDTO result = trainTypeEastService.update(trainTypeEastDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, trainTypeEastDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /train-type-easts/:id} : Partial updates given fields of an existing trainTypeEast, field will ignore if it is null
     *
     * @param id the id of the trainTypeEastDTO to save.
     * @param trainTypeEastDTO the trainTypeEastDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated trainTypeEastDTO,
     * or with status {@code 400 (Bad Request)} if the trainTypeEastDTO is not valid,
     * or with status {@code 404 (Not Found)} if the trainTypeEastDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the trainTypeEastDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/train-type-easts/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TrainTypeEastDTO> partialUpdateTrainTypeEast(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TrainTypeEastDTO trainTypeEastDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update TrainTypeEast partially : {}, {}", id, trainTypeEastDTO);
        if (trainTypeEastDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, trainTypeEastDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!trainTypeEastRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TrainTypeEastDTO> result = trainTypeEastService.partialUpdate(trainTypeEastDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, trainTypeEastDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /train-type-easts} : get all the trainTypeEasts.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of trainTypeEasts in body.
     */
    @GetMapping("/train-type-easts")
    public ResponseEntity<List<TrainTypeEastDTO>> getAllTrainTypeEasts(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of TrainTypeEasts");
        Page<TrainTypeEastDTO> page = trainTypeEastService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /train-type-easts/:id} : get the "id" trainTypeEast.
     *
     * @param id the id of the trainTypeEastDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the trainTypeEastDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/train-type-easts/{id}")
    public ResponseEntity<TrainTypeEastDTO> getTrainTypeEast(@PathVariable Long id) {
        log.debug("REST request to get TrainTypeEast : {}", id);
        Optional<TrainTypeEastDTO> trainTypeEastDTO = trainTypeEastService.findOne(id);
        return ResponseUtil.wrapOrNotFound(trainTypeEastDTO);
    }

    /**
     * {@code DELETE  /train-type-easts/:id} : delete the "id" trainTypeEast.
     *
     * @param id the id of the trainTypeEastDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/train-type-easts/{id}")
    public ResponseEntity<Void> deleteTrainTypeEast(@PathVariable Long id) {
        log.debug("REST request to delete TrainTypeEast : {}", id);
        trainTypeEastService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
