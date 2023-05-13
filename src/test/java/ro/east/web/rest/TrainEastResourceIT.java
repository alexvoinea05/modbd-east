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
import ro.east.domain.TrainEast;
import ro.east.repository.TrainEastRepository;
import ro.east.service.dto.TrainEastDTO;
import ro.east.service.mapper.TrainEastMapper;

/**
 * Integration tests for the {@link TrainEastResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TrainEastResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final Long DEFAULT_NUMBER_OF_SEATS = 1L;
    private static final Long UPDATED_NUMBER_OF_SEATS = 2L;

    private static final Long DEFAULT_FUEL_TYPE_ID = 1L;
    private static final Long UPDATED_FUEL_TYPE_ID = 2L;

    private static final Long DEFAULT_TRAIN_TYPE_ID = 1L;
    private static final Long UPDATED_TRAIN_TYPE_ID = 2L;

    private static final String ENTITY_API_URL = "/api/train-easts";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TrainEastRepository trainEastRepository;

    @Autowired
    private TrainEastMapper trainEastMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTrainEastMockMvc;

    private TrainEast trainEast;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TrainEast createEntity(EntityManager em) {
        TrainEast trainEast = new TrainEast()
            .code(DEFAULT_CODE)
            .numberOfSeats(DEFAULT_NUMBER_OF_SEATS)
            .fuelTypeId(DEFAULT_FUEL_TYPE_ID)
            .trainTypeId(DEFAULT_TRAIN_TYPE_ID);
        return trainEast;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TrainEast createUpdatedEntity(EntityManager em) {
        TrainEast trainEast = new TrainEast()
            .code(UPDATED_CODE)
            .numberOfSeats(UPDATED_NUMBER_OF_SEATS)
            .fuelTypeId(UPDATED_FUEL_TYPE_ID)
            .trainTypeId(UPDATED_TRAIN_TYPE_ID);
        return trainEast;
    }

    @BeforeEach
    public void initTest() {
        trainEast = createEntity(em);
    }

    @Test
    @Transactional
    void createTrainEast() throws Exception {
        int databaseSizeBeforeCreate = trainEastRepository.findAll().size();
        // Create the TrainEast
        TrainEastDTO trainEastDTO = trainEastMapper.toDto(trainEast);
        restTrainEastMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(trainEastDTO)))
            .andExpect(status().isCreated());

        // Validate the TrainEast in the database
        List<TrainEast> trainEastList = trainEastRepository.findAll();
        assertThat(trainEastList).hasSize(databaseSizeBeforeCreate + 1);
        TrainEast testTrainEast = trainEastList.get(trainEastList.size() - 1);
        assertThat(testTrainEast.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testTrainEast.getNumberOfSeats()).isEqualTo(DEFAULT_NUMBER_OF_SEATS);
        assertThat(testTrainEast.getFuelTypeId()).isEqualTo(DEFAULT_FUEL_TYPE_ID);
        assertThat(testTrainEast.getTrainTypeId()).isEqualTo(DEFAULT_TRAIN_TYPE_ID);
    }

    @Test
    @Transactional
    void createTrainEastWithExistingId() throws Exception {
        // Create the TrainEast with an existing ID
        trainEast.setId(1L);
        TrainEastDTO trainEastDTO = trainEastMapper.toDto(trainEast);

        int databaseSizeBeforeCreate = trainEastRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTrainEastMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(trainEastDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TrainEast in the database
        List<TrainEast> trainEastList = trainEastRepository.findAll();
        assertThat(trainEastList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllTrainEasts() throws Exception {
        // Initialize the database
        trainEastRepository.saveAndFlush(trainEast);

        // Get all the trainEastList
        restTrainEastMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(trainEast.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].numberOfSeats").value(hasItem(DEFAULT_NUMBER_OF_SEATS.intValue())))
            .andExpect(jsonPath("$.[*].fuelTypeId").value(hasItem(DEFAULT_FUEL_TYPE_ID.intValue())))
            .andExpect(jsonPath("$.[*].trainTypeId").value(hasItem(DEFAULT_TRAIN_TYPE_ID.intValue())));
    }

    @Test
    @Transactional
    void getTrainEast() throws Exception {
        // Initialize the database
        trainEastRepository.saveAndFlush(trainEast);

        // Get the trainEast
        restTrainEastMockMvc
            .perform(get(ENTITY_API_URL_ID, trainEast.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(trainEast.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.numberOfSeats").value(DEFAULT_NUMBER_OF_SEATS.intValue()))
            .andExpect(jsonPath("$.fuelTypeId").value(DEFAULT_FUEL_TYPE_ID.intValue()))
            .andExpect(jsonPath("$.trainTypeId").value(DEFAULT_TRAIN_TYPE_ID.intValue()));
    }

    @Test
    @Transactional
    void getNonExistingTrainEast() throws Exception {
        // Get the trainEast
        restTrainEastMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingTrainEast() throws Exception {
        // Initialize the database
        trainEastRepository.saveAndFlush(trainEast);

        int databaseSizeBeforeUpdate = trainEastRepository.findAll().size();

        // Update the trainEast
        TrainEast updatedTrainEast = trainEastRepository.findById(trainEast.getId()).get();
        // Disconnect from session so that the updates on updatedTrainEast are not directly saved in db
        em.detach(updatedTrainEast);
        updatedTrainEast
            .code(UPDATED_CODE)
            .numberOfSeats(UPDATED_NUMBER_OF_SEATS)
            .fuelTypeId(UPDATED_FUEL_TYPE_ID)
            .trainTypeId(UPDATED_TRAIN_TYPE_ID);
        TrainEastDTO trainEastDTO = trainEastMapper.toDto(updatedTrainEast);

        restTrainEastMockMvc
            .perform(
                put(ENTITY_API_URL_ID, trainEastDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(trainEastDTO))
            )
            .andExpect(status().isOk());

        // Validate the TrainEast in the database
        List<TrainEast> trainEastList = trainEastRepository.findAll();
        assertThat(trainEastList).hasSize(databaseSizeBeforeUpdate);
        TrainEast testTrainEast = trainEastList.get(trainEastList.size() - 1);
        assertThat(testTrainEast.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testTrainEast.getNumberOfSeats()).isEqualTo(UPDATED_NUMBER_OF_SEATS);
        assertThat(testTrainEast.getFuelTypeId()).isEqualTo(UPDATED_FUEL_TYPE_ID);
        assertThat(testTrainEast.getTrainTypeId()).isEqualTo(UPDATED_TRAIN_TYPE_ID);
    }

    @Test
    @Transactional
    void putNonExistingTrainEast() throws Exception {
        int databaseSizeBeforeUpdate = trainEastRepository.findAll().size();
        trainEast.setId(count.incrementAndGet());

        // Create the TrainEast
        TrainEastDTO trainEastDTO = trainEastMapper.toDto(trainEast);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTrainEastMockMvc
            .perform(
                put(ENTITY_API_URL_ID, trainEastDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(trainEastDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TrainEast in the database
        List<TrainEast> trainEastList = trainEastRepository.findAll();
        assertThat(trainEastList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTrainEast() throws Exception {
        int databaseSizeBeforeUpdate = trainEastRepository.findAll().size();
        trainEast.setId(count.incrementAndGet());

        // Create the TrainEast
        TrainEastDTO trainEastDTO = trainEastMapper.toDto(trainEast);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTrainEastMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(trainEastDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TrainEast in the database
        List<TrainEast> trainEastList = trainEastRepository.findAll();
        assertThat(trainEastList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTrainEast() throws Exception {
        int databaseSizeBeforeUpdate = trainEastRepository.findAll().size();
        trainEast.setId(count.incrementAndGet());

        // Create the TrainEast
        TrainEastDTO trainEastDTO = trainEastMapper.toDto(trainEast);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTrainEastMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(trainEastDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the TrainEast in the database
        List<TrainEast> trainEastList = trainEastRepository.findAll();
        assertThat(trainEastList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTrainEastWithPatch() throws Exception {
        // Initialize the database
        trainEastRepository.saveAndFlush(trainEast);

        int databaseSizeBeforeUpdate = trainEastRepository.findAll().size();

        // Update the trainEast using partial update
        TrainEast partialUpdatedTrainEast = new TrainEast();
        partialUpdatedTrainEast.setId(trainEast.getId());

        restTrainEastMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTrainEast.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTrainEast))
            )
            .andExpect(status().isOk());

        // Validate the TrainEast in the database
        List<TrainEast> trainEastList = trainEastRepository.findAll();
        assertThat(trainEastList).hasSize(databaseSizeBeforeUpdate);
        TrainEast testTrainEast = trainEastList.get(trainEastList.size() - 1);
        assertThat(testTrainEast.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testTrainEast.getNumberOfSeats()).isEqualTo(DEFAULT_NUMBER_OF_SEATS);
        assertThat(testTrainEast.getFuelTypeId()).isEqualTo(DEFAULT_FUEL_TYPE_ID);
        assertThat(testTrainEast.getTrainTypeId()).isEqualTo(DEFAULT_TRAIN_TYPE_ID);
    }

    @Test
    @Transactional
    void fullUpdateTrainEastWithPatch() throws Exception {
        // Initialize the database
        trainEastRepository.saveAndFlush(trainEast);

        int databaseSizeBeforeUpdate = trainEastRepository.findAll().size();

        // Update the trainEast using partial update
        TrainEast partialUpdatedTrainEast = new TrainEast();
        partialUpdatedTrainEast.setId(trainEast.getId());

        partialUpdatedTrainEast
            .code(UPDATED_CODE)
            .numberOfSeats(UPDATED_NUMBER_OF_SEATS)
            .fuelTypeId(UPDATED_FUEL_TYPE_ID)
            .trainTypeId(UPDATED_TRAIN_TYPE_ID);

        restTrainEastMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTrainEast.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTrainEast))
            )
            .andExpect(status().isOk());

        // Validate the TrainEast in the database
        List<TrainEast> trainEastList = trainEastRepository.findAll();
        assertThat(trainEastList).hasSize(databaseSizeBeforeUpdate);
        TrainEast testTrainEast = trainEastList.get(trainEastList.size() - 1);
        assertThat(testTrainEast.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testTrainEast.getNumberOfSeats()).isEqualTo(UPDATED_NUMBER_OF_SEATS);
        assertThat(testTrainEast.getFuelTypeId()).isEqualTo(UPDATED_FUEL_TYPE_ID);
        assertThat(testTrainEast.getTrainTypeId()).isEqualTo(UPDATED_TRAIN_TYPE_ID);
    }

    @Test
    @Transactional
    void patchNonExistingTrainEast() throws Exception {
        int databaseSizeBeforeUpdate = trainEastRepository.findAll().size();
        trainEast.setId(count.incrementAndGet());

        // Create the TrainEast
        TrainEastDTO trainEastDTO = trainEastMapper.toDto(trainEast);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTrainEastMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, trainEastDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(trainEastDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TrainEast in the database
        List<TrainEast> trainEastList = trainEastRepository.findAll();
        assertThat(trainEastList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTrainEast() throws Exception {
        int databaseSizeBeforeUpdate = trainEastRepository.findAll().size();
        trainEast.setId(count.incrementAndGet());

        // Create the TrainEast
        TrainEastDTO trainEastDTO = trainEastMapper.toDto(trainEast);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTrainEastMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(trainEastDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TrainEast in the database
        List<TrainEast> trainEastList = trainEastRepository.findAll();
        assertThat(trainEastList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTrainEast() throws Exception {
        int databaseSizeBeforeUpdate = trainEastRepository.findAll().size();
        trainEast.setId(count.incrementAndGet());

        // Create the TrainEast
        TrainEastDTO trainEastDTO = trainEastMapper.toDto(trainEast);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTrainEastMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(trainEastDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TrainEast in the database
        List<TrainEast> trainEastList = trainEastRepository.findAll();
        assertThat(trainEastList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTrainEast() throws Exception {
        // Initialize the database
        trainEastRepository.saveAndFlush(trainEast);

        int databaseSizeBeforeDelete = trainEastRepository.findAll().size();

        // Delete the trainEast
        restTrainEastMockMvc
            .perform(delete(ENTITY_API_URL_ID, trainEast.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TrainEast> trainEastList = trainEastRepository.findAll();
        assertThat(trainEastList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
