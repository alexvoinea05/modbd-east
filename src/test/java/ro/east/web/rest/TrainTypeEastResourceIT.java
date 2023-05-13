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
import ro.east.domain.TrainTypeEast;
import ro.east.repository.TrainTypeEastRepository;
import ro.east.service.dto.TrainTypeEastDTO;
import ro.east.service.mapper.TrainTypeEastMapper;

/**
 * Integration tests for the {@link TrainTypeEastResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TrainTypeEastResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/train-type-easts";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TrainTypeEastRepository trainTypeEastRepository;

    @Autowired
    private TrainTypeEastMapper trainTypeEastMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTrainTypeEastMockMvc;

    private TrainTypeEast trainTypeEast;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TrainTypeEast createEntity(EntityManager em) {
        TrainTypeEast trainTypeEast = new TrainTypeEast().code(DEFAULT_CODE).description(DEFAULT_DESCRIPTION);
        return trainTypeEast;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TrainTypeEast createUpdatedEntity(EntityManager em) {
        TrainTypeEast trainTypeEast = new TrainTypeEast().code(UPDATED_CODE).description(UPDATED_DESCRIPTION);
        return trainTypeEast;
    }

    @BeforeEach
    public void initTest() {
        trainTypeEast = createEntity(em);
    }

    @Test
    @Transactional
    void createTrainTypeEast() throws Exception {
        int databaseSizeBeforeCreate = trainTypeEastRepository.findAll().size();
        // Create the TrainTypeEast
        TrainTypeEastDTO trainTypeEastDTO = trainTypeEastMapper.toDto(trainTypeEast);
        restTrainTypeEastMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(trainTypeEastDTO))
            )
            .andExpect(status().isCreated());

        // Validate the TrainTypeEast in the database
        List<TrainTypeEast> trainTypeEastList = trainTypeEastRepository.findAll();
        assertThat(trainTypeEastList).hasSize(databaseSizeBeforeCreate + 1);
        TrainTypeEast testTrainTypeEast = trainTypeEastList.get(trainTypeEastList.size() - 1);
        assertThat(testTrainTypeEast.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testTrainTypeEast.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    void createTrainTypeEastWithExistingId() throws Exception {
        // Create the TrainTypeEast with an existing ID
        trainTypeEast.setId(1L);
        TrainTypeEastDTO trainTypeEastDTO = trainTypeEastMapper.toDto(trainTypeEast);

        int databaseSizeBeforeCreate = trainTypeEastRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTrainTypeEastMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(trainTypeEastDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TrainTypeEast in the database
        List<TrainTypeEast> trainTypeEastList = trainTypeEastRepository.findAll();
        assertThat(trainTypeEastList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllTrainTypeEasts() throws Exception {
        // Initialize the database
        trainTypeEastRepository.saveAndFlush(trainTypeEast);

        // Get all the trainTypeEastList
        restTrainTypeEastMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(trainTypeEast.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }

    @Test
    @Transactional
    void getTrainTypeEast() throws Exception {
        // Initialize the database
        trainTypeEastRepository.saveAndFlush(trainTypeEast);

        // Get the trainTypeEast
        restTrainTypeEastMockMvc
            .perform(get(ENTITY_API_URL_ID, trainTypeEast.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(trainTypeEast.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }

    @Test
    @Transactional
    void getNonExistingTrainTypeEast() throws Exception {
        // Get the trainTypeEast
        restTrainTypeEastMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingTrainTypeEast() throws Exception {
        // Initialize the database
        trainTypeEastRepository.saveAndFlush(trainTypeEast);

        int databaseSizeBeforeUpdate = trainTypeEastRepository.findAll().size();

        // Update the trainTypeEast
        TrainTypeEast updatedTrainTypeEast = trainTypeEastRepository.findById(trainTypeEast.getId()).get();
        // Disconnect from session so that the updates on updatedTrainTypeEast are not directly saved in db
        em.detach(updatedTrainTypeEast);
        updatedTrainTypeEast.code(UPDATED_CODE).description(UPDATED_DESCRIPTION);
        TrainTypeEastDTO trainTypeEastDTO = trainTypeEastMapper.toDto(updatedTrainTypeEast);

        restTrainTypeEastMockMvc
            .perform(
                put(ENTITY_API_URL_ID, trainTypeEastDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(trainTypeEastDTO))
            )
            .andExpect(status().isOk());

        // Validate the TrainTypeEast in the database
        List<TrainTypeEast> trainTypeEastList = trainTypeEastRepository.findAll();
        assertThat(trainTypeEastList).hasSize(databaseSizeBeforeUpdate);
        TrainTypeEast testTrainTypeEast = trainTypeEastList.get(trainTypeEastList.size() - 1);
        assertThat(testTrainTypeEast.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testTrainTypeEast.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void putNonExistingTrainTypeEast() throws Exception {
        int databaseSizeBeforeUpdate = trainTypeEastRepository.findAll().size();
        trainTypeEast.setId(count.incrementAndGet());

        // Create the TrainTypeEast
        TrainTypeEastDTO trainTypeEastDTO = trainTypeEastMapper.toDto(trainTypeEast);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTrainTypeEastMockMvc
            .perform(
                put(ENTITY_API_URL_ID, trainTypeEastDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(trainTypeEastDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TrainTypeEast in the database
        List<TrainTypeEast> trainTypeEastList = trainTypeEastRepository.findAll();
        assertThat(trainTypeEastList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTrainTypeEast() throws Exception {
        int databaseSizeBeforeUpdate = trainTypeEastRepository.findAll().size();
        trainTypeEast.setId(count.incrementAndGet());

        // Create the TrainTypeEast
        TrainTypeEastDTO trainTypeEastDTO = trainTypeEastMapper.toDto(trainTypeEast);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTrainTypeEastMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(trainTypeEastDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TrainTypeEast in the database
        List<TrainTypeEast> trainTypeEastList = trainTypeEastRepository.findAll();
        assertThat(trainTypeEastList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTrainTypeEast() throws Exception {
        int databaseSizeBeforeUpdate = trainTypeEastRepository.findAll().size();
        trainTypeEast.setId(count.incrementAndGet());

        // Create the TrainTypeEast
        TrainTypeEastDTO trainTypeEastDTO = trainTypeEastMapper.toDto(trainTypeEast);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTrainTypeEastMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(trainTypeEastDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TrainTypeEast in the database
        List<TrainTypeEast> trainTypeEastList = trainTypeEastRepository.findAll();
        assertThat(trainTypeEastList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTrainTypeEastWithPatch() throws Exception {
        // Initialize the database
        trainTypeEastRepository.saveAndFlush(trainTypeEast);

        int databaseSizeBeforeUpdate = trainTypeEastRepository.findAll().size();

        // Update the trainTypeEast using partial update
        TrainTypeEast partialUpdatedTrainTypeEast = new TrainTypeEast();
        partialUpdatedTrainTypeEast.setId(trainTypeEast.getId());

        partialUpdatedTrainTypeEast.code(UPDATED_CODE).description(UPDATED_DESCRIPTION);

        restTrainTypeEastMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTrainTypeEast.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTrainTypeEast))
            )
            .andExpect(status().isOk());

        // Validate the TrainTypeEast in the database
        List<TrainTypeEast> trainTypeEastList = trainTypeEastRepository.findAll();
        assertThat(trainTypeEastList).hasSize(databaseSizeBeforeUpdate);
        TrainTypeEast testTrainTypeEast = trainTypeEastList.get(trainTypeEastList.size() - 1);
        assertThat(testTrainTypeEast.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testTrainTypeEast.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void fullUpdateTrainTypeEastWithPatch() throws Exception {
        // Initialize the database
        trainTypeEastRepository.saveAndFlush(trainTypeEast);

        int databaseSizeBeforeUpdate = trainTypeEastRepository.findAll().size();

        // Update the trainTypeEast using partial update
        TrainTypeEast partialUpdatedTrainTypeEast = new TrainTypeEast();
        partialUpdatedTrainTypeEast.setId(trainTypeEast.getId());

        partialUpdatedTrainTypeEast.code(UPDATED_CODE).description(UPDATED_DESCRIPTION);

        restTrainTypeEastMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTrainTypeEast.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTrainTypeEast))
            )
            .andExpect(status().isOk());

        // Validate the TrainTypeEast in the database
        List<TrainTypeEast> trainTypeEastList = trainTypeEastRepository.findAll();
        assertThat(trainTypeEastList).hasSize(databaseSizeBeforeUpdate);
        TrainTypeEast testTrainTypeEast = trainTypeEastList.get(trainTypeEastList.size() - 1);
        assertThat(testTrainTypeEast.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testTrainTypeEast.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void patchNonExistingTrainTypeEast() throws Exception {
        int databaseSizeBeforeUpdate = trainTypeEastRepository.findAll().size();
        trainTypeEast.setId(count.incrementAndGet());

        // Create the TrainTypeEast
        TrainTypeEastDTO trainTypeEastDTO = trainTypeEastMapper.toDto(trainTypeEast);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTrainTypeEastMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, trainTypeEastDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(trainTypeEastDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TrainTypeEast in the database
        List<TrainTypeEast> trainTypeEastList = trainTypeEastRepository.findAll();
        assertThat(trainTypeEastList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTrainTypeEast() throws Exception {
        int databaseSizeBeforeUpdate = trainTypeEastRepository.findAll().size();
        trainTypeEast.setId(count.incrementAndGet());

        // Create the TrainTypeEast
        TrainTypeEastDTO trainTypeEastDTO = trainTypeEastMapper.toDto(trainTypeEast);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTrainTypeEastMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(trainTypeEastDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TrainTypeEast in the database
        List<TrainTypeEast> trainTypeEastList = trainTypeEastRepository.findAll();
        assertThat(trainTypeEastList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTrainTypeEast() throws Exception {
        int databaseSizeBeforeUpdate = trainTypeEastRepository.findAll().size();
        trainTypeEast.setId(count.incrementAndGet());

        // Create the TrainTypeEast
        TrainTypeEastDTO trainTypeEastDTO = trainTypeEastMapper.toDto(trainTypeEast);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTrainTypeEastMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(trainTypeEastDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TrainTypeEast in the database
        List<TrainTypeEast> trainTypeEastList = trainTypeEastRepository.findAll();
        assertThat(trainTypeEastList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTrainTypeEast() throws Exception {
        // Initialize the database
        trainTypeEastRepository.saveAndFlush(trainTypeEast);

        int databaseSizeBeforeDelete = trainTypeEastRepository.findAll().size();

        // Delete the trainTypeEast
        restTrainTypeEastMockMvc
            .perform(delete(ENTITY_API_URL_ID, trainTypeEast.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TrainTypeEast> trainTypeEastList = trainTypeEastRepository.findAll();
        assertThat(trainTypeEastList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
