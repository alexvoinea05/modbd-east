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
import ro.east.domain.RailwayStationEast;
import ro.east.repository.RailwayStationEastRepository;
import ro.east.service.dto.RailwayStationEastDTO;
import ro.east.service.mapper.RailwayStationEastMapper;

/**
 * Integration tests for the {@link RailwayStationEastResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class RailwayStationEastResourceIT {

    private static final String DEFAULT_RAILWAY_STATION_NAME = "AAAAAAAAAA";
    private static final String UPDATED_RAILWAY_STATION_NAME = "BBBBBBBBBB";

    private static final Long DEFAULT_RAILWAY_TYPE_ID = 1L;
    private static final Long UPDATED_RAILWAY_TYPE_ID = 2L;

    private static final Long DEFAULT_ADDRESS_ID = 1L;
    private static final Long UPDATED_ADDRESS_ID = 2L;

    private static final String ENTITY_API_URL = "/api/railway-station-easts";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private RailwayStationEastRepository railwayStationEastRepository;

    @Autowired
    private RailwayStationEastMapper railwayStationEastMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRailwayStationEastMockMvc;

    private RailwayStationEast railwayStationEast;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RailwayStationEast createEntity(EntityManager em) {
        RailwayStationEast railwayStationEast = new RailwayStationEast()
            .railwayStationName(DEFAULT_RAILWAY_STATION_NAME)
            .railwayTypeId(DEFAULT_RAILWAY_TYPE_ID)
            .addressId(DEFAULT_ADDRESS_ID);
        return railwayStationEast;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RailwayStationEast createUpdatedEntity(EntityManager em) {
        RailwayStationEast railwayStationEast = new RailwayStationEast()
            .railwayStationName(UPDATED_RAILWAY_STATION_NAME)
            .railwayTypeId(UPDATED_RAILWAY_TYPE_ID)
            .addressId(UPDATED_ADDRESS_ID);
        return railwayStationEast;
    }

    @BeforeEach
    public void initTest() {
        railwayStationEast = createEntity(em);
    }

    @Test
    @Transactional
    void createRailwayStationEast() throws Exception {
        int databaseSizeBeforeCreate = railwayStationEastRepository.findAll().size();
        // Create the RailwayStationEast
        RailwayStationEastDTO railwayStationEastDTO = railwayStationEastMapper.toDto(railwayStationEast);
        restRailwayStationEastMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(railwayStationEastDTO))
            )
            .andExpect(status().isCreated());

        // Validate the RailwayStationEast in the database
        List<RailwayStationEast> railwayStationEastList = railwayStationEastRepository.findAll();
        assertThat(railwayStationEastList).hasSize(databaseSizeBeforeCreate + 1);
        RailwayStationEast testRailwayStationEast = railwayStationEastList.get(railwayStationEastList.size() - 1);
        assertThat(testRailwayStationEast.getRailwayStationName()).isEqualTo(DEFAULT_RAILWAY_STATION_NAME);
        assertThat(testRailwayStationEast.getRailwayTypeId()).isEqualTo(DEFAULT_RAILWAY_TYPE_ID);
        assertThat(testRailwayStationEast.getAddressId()).isEqualTo(DEFAULT_ADDRESS_ID);
    }

    @Test
    @Transactional
    void createRailwayStationEastWithExistingId() throws Exception {
        // Create the RailwayStationEast with an existing ID
        railwayStationEast.setId(1L);
        RailwayStationEastDTO railwayStationEastDTO = railwayStationEastMapper.toDto(railwayStationEast);

        int databaseSizeBeforeCreate = railwayStationEastRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRailwayStationEastMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(railwayStationEastDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RailwayStationEast in the database
        List<RailwayStationEast> railwayStationEastList = railwayStationEastRepository.findAll();
        assertThat(railwayStationEastList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllRailwayStationEasts() throws Exception {
        // Initialize the database
        railwayStationEastRepository.saveAndFlush(railwayStationEast);

        // Get all the railwayStationEastList
        restRailwayStationEastMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(railwayStationEast.getId().intValue())))
            .andExpect(jsonPath("$.[*].railwayStationName").value(hasItem(DEFAULT_RAILWAY_STATION_NAME)))
            .andExpect(jsonPath("$.[*].railwayTypeId").value(hasItem(DEFAULT_RAILWAY_TYPE_ID.intValue())))
            .andExpect(jsonPath("$.[*].addressId").value(hasItem(DEFAULT_ADDRESS_ID.intValue())));
    }

    @Test
    @Transactional
    void getRailwayStationEast() throws Exception {
        // Initialize the database
        railwayStationEastRepository.saveAndFlush(railwayStationEast);

        // Get the railwayStationEast
        restRailwayStationEastMockMvc
            .perform(get(ENTITY_API_URL_ID, railwayStationEast.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(railwayStationEast.getId().intValue()))
            .andExpect(jsonPath("$.railwayStationName").value(DEFAULT_RAILWAY_STATION_NAME))
            .andExpect(jsonPath("$.railwayTypeId").value(DEFAULT_RAILWAY_TYPE_ID.intValue()))
            .andExpect(jsonPath("$.addressId").value(DEFAULT_ADDRESS_ID.intValue()));
    }

    @Test
    @Transactional
    void getNonExistingRailwayStationEast() throws Exception {
        // Get the railwayStationEast
        restRailwayStationEastMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingRailwayStationEast() throws Exception {
        // Initialize the database
        railwayStationEastRepository.saveAndFlush(railwayStationEast);

        int databaseSizeBeforeUpdate = railwayStationEastRepository.findAll().size();

        // Update the railwayStationEast
        RailwayStationEast updatedRailwayStationEast = railwayStationEastRepository.findById(railwayStationEast.getId()).get();
        // Disconnect from session so that the updates on updatedRailwayStationEast are not directly saved in db
        em.detach(updatedRailwayStationEast);
        updatedRailwayStationEast
            .railwayStationName(UPDATED_RAILWAY_STATION_NAME)
            .railwayTypeId(UPDATED_RAILWAY_TYPE_ID)
            .addressId(UPDATED_ADDRESS_ID);
        RailwayStationEastDTO railwayStationEastDTO = railwayStationEastMapper.toDto(updatedRailwayStationEast);

        restRailwayStationEastMockMvc
            .perform(
                put(ENTITY_API_URL_ID, railwayStationEastDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(railwayStationEastDTO))
            )
            .andExpect(status().isOk());

        // Validate the RailwayStationEast in the database
        List<RailwayStationEast> railwayStationEastList = railwayStationEastRepository.findAll();
        assertThat(railwayStationEastList).hasSize(databaseSizeBeforeUpdate);
        RailwayStationEast testRailwayStationEast = railwayStationEastList.get(railwayStationEastList.size() - 1);
        assertThat(testRailwayStationEast.getRailwayStationName()).isEqualTo(UPDATED_RAILWAY_STATION_NAME);
        assertThat(testRailwayStationEast.getRailwayTypeId()).isEqualTo(UPDATED_RAILWAY_TYPE_ID);
        assertThat(testRailwayStationEast.getAddressId()).isEqualTo(UPDATED_ADDRESS_ID);
    }

    @Test
    @Transactional
    void putNonExistingRailwayStationEast() throws Exception {
        int databaseSizeBeforeUpdate = railwayStationEastRepository.findAll().size();
        railwayStationEast.setId(count.incrementAndGet());

        // Create the RailwayStationEast
        RailwayStationEastDTO railwayStationEastDTO = railwayStationEastMapper.toDto(railwayStationEast);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRailwayStationEastMockMvc
            .perform(
                put(ENTITY_API_URL_ID, railwayStationEastDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(railwayStationEastDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RailwayStationEast in the database
        List<RailwayStationEast> railwayStationEastList = railwayStationEastRepository.findAll();
        assertThat(railwayStationEastList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchRailwayStationEast() throws Exception {
        int databaseSizeBeforeUpdate = railwayStationEastRepository.findAll().size();
        railwayStationEast.setId(count.incrementAndGet());

        // Create the RailwayStationEast
        RailwayStationEastDTO railwayStationEastDTO = railwayStationEastMapper.toDto(railwayStationEast);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRailwayStationEastMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(railwayStationEastDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RailwayStationEast in the database
        List<RailwayStationEast> railwayStationEastList = railwayStationEastRepository.findAll();
        assertThat(railwayStationEastList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamRailwayStationEast() throws Exception {
        int databaseSizeBeforeUpdate = railwayStationEastRepository.findAll().size();
        railwayStationEast.setId(count.incrementAndGet());

        // Create the RailwayStationEast
        RailwayStationEastDTO railwayStationEastDTO = railwayStationEastMapper.toDto(railwayStationEast);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRailwayStationEastMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(railwayStationEastDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the RailwayStationEast in the database
        List<RailwayStationEast> railwayStationEastList = railwayStationEastRepository.findAll();
        assertThat(railwayStationEastList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateRailwayStationEastWithPatch() throws Exception {
        // Initialize the database
        railwayStationEastRepository.saveAndFlush(railwayStationEast);

        int databaseSizeBeforeUpdate = railwayStationEastRepository.findAll().size();

        // Update the railwayStationEast using partial update
        RailwayStationEast partialUpdatedRailwayStationEast = new RailwayStationEast();
        partialUpdatedRailwayStationEast.setId(railwayStationEast.getId());

        restRailwayStationEastMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRailwayStationEast.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRailwayStationEast))
            )
            .andExpect(status().isOk());

        // Validate the RailwayStationEast in the database
        List<RailwayStationEast> railwayStationEastList = railwayStationEastRepository.findAll();
        assertThat(railwayStationEastList).hasSize(databaseSizeBeforeUpdate);
        RailwayStationEast testRailwayStationEast = railwayStationEastList.get(railwayStationEastList.size() - 1);
        assertThat(testRailwayStationEast.getRailwayStationName()).isEqualTo(DEFAULT_RAILWAY_STATION_NAME);
        assertThat(testRailwayStationEast.getRailwayTypeId()).isEqualTo(DEFAULT_RAILWAY_TYPE_ID);
        assertThat(testRailwayStationEast.getAddressId()).isEqualTo(DEFAULT_ADDRESS_ID);
    }

    @Test
    @Transactional
    void fullUpdateRailwayStationEastWithPatch() throws Exception {
        // Initialize the database
        railwayStationEastRepository.saveAndFlush(railwayStationEast);

        int databaseSizeBeforeUpdate = railwayStationEastRepository.findAll().size();

        // Update the railwayStationEast using partial update
        RailwayStationEast partialUpdatedRailwayStationEast = new RailwayStationEast();
        partialUpdatedRailwayStationEast.setId(railwayStationEast.getId());

        partialUpdatedRailwayStationEast
            .railwayStationName(UPDATED_RAILWAY_STATION_NAME)
            .railwayTypeId(UPDATED_RAILWAY_TYPE_ID)
            .addressId(UPDATED_ADDRESS_ID);

        restRailwayStationEastMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRailwayStationEast.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRailwayStationEast))
            )
            .andExpect(status().isOk());

        // Validate the RailwayStationEast in the database
        List<RailwayStationEast> railwayStationEastList = railwayStationEastRepository.findAll();
        assertThat(railwayStationEastList).hasSize(databaseSizeBeforeUpdate);
        RailwayStationEast testRailwayStationEast = railwayStationEastList.get(railwayStationEastList.size() - 1);
        assertThat(testRailwayStationEast.getRailwayStationName()).isEqualTo(UPDATED_RAILWAY_STATION_NAME);
        assertThat(testRailwayStationEast.getRailwayTypeId()).isEqualTo(UPDATED_RAILWAY_TYPE_ID);
        assertThat(testRailwayStationEast.getAddressId()).isEqualTo(UPDATED_ADDRESS_ID);
    }

    @Test
    @Transactional
    void patchNonExistingRailwayStationEast() throws Exception {
        int databaseSizeBeforeUpdate = railwayStationEastRepository.findAll().size();
        railwayStationEast.setId(count.incrementAndGet());

        // Create the RailwayStationEast
        RailwayStationEastDTO railwayStationEastDTO = railwayStationEastMapper.toDto(railwayStationEast);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRailwayStationEastMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, railwayStationEastDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(railwayStationEastDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RailwayStationEast in the database
        List<RailwayStationEast> railwayStationEastList = railwayStationEastRepository.findAll();
        assertThat(railwayStationEastList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchRailwayStationEast() throws Exception {
        int databaseSizeBeforeUpdate = railwayStationEastRepository.findAll().size();
        railwayStationEast.setId(count.incrementAndGet());

        // Create the RailwayStationEast
        RailwayStationEastDTO railwayStationEastDTO = railwayStationEastMapper.toDto(railwayStationEast);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRailwayStationEastMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(railwayStationEastDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RailwayStationEast in the database
        List<RailwayStationEast> railwayStationEastList = railwayStationEastRepository.findAll();
        assertThat(railwayStationEastList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamRailwayStationEast() throws Exception {
        int databaseSizeBeforeUpdate = railwayStationEastRepository.findAll().size();
        railwayStationEast.setId(count.incrementAndGet());

        // Create the RailwayStationEast
        RailwayStationEastDTO railwayStationEastDTO = railwayStationEastMapper.toDto(railwayStationEast);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRailwayStationEastMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(railwayStationEastDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the RailwayStationEast in the database
        List<RailwayStationEast> railwayStationEastList = railwayStationEastRepository.findAll();
        assertThat(railwayStationEastList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteRailwayStationEast() throws Exception {
        // Initialize the database
        railwayStationEastRepository.saveAndFlush(railwayStationEast);

        int databaseSizeBeforeDelete = railwayStationEastRepository.findAll().size();

        // Delete the railwayStationEast
        restRailwayStationEastMockMvc
            .perform(delete(ENTITY_API_URL_ID, railwayStationEast.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<RailwayStationEast> railwayStationEastList = railwayStationEastRepository.findAll();
        assertThat(railwayStationEastList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
