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
import ro.east.domain.DistrictEast;
import ro.east.repository.DistrictEastRepository;
import ro.east.service.dto.DistrictEastDTO;
import ro.east.service.mapper.DistrictEastMapper;

/**
 * Integration tests for the {@link DistrictEastResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class DistrictEastResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_REGION = "AAAAAAAAAA";
    private static final String UPDATED_REGION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/district-easts";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private DistrictEastRepository districtEastRepository;

    @Autowired
    private DistrictEastMapper districtEastMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDistrictEastMockMvc;

    private DistrictEast districtEast;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DistrictEast createEntity(EntityManager em) {
        DistrictEast districtEast = new DistrictEast().name(DEFAULT_NAME).region(DEFAULT_REGION);
        return districtEast;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DistrictEast createUpdatedEntity(EntityManager em) {
        DistrictEast districtEast = new DistrictEast().name(UPDATED_NAME).region(UPDATED_REGION);
        return districtEast;
    }

    @BeforeEach
    public void initTest() {
        districtEast = createEntity(em);
    }

    @Test
    @Transactional
    void createDistrictEast() throws Exception {
        int databaseSizeBeforeCreate = districtEastRepository.findAll().size();
        // Create the DistrictEast
        DistrictEastDTO districtEastDTO = districtEastMapper.toDto(districtEast);
        restDistrictEastMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(districtEastDTO))
            )
            .andExpect(status().isCreated());

        // Validate the DistrictEast in the database
        List<DistrictEast> districtEastList = districtEastRepository.findAll();
        assertThat(districtEastList).hasSize(databaseSizeBeforeCreate + 1);
        DistrictEast testDistrictEast = districtEastList.get(districtEastList.size() - 1);
        assertThat(testDistrictEast.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testDistrictEast.getRegion()).isEqualTo(DEFAULT_REGION);
    }

    @Test
    @Transactional
    void createDistrictEastWithExistingId() throws Exception {
        // Create the DistrictEast with an existing ID
        districtEast.setId(1L);
        DistrictEastDTO districtEastDTO = districtEastMapper.toDto(districtEast);

        int databaseSizeBeforeCreate = districtEastRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDistrictEastMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(districtEastDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DistrictEast in the database
        List<DistrictEast> districtEastList = districtEastRepository.findAll();
        assertThat(districtEastList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllDistrictEasts() throws Exception {
        // Initialize the database
        districtEastRepository.saveAndFlush(districtEast);

        // Get all the districtEastList
        restDistrictEastMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(districtEast.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].region").value(hasItem(DEFAULT_REGION)));
    }

    @Test
    @Transactional
    void getDistrictEast() throws Exception {
        // Initialize the database
        districtEastRepository.saveAndFlush(districtEast);

        // Get the districtEast
        restDistrictEastMockMvc
            .perform(get(ENTITY_API_URL_ID, districtEast.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(districtEast.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.region").value(DEFAULT_REGION));
    }

    @Test
    @Transactional
    void getNonExistingDistrictEast() throws Exception {
        // Get the districtEast
        restDistrictEastMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingDistrictEast() throws Exception {
        // Initialize the database
        districtEastRepository.saveAndFlush(districtEast);

        int databaseSizeBeforeUpdate = districtEastRepository.findAll().size();

        // Update the districtEast
        DistrictEast updatedDistrictEast = districtEastRepository.findById(districtEast.getId()).get();
        // Disconnect from session so that the updates on updatedDistrictEast are not directly saved in db
        em.detach(updatedDistrictEast);
        updatedDistrictEast.name(UPDATED_NAME).region(UPDATED_REGION);
        DistrictEastDTO districtEastDTO = districtEastMapper.toDto(updatedDistrictEast);

        restDistrictEastMockMvc
            .perform(
                put(ENTITY_API_URL_ID, districtEastDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(districtEastDTO))
            )
            .andExpect(status().isOk());

        // Validate the DistrictEast in the database
        List<DistrictEast> districtEastList = districtEastRepository.findAll();
        assertThat(districtEastList).hasSize(databaseSizeBeforeUpdate);
        DistrictEast testDistrictEast = districtEastList.get(districtEastList.size() - 1);
        assertThat(testDistrictEast.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDistrictEast.getRegion()).isEqualTo(UPDATED_REGION);
    }

    @Test
    @Transactional
    void putNonExistingDistrictEast() throws Exception {
        int databaseSizeBeforeUpdate = districtEastRepository.findAll().size();
        districtEast.setId(count.incrementAndGet());

        // Create the DistrictEast
        DistrictEastDTO districtEastDTO = districtEastMapper.toDto(districtEast);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDistrictEastMockMvc
            .perform(
                put(ENTITY_API_URL_ID, districtEastDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(districtEastDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DistrictEast in the database
        List<DistrictEast> districtEastList = districtEastRepository.findAll();
        assertThat(districtEastList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDistrictEast() throws Exception {
        int databaseSizeBeforeUpdate = districtEastRepository.findAll().size();
        districtEast.setId(count.incrementAndGet());

        // Create the DistrictEast
        DistrictEastDTO districtEastDTO = districtEastMapper.toDto(districtEast);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDistrictEastMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(districtEastDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DistrictEast in the database
        List<DistrictEast> districtEastList = districtEastRepository.findAll();
        assertThat(districtEastList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDistrictEast() throws Exception {
        int databaseSizeBeforeUpdate = districtEastRepository.findAll().size();
        districtEast.setId(count.incrementAndGet());

        // Create the DistrictEast
        DistrictEastDTO districtEastDTO = districtEastMapper.toDto(districtEast);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDistrictEastMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(districtEastDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the DistrictEast in the database
        List<DistrictEast> districtEastList = districtEastRepository.findAll();
        assertThat(districtEastList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDistrictEastWithPatch() throws Exception {
        // Initialize the database
        districtEastRepository.saveAndFlush(districtEast);

        int databaseSizeBeforeUpdate = districtEastRepository.findAll().size();

        // Update the districtEast using partial update
        DistrictEast partialUpdatedDistrictEast = new DistrictEast();
        partialUpdatedDistrictEast.setId(districtEast.getId());

        restDistrictEastMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDistrictEast.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDistrictEast))
            )
            .andExpect(status().isOk());

        // Validate the DistrictEast in the database
        List<DistrictEast> districtEastList = districtEastRepository.findAll();
        assertThat(districtEastList).hasSize(databaseSizeBeforeUpdate);
        DistrictEast testDistrictEast = districtEastList.get(districtEastList.size() - 1);
        assertThat(testDistrictEast.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testDistrictEast.getRegion()).isEqualTo(DEFAULT_REGION);
    }

    @Test
    @Transactional
    void fullUpdateDistrictEastWithPatch() throws Exception {
        // Initialize the database
        districtEastRepository.saveAndFlush(districtEast);

        int databaseSizeBeforeUpdate = districtEastRepository.findAll().size();

        // Update the districtEast using partial update
        DistrictEast partialUpdatedDistrictEast = new DistrictEast();
        partialUpdatedDistrictEast.setId(districtEast.getId());

        partialUpdatedDistrictEast.name(UPDATED_NAME).region(UPDATED_REGION);

        restDistrictEastMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDistrictEast.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDistrictEast))
            )
            .andExpect(status().isOk());

        // Validate the DistrictEast in the database
        List<DistrictEast> districtEastList = districtEastRepository.findAll();
        assertThat(districtEastList).hasSize(databaseSizeBeforeUpdate);
        DistrictEast testDistrictEast = districtEastList.get(districtEastList.size() - 1);
        assertThat(testDistrictEast.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDistrictEast.getRegion()).isEqualTo(UPDATED_REGION);
    }

    @Test
    @Transactional
    void patchNonExistingDistrictEast() throws Exception {
        int databaseSizeBeforeUpdate = districtEastRepository.findAll().size();
        districtEast.setId(count.incrementAndGet());

        // Create the DistrictEast
        DistrictEastDTO districtEastDTO = districtEastMapper.toDto(districtEast);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDistrictEastMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, districtEastDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(districtEastDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DistrictEast in the database
        List<DistrictEast> districtEastList = districtEastRepository.findAll();
        assertThat(districtEastList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDistrictEast() throws Exception {
        int databaseSizeBeforeUpdate = districtEastRepository.findAll().size();
        districtEast.setId(count.incrementAndGet());

        // Create the DistrictEast
        DistrictEastDTO districtEastDTO = districtEastMapper.toDto(districtEast);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDistrictEastMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(districtEastDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DistrictEast in the database
        List<DistrictEast> districtEastList = districtEastRepository.findAll();
        assertThat(districtEastList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDistrictEast() throws Exception {
        int databaseSizeBeforeUpdate = districtEastRepository.findAll().size();
        districtEast.setId(count.incrementAndGet());

        // Create the DistrictEast
        DistrictEastDTO districtEastDTO = districtEastMapper.toDto(districtEast);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDistrictEastMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(districtEastDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the DistrictEast in the database
        List<DistrictEast> districtEastList = districtEastRepository.findAll();
        assertThat(districtEastList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDistrictEast() throws Exception {
        // Initialize the database
        districtEastRepository.saveAndFlush(districtEast);

        int databaseSizeBeforeDelete = districtEastRepository.findAll().size();

        // Delete the districtEast
        restDistrictEastMockMvc
            .perform(delete(ENTITY_API_URL_ID, districtEast.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DistrictEast> districtEastList = districtEastRepository.findAll();
        assertThat(districtEastList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
