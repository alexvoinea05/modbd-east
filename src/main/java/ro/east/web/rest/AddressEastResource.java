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
import ro.east.repository.AddressEastRepository;
import ro.east.service.AddressEastService;
import ro.east.service.dto.AddressEastDTO;
import ro.east.web.rest.errors.BadRequestAlertException;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link ro.east.domain.AddressEast}.
 */
@RestController
@RequestMapping("/api")
public class AddressEastResource {

    private final Logger log = LoggerFactory.getLogger(AddressEastResource.class);

    private static final String ENTITY_NAME = "addressEast";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AddressEastService addressEastService;

    private final AddressEastRepository addressEastRepository;

    public AddressEastResource(AddressEastService addressEastService, AddressEastRepository addressEastRepository) {
        this.addressEastService = addressEastService;
        this.addressEastRepository = addressEastRepository;
    }

    /**
     * {@code POST  /address-easts} : Create a new addressEast.
     *
     * @param addressEastDTO the addressEastDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new addressEastDTO, or with status {@code 400 (Bad Request)} if the addressEast has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/address-easts")
    public ResponseEntity<AddressEastDTO> createAddressEast(@RequestBody AddressEastDTO addressEastDTO) throws URISyntaxException {
        log.debug("REST request to save AddressEast : {}", addressEastDTO);
        if (addressEastDTO.getId() != null) {
            throw new BadRequestAlertException("A new addressEast cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AddressEastDTO result = addressEastService.save(addressEastDTO);
        return ResponseEntity
            .created(new URI("/api/address-easts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /address-easts/:id} : Updates an existing addressEast.
     *
     * @param id the id of the addressEastDTO to save.
     * @param addressEastDTO the addressEastDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated addressEastDTO,
     * or with status {@code 400 (Bad Request)} if the addressEastDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the addressEastDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/address-easts/{id}")
    public ResponseEntity<AddressEastDTO> updateAddressEast(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AddressEastDTO addressEastDTO
    ) throws URISyntaxException {
        log.debug("REST request to update AddressEast : {}, {}", id, addressEastDTO);
        if (addressEastDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, addressEastDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!addressEastRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        AddressEastDTO result = addressEastService.update(addressEastDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, addressEastDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /address-easts/:id} : Partial updates given fields of an existing addressEast, field will ignore if it is null
     *
     * @param id the id of the addressEastDTO to save.
     * @param addressEastDTO the addressEastDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated addressEastDTO,
     * or with status {@code 400 (Bad Request)} if the addressEastDTO is not valid,
     * or with status {@code 404 (Not Found)} if the addressEastDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the addressEastDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/address-easts/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AddressEastDTO> partialUpdateAddressEast(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AddressEastDTO addressEastDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update AddressEast partially : {}, {}", id, addressEastDTO);
        if (addressEastDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, addressEastDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!addressEastRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AddressEastDTO> result = addressEastService.partialUpdate(addressEastDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, addressEastDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /address-easts} : get all the addressEasts.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of addressEasts in body.
     */
    @GetMapping("/address-easts")
    public ResponseEntity<List<AddressEastDTO>> getAllAddressEasts(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of AddressEasts");
        Page<AddressEastDTO> page = addressEastService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /address-easts/:id} : get the "id" addressEast.
     *
     * @param id the id of the addressEastDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the addressEastDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/address-easts/{id}")
    public ResponseEntity<AddressEastDTO> getAddressEast(@PathVariable Long id) {
        log.debug("REST request to get AddressEast : {}", id);
        Optional<AddressEastDTO> addressEastDTO = addressEastService.findOne(id);
        return ResponseUtil.wrapOrNotFound(addressEastDTO);
    }

    /**
     * {@code DELETE  /address-easts/:id} : delete the "id" addressEast.
     *
     * @param id the id of the addressEastDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/address-easts/{id}")
    public ResponseEntity<Void> deleteAddressEast(@PathVariable Long id) {
        log.debug("REST request to delete AddressEast : {}", id);
        addressEastService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
