package ro.east.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import ro.east.IntegrationTest;
import ro.east.domain.FuelTypeEast;
import ro.east.repository.FuelTypeEastRepository;
import ro.east.service.dto.FuelTypeEastDTO;
import ro.east.service.mapper.FuelTypeEastMapper;

/**
 * Integration tests for the {@link FuelTypeEastResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class FuelTypeEastResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/fuel-type-easts";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private FuelTypeEastRepository fuelTypeEastRepository;

    @Autowired
    private FuelTypeEastMapper fuelTypeEastMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFuelTypeEastMockMvc;

    private FuelTypeEast fuelTypeEast;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FuelTypeEast createEntity(EntityManager em) {
        FuelTypeEast fuelTypeEast = new FuelTypeEast().code(DEFAULT_CODE).description(DEFAULT_DESCRIPTION);
        return fuelTypeEast;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FuelTypeEast createUpdatedEntity(EntityManager em) {
        FuelTypeEast fuelTypeEast = new FuelTypeEast().code(UPDATED_CODE).description(UPDATED_DESCRIPTION);
        return fuelTypeEast;
    }

    @BeforeEach
    public void initTest() {
        fuelTypeEast = createEntity(em);
    }

    @Test
    @Transactional
    void createFuelTypeEast() throws Exception {
        int databaseSizeBeforeCreate = fuelTypeEastRepository.findAll().size();
        // Create the FuelTypeEast
        FuelTypeEastDTO fuelTypeEastDTO = fuelTypeEastMapper.toDto(fuelTypeEast);
        restFuelTypeEastMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fuelTypeEastDTO))
            )
            .andExpect(status().isCreated());

        // Validate the FuelTypeEast in the database
        List<FuelTypeEast> fuelTypeEastList = fuelTypeEastRepository.findAll();
        assertThat(fuelTypeEastList).hasSize(databaseSizeBeforeCreate + 1);
        FuelTypeEast testFuelTypeEast = fuelTypeEastList.get(fuelTypeEastList.size() - 1);
        assertThat(testFuelTypeEast.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testFuelTypeEast.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    void createFuelTypeEastWithExistingId() throws Exception {
        // Create the FuelTypeEast with an existing ID
        fuelTypeEast.setId(1L);
        FuelTypeEastDTO fuelTypeEastDTO = fuelTypeEastMapper.toDto(fuelTypeEast);

        int databaseSizeBeforeCreate = fuelTypeEastRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFuelTypeEastMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fuelTypeEastDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FuelTypeEast in the database
        List<FuelTypeEast> fuelTypeEastList = fuelTypeEastRepository.findAll();
        assertThat(fuelTypeEastList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllFuelTypeEasts() throws Exception {
        // Initialize the database
        fuelTypeEastRepository.saveAndFlush(fuelTypeEast);

        // Get all the fuelTypeEastList
        restFuelTypeEastMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fuelTypeEast.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }

    @Test
    @Transactional
    void getFuelTypeEast() throws Exception {
        // Initialize the database
        fuelTypeEastRepository.saveAndFlush(fuelTypeEast);

        // Get the fuelTypeEast
        restFuelTypeEastMockMvc
            .perform(get(ENTITY_API_URL_ID, fuelTypeEast.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(fuelTypeEast.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }

    @Test
    @Transactional
    void getNonExistingFuelTypeEast() throws Exception {
        // Get the fuelTypeEast
        restFuelTypeEastMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingFuelTypeEast() throws Exception {
        // Initialize the database
        fuelTypeEastRepository.saveAndFlush(fuelTypeEast);

        int databaseSizeBeforeUpdate = fuelTypeEastRepository.findAll().size();

        // Update the fuelTypeEast
        FuelTypeEast updatedFuelTypeEast = fuelTypeEastRepository.findById(fuelTypeEast.getId()).get();
        // Disconnect from session so that the updates on updatedFuelTypeEast are not directly saved in db
        em.detach(updatedFuelTypeEast);
        updatedFuelTypeEast.code(UPDATED_CODE).description(UPDATED_DESCRIPTION);
        FuelTypeEastDTO fuelTypeEastDTO = fuelTypeEastMapper.toDto(updatedFuelTypeEast);

        restFuelTypeEastMockMvc
            .perform(
                put(ENTITY_API_URL_ID, fuelTypeEastDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fuelTypeEastDTO))
            )
            .andExpect(status().isOk());

        // Validate the FuelTypeEast in the database
        List<FuelTypeEast> fuelTypeEastList = fuelTypeEastRepository.findAll();
        assertThat(fuelTypeEastList).hasSize(databaseSizeBeforeUpdate);
        FuelTypeEast testFuelTypeEast = fuelTypeEastList.get(fuelTypeEastList.size() - 1);
        assertThat(testFuelTypeEast.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testFuelTypeEast.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void putNonExistingFuelTypeEast() throws Exception {
        int databaseSizeBeforeUpdate = fuelTypeEastRepository.findAll().size();
        fuelTypeEast.setId(count.incrementAndGet());

        // Create the FuelTypeEast
        FuelTypeEastDTO fuelTypeEastDTO = fuelTypeEastMapper.toDto(fuelTypeEast);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFuelTypeEastMockMvc
            .perform(
                put(ENTITY_API_URL_ID, fuelTypeEastDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fuelTypeEastDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FuelTypeEast in the database
        List<FuelTypeEast> fuelTypeEastList = fuelTypeEastRepository.findAll();
        assertThat(fuelTypeEastList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchFuelTypeEast() throws Exception {
        int databaseSizeBeforeUpdate = fuelTypeEastRepository.findAll().size();
        fuelTypeEast.setId(count.incrementAndGet());

        // Create the FuelTypeEast
        FuelTypeEastDTO fuelTypeEastDTO = fuelTypeEastMapper.toDto(fuelTypeEast);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFuelTypeEastMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fuelTypeEastDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FuelTypeEast in the database
        List<FuelTypeEast> fuelTypeEastList = fuelTypeEastRepository.findAll();
        assertThat(fuelTypeEastList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamFuelTypeEast() throws Exception {
        int databaseSizeBeforeUpdate = fuelTypeEastRepository.findAll().size();
        fuelTypeEast.setId(count.incrementAndGet());

        // Create the FuelTypeEast
        FuelTypeEastDTO fuelTypeEastDTO = fuelTypeEastMapper.toDto(fuelTypeEast);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFuelTypeEastMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fuelTypeEastDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the FuelTypeEast in the database
        List<FuelTypeEast> fuelTypeEastList = fuelTypeEastRepository.findAll();
        assertThat(fuelTypeEastList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateFuelTypeEastWithPatch() throws Exception {
        // Initialize the database
        fuelTypeEastRepository.saveAndFlush(fuelTypeEast);

        int databaseSizeBeforeUpdate = fuelTypeEastRepository.findAll().size();

        // Update the fuelTypeEast using partial update
        FuelTypeEast partialUpdatedFuelTypeEast = new FuelTypeEast();
        partialUpdatedFuelTypeEast.setId(fuelTypeEast.getId());

        restFuelTypeEastMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFuelTypeEast.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFuelTypeEast))
            )
            .andExpect(status().isOk());

        // Validate the FuelTypeEast in the database
        List<FuelTypeEast> fuelTypeEastList = fuelTypeEastRepository.findAll();
        assertThat(fuelTypeEastList).hasSize(databaseSizeBeforeUpdate);
        FuelTypeEast testFuelTypeEast = fuelTypeEastList.get(fuelTypeEastList.size() - 1);
        assertThat(testFuelTypeEast.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testFuelTypeEast.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    void fullUpdateFuelTypeEastWithPatch() throws Exception {
        // Initialize the database
        fuelTypeEastRepository.saveAndFlush(fuelTypeEast);

        int databaseSizeBeforeUpdate = fuelTypeEastRepository.findAll().size();

        // Update the fuelTypeEast using partial update
        FuelTypeEast partialUpdatedFuelTypeEast = new FuelTypeEast();
        partialUpdatedFuelTypeEast.setId(fuelTypeEast.getId());

        partialUpdatedFuelTypeEast.code(UPDATED_CODE).description(UPDATED_DESCRIPTION);

        restFuelTypeEastMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFuelTypeEast.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFuelTypeEast))
            )
            .andExpect(status().isOk());

        // Validate the FuelTypeEast in the database
        List<FuelTypeEast> fuelTypeEastList = fuelTypeEastRepository.findAll();
        assertThat(fuelTypeEastList).hasSize(databaseSizeBeforeUpdate);
        FuelTypeEast testFuelTypeEast = fuelTypeEastList.get(fuelTypeEastList.size() - 1);
        assertThat(testFuelTypeEast.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testFuelTypeEast.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void patchNonExistingFuelTypeEast() throws Exception {
        int databaseSizeBeforeUpdate = fuelTypeEastRepository.findAll().size();
        fuelTypeEast.setId(count.incrementAndGet());

        // Create the FuelTypeEast
        FuelTypeEastDTO fuelTypeEastDTO = fuelTypeEastMapper.toDto(fuelTypeEast);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFuelTypeEastMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, fuelTypeEastDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(fuelTypeEastDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FuelTypeEast in the database
        List<FuelTypeEast> fuelTypeEastList = fuelTypeEastRepository.findAll();
        assertThat(fuelTypeEastList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchFuelTypeEast() throws Exception {
        int databaseSizeBeforeUpdate = fuelTypeEastRepository.findAll().size();
        fuelTypeEast.setId(count.incrementAndGet());

        // Create the FuelTypeEast
        FuelTypeEastDTO fuelTypeEastDTO = fuelTypeEastMapper.toDto(fuelTypeEast);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFuelTypeEastMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(fuelTypeEastDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FuelTypeEast in the database
        List<FuelTypeEast> fuelTypeEastList = fuelTypeEastRepository.findAll();
        assertThat(fuelTypeEastList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamFuelTypeEast() throws Exception {
        int databaseSizeBeforeUpdate = fuelTypeEastRepository.findAll().size();
        fuelTypeEast.setId(count.incrementAndGet());

        // Create the FuelTypeEast
        FuelTypeEastDTO fuelTypeEastDTO = fuelTypeEastMapper.toDto(fuelTypeEast);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFuelTypeEastMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(fuelTypeEastDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the FuelTypeEast in the database
        List<FuelTypeEast> fuelTypeEastList = fuelTypeEastRepository.findAll();
        assertThat(fuelTypeEastList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteFuelTypeEast() throws Exception {
        // Initialize the database
        fuelTypeEastRepository.saveAndFlush(fuelTypeEast);

        int databaseSizeBeforeDelete = fuelTypeEastRepository.findAll().size();

        // Delete the fuelTypeEast
        restFuelTypeEastMockMvc
            .perform(delete(ENTITY_API_URL_ID, fuelTypeEast.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<FuelTypeEast> fuelTypeEastList = fuelTypeEastRepository.findAll();
        assertThat(fuelTypeEastList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
