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
import ro.east.domain.RailwayTypeEast;
import ro.east.repository.RailwayTypeEastRepository;
import ro.east.service.dto.RailwayTypeEastDTO;
import ro.east.service.mapper.RailwayTypeEastMapper;

/**
 * Integration tests for the {@link RailwayTypeEastResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class RailwayTypeEastResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/railway-type-easts";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private RailwayTypeEastRepository railwayTypeEastRepository;

    @Autowired
    private RailwayTypeEastMapper railwayTypeEastMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRailwayTypeEastMockMvc;

    private RailwayTypeEast railwayTypeEast;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RailwayTypeEast createEntity(EntityManager em) {
        RailwayTypeEast railwayTypeEast = new RailwayTypeEast().code(DEFAULT_CODE).description(DEFAULT_DESCRIPTION);
        return railwayTypeEast;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RailwayTypeEast createUpdatedEntity(EntityManager em) {
        RailwayTypeEast railwayTypeEast = new RailwayTypeEast().code(UPDATED_CODE).description(UPDATED_DESCRIPTION);
        return railwayTypeEast;
    }

    @BeforeEach
    public void initTest() {
        railwayTypeEast = createEntity(em);
    }

    @Test
    @Transactional
    void createRailwayTypeEast() throws Exception {
        int databaseSizeBeforeCreate = railwayTypeEastRepository.findAll().size();
        // Create the RailwayTypeEast
        RailwayTypeEastDTO railwayTypeEastDTO = railwayTypeEastMapper.toDto(railwayTypeEast);
        restRailwayTypeEastMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(railwayTypeEastDTO))
            )
            .andExpect(status().isCreated());

        // Validate the RailwayTypeEast in the database
        List<RailwayTypeEast> railwayTypeEastList = railwayTypeEastRepository.findAll();
        assertThat(railwayTypeEastList).hasSize(databaseSizeBeforeCreate + 1);
        RailwayTypeEast testRailwayTypeEast = railwayTypeEastList.get(railwayTypeEastList.size() - 1);
        assertThat(testRailwayTypeEast.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testRailwayTypeEast.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    void createRailwayTypeEastWithExistingId() throws Exception {
        // Create the RailwayTypeEast with an existing ID
        railwayTypeEast.setId(1L);
        RailwayTypeEastDTO railwayTypeEastDTO = railwayTypeEastMapper.toDto(railwayTypeEast);

        int databaseSizeBeforeCreate = railwayTypeEastRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRailwayTypeEastMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(railwayTypeEastDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RailwayTypeEast in the database
        List<RailwayTypeEast> railwayTypeEastList = railwayTypeEastRepository.findAll();
        assertThat(railwayTypeEastList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllRailwayTypeEasts() throws Exception {
        // Initialize the database
        railwayTypeEastRepository.saveAndFlush(railwayTypeEast);

        // Get all the railwayTypeEastList
        restRailwayTypeEastMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(railwayTypeEast.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }

    @Test
    @Transactional
    void getRailwayTypeEast() throws Exception {
        // Initialize the database
        railwayTypeEastRepository.saveAndFlush(railwayTypeEast);

        // Get the railwayTypeEast
        restRailwayTypeEastMockMvc
            .perform(get(ENTITY_API_URL_ID, railwayTypeEast.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(railwayTypeEast.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }

    @Test
    @Transactional
    void getNonExistingRailwayTypeEast() throws Exception {
        // Get the railwayTypeEast
        restRailwayTypeEastMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingRailwayTypeEast() throws Exception {
        // Initialize the database
        railwayTypeEastRepository.saveAndFlush(railwayTypeEast);

        int databaseSizeBeforeUpdate = railwayTypeEastRepository.findAll().size();

        // Update the railwayTypeEast
        RailwayTypeEast updatedRailwayTypeEast = railwayTypeEastRepository.findById(railwayTypeEast.getId()).get();
        // Disconnect from session so that the updates on updatedRailwayTypeEast are not directly saved in db
        em.detach(updatedRailwayTypeEast);
        updatedRailwayTypeEast.code(UPDATED_CODE).description(UPDATED_DESCRIPTION);
        RailwayTypeEastDTO railwayTypeEastDTO = railwayTypeEastMapper.toDto(updatedRailwayTypeEast);

        restRailwayTypeEastMockMvc
            .perform(
                put(ENTITY_API_URL_ID, railwayTypeEastDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(railwayTypeEastDTO))
            )
            .andExpect(status().isOk());

        // Validate the RailwayTypeEast in the database
        List<RailwayTypeEast> railwayTypeEastList = railwayTypeEastRepository.findAll();
        assertThat(railwayTypeEastList).hasSize(databaseSizeBeforeUpdate);
        RailwayTypeEast testRailwayTypeEast = railwayTypeEastList.get(railwayTypeEastList.size() - 1);
        assertThat(testRailwayTypeEast.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testRailwayTypeEast.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void putNonExistingRailwayTypeEast() throws Exception {
        int databaseSizeBeforeUpdate = railwayTypeEastRepository.findAll().size();
        railwayTypeEast.setId(count.incrementAndGet());

        // Create the RailwayTypeEast
        RailwayTypeEastDTO railwayTypeEastDTO = railwayTypeEastMapper.toDto(railwayTypeEast);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRailwayTypeEastMockMvc
            .perform(
                put(ENTITY_API_URL_ID, railwayTypeEastDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(railwayTypeEastDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RailwayTypeEast in the database
        List<RailwayTypeEast> railwayTypeEastList = railwayTypeEastRepository.findAll();
        assertThat(railwayTypeEastList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchRailwayTypeEast() throws Exception {
        int databaseSizeBeforeUpdate = railwayTypeEastRepository.findAll().size();
        railwayTypeEast.setId(count.incrementAndGet());

        // Create the RailwayTypeEast
        RailwayTypeEastDTO railwayTypeEastDTO = railwayTypeEastMapper.toDto(railwayTypeEast);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRailwayTypeEastMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(railwayTypeEastDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RailwayTypeEast in the database
        List<RailwayTypeEast> railwayTypeEastList = railwayTypeEastRepository.findAll();
        assertThat(railwayTypeEastList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamRailwayTypeEast() throws Exception {
        int databaseSizeBeforeUpdate = railwayTypeEastRepository.findAll().size();
        railwayTypeEast.setId(count.incrementAndGet());

        // Create the RailwayTypeEast
        RailwayTypeEastDTO railwayTypeEastDTO = railwayTypeEastMapper.toDto(railwayTypeEast);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRailwayTypeEastMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(railwayTypeEastDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the RailwayTypeEast in the database
        List<RailwayTypeEast> railwayTypeEastList = railwayTypeEastRepository.findAll();
        assertThat(railwayTypeEastList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateRailwayTypeEastWithPatch() throws Exception {
        // Initialize the database
        railwayTypeEastRepository.saveAndFlush(railwayTypeEast);

        int databaseSizeBeforeUpdate = railwayTypeEastRepository.findAll().size();

        // Update the railwayTypeEast using partial update
        RailwayTypeEast partialUpdatedRailwayTypeEast = new RailwayTypeEast();
        partialUpdatedRailwayTypeEast.setId(railwayTypeEast.getId());

        restRailwayTypeEastMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRailwayTypeEast.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRailwayTypeEast))
            )
            .andExpect(status().isOk());

        // Validate the RailwayTypeEast in the database
        List<RailwayTypeEast> railwayTypeEastList = railwayTypeEastRepository.findAll();
        assertThat(railwayTypeEastList).hasSize(databaseSizeBeforeUpdate);
        RailwayTypeEast testRailwayTypeEast = railwayTypeEastList.get(railwayTypeEastList.size() - 1);
        assertThat(testRailwayTypeEast.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testRailwayTypeEast.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    void fullUpdateRailwayTypeEastWithPatch() throws Exception {
        // Initialize the database
        railwayTypeEastRepository.saveAndFlush(railwayTypeEast);

        int databaseSizeBeforeUpdate = railwayTypeEastRepository.findAll().size();

        // Update the railwayTypeEast using partial update
        RailwayTypeEast partialUpdatedRailwayTypeEast = new RailwayTypeEast();
        partialUpdatedRailwayTypeEast.setId(railwayTypeEast.getId());

        partialUpdatedRailwayTypeEast.code(UPDATED_CODE).description(UPDATED_DESCRIPTION);

        restRailwayTypeEastMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRailwayTypeEast.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRailwayTypeEast))
            )
            .andExpect(status().isOk());

        // Validate the RailwayTypeEast in the database
        List<RailwayTypeEast> railwayTypeEastList = railwayTypeEastRepository.findAll();
        assertThat(railwayTypeEastList).hasSize(databaseSizeBeforeUpdate);
        RailwayTypeEast testRailwayTypeEast = railwayTypeEastList.get(railwayTypeEastList.size() - 1);
        assertThat(testRailwayTypeEast.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testRailwayTypeEast.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void patchNonExistingRailwayTypeEast() throws Exception {
        int databaseSizeBeforeUpdate = railwayTypeEastRepository.findAll().size();
        railwayTypeEast.setId(count.incrementAndGet());

        // Create the RailwayTypeEast
        RailwayTypeEastDTO railwayTypeEastDTO = railwayTypeEastMapper.toDto(railwayTypeEast);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRailwayTypeEastMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, railwayTypeEastDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(railwayTypeEastDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RailwayTypeEast in the database
        List<RailwayTypeEast> railwayTypeEastList = railwayTypeEastRepository.findAll();
        assertThat(railwayTypeEastList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchRailwayTypeEast() throws Exception {
        int databaseSizeBeforeUpdate = railwayTypeEastRepository.findAll().size();
        railwayTypeEast.setId(count.incrementAndGet());

        // Create the RailwayTypeEast
        RailwayTypeEastDTO railwayTypeEastDTO = railwayTypeEastMapper.toDto(railwayTypeEast);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRailwayTypeEastMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(railwayTypeEastDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RailwayTypeEast in the database
        List<RailwayTypeEast> railwayTypeEastList = railwayTypeEastRepository.findAll();
        assertThat(railwayTypeEastList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamRailwayTypeEast() throws Exception {
        int databaseSizeBeforeUpdate = railwayTypeEastRepository.findAll().size();
        railwayTypeEast.setId(count.incrementAndGet());

        // Create the RailwayTypeEast
        RailwayTypeEastDTO railwayTypeEastDTO = railwayTypeEastMapper.toDto(railwayTypeEast);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRailwayTypeEastMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(railwayTypeEastDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the RailwayTypeEast in the database
        List<RailwayTypeEast> railwayTypeEastList = railwayTypeEastRepository.findAll();
        assertThat(railwayTypeEastList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteRailwayTypeEast() throws Exception {
        // Initialize the database
        railwayTypeEastRepository.saveAndFlush(railwayTypeEast);

        int databaseSizeBeforeDelete = railwayTypeEastRepository.findAll().size();

        // Delete the railwayTypeEast
        restRailwayTypeEastMockMvc
            .perform(delete(ENTITY_API_URL_ID, railwayTypeEast.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<RailwayTypeEast> railwayTypeEastList = railwayTypeEastRepository.findAll();
        assertThat(railwayTypeEastList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
