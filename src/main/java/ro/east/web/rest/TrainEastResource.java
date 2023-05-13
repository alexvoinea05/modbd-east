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
import ro.east.repository.TrainEastRepository;
import ro.east.service.TrainEastService;
import ro.east.service.dto.TrainEastDTO;
import ro.east.web.rest.errors.BadRequestAlertException;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link ro.east.domain.TrainEast}.
 */
@RestController
@RequestMapping("/api")
public class TrainEastResource {

    private final Logger log = LoggerFactory.getLogger(TrainEastResource.class);

    private static final String ENTITY_NAME = "trainEast";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TrainEastService trainEastService;

    private final TrainEastRepository trainEastRepository;

    public TrainEastResource(TrainEastService trainEastService, TrainEastRepository trainEastRepository) {
        this.trainEastService = trainEastService;
        this.trainEastRepository = trainEastRepository;
    }

    /**
     * {@code POST  /train-easts} : Create a new trainEast.
     *
     * @param trainEastDTO the trainEastDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new trainEastDTO, or with status {@code 400 (Bad Request)} if the trainEast has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/train-easts")
    public ResponseEntity<TrainEastDTO> createTrainEast(@RequestBody TrainEastDTO trainEastDTO) throws URISyntaxException {
        log.debug("REST request to save TrainEast : {}", trainEastDTO);
        if (trainEastDTO.getId() != null) {
            throw new BadRequestAlertException("A new trainEast cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TrainEastDTO result = trainEastService.save(trainEastDTO);
        return ResponseEntity
            .created(new URI("/api/train-easts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /train-easts/:id} : Updates an existing trainEast.
     *
     * @param id the id of the trainEastDTO to save.
     * @param trainEastDTO the trainEastDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated trainEastDTO,
     * or with status {@code 400 (Bad Request)} if the trainEastDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the trainEastDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/train-easts/{id}")
    public ResponseEntity<TrainEastDTO> updateTrainEast(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TrainEastDTO trainEastDTO
    ) throws URISyntaxException {
        log.debug("REST request to update TrainEast : {}, {}", id, trainEastDTO);
        if (trainEastDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, trainEastDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!trainEastRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        TrainEastDTO result = trainEastService.update(trainEastDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, trainEastDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /train-easts/:id} : Partial updates given fields of an existing trainEast, field will ignore if it is null
     *
     * @param id the id of the trainEastDTO to save.
     * @param trainEastDTO the trainEastDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated trainEastDTO,
     * or with status {@code 400 (Bad Request)} if the trainEastDTO is not valid,
     * or with status {@code 404 (Not Found)} if the trainEastDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the trainEastDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/train-easts/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TrainEastDTO> partialUpdateTrainEast(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TrainEastDTO trainEastDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update TrainEast partially : {}, {}", id, trainEastDTO);
        if (trainEastDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, trainEastDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!trainEastRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TrainEastDTO> result = trainEastService.partialUpdate(trainEastDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, trainEastDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /train-easts} : get all the trainEasts.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of trainEasts in body.
     */
    @GetMapping("/train-easts")
    public ResponseEntity<List<TrainEastDTO>> getAllTrainEasts(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of TrainEasts");
        Page<TrainEastDTO> page = trainEastService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /train-easts/:id} : get the "id" trainEast.
     *
     * @param id the id of the trainEastDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the trainEastDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/train-easts/{id}")
    public ResponseEntity<TrainEastDTO> getTrainEast(@PathVariable Long id) {
        log.debug("REST request to get TrainEast : {}", id);
        Optional<TrainEastDTO> trainEastDTO = trainEastService.findOne(id);
        return ResponseUtil.wrapOrNotFound(trainEastDTO);
    }

    /**
     * {@code DELETE  /train-easts/:id} : delete the "id" trainEast.
     *
     * @param id the id of the trainEastDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/train-easts/{id}")
    public ResponseEntity<Void> deleteTrainEast(@PathVariable Long id) {
        log.debug("REST request to delete TrainEast : {}", id);
        trainEastService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
