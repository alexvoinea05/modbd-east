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
import ro.east.domain.JourneyStatusEast;
import ro.east.repository.JourneyStatusEastRepository;
import ro.east.service.dto.JourneyStatusEastDTO;
import ro.east.service.mapper.JourneyStatusEastMapper;

/**
 * Integration tests for the {@link JourneyStatusEastResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class JourneyStatusEastResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/journey-status-easts";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private JourneyStatusEastRepository journeyStatusEastRepository;

    @Autowired
    private JourneyStatusEastMapper journeyStatusEastMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restJourneyStatusEastMockMvc;

    private JourneyStatusEast journeyStatusEast;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static JourneyStatusEast createEntity(EntityManager em) {
        JourneyStatusEast journeyStatusEast = new JourneyStatusEast().code(DEFAULT_CODE).description(DEFAULT_DESCRIPTION);
        return journeyStatusEast;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static JourneyStatusEast createUpdatedEntity(EntityManager em) {
        JourneyStatusEast journeyStatusEast = new JourneyStatusEast().code(UPDATED_CODE).description(UPDATED_DESCRIPTION);
        return journeyStatusEast;
    }

    @BeforeEach
    public void initTest() {
        journeyStatusEast = createEntity(em);
    }

    @Test
    @Transactional
    void createJourneyStatusEast() throws Exception {
        int databaseSizeBeforeCreate = journeyStatusEastRepository.findAll().size();
        // Create the JourneyStatusEast
        JourneyStatusEastDTO journeyStatusEastDTO = journeyStatusEastMapper.toDto(journeyStatusEast);
        restJourneyStatusEastMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(journeyStatusEastDTO))
            )
            .andExpect(status().isCreated());

        // Validate the JourneyStatusEast in the database
        List<JourneyStatusEast> journeyStatusEastList = journeyStatusEastRepository.findAll();
        assertThat(journeyStatusEastList).hasSize(databaseSizeBeforeCreate + 1);
        JourneyStatusEast testJourneyStatusEast = journeyStatusEastList.get(journeyStatusEastList.size() - 1);
        assertThat(testJourneyStatusEast.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testJourneyStatusEast.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    void createJourneyStatusEastWithExistingId() throws Exception {
        // Create the JourneyStatusEast with an existing ID
        journeyStatusEast.setId(1L);
        JourneyStatusEastDTO journeyStatusEastDTO = journeyStatusEastMapper.toDto(journeyStatusEast);

        int databaseSizeBeforeCreate = journeyStatusEastRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restJourneyStatusEastMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(journeyStatusEastDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the JourneyStatusEast in the database
        List<JourneyStatusEast> journeyStatusEastList = journeyStatusEastRepository.findAll();
        assertThat(journeyStatusEastList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllJourneyStatusEasts() throws Exception {
        // Initialize the database
        journeyStatusEastRepository.saveAndFlush(journeyStatusEast);

        // Get all the journeyStatusEastList
        restJourneyStatusEastMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(journeyStatusEast.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }

    @Test
    @Transactional
    void getJourneyStatusEast() throws Exception {
        // Initialize the database
        journeyStatusEastRepository.saveAndFlush(journeyStatusEast);

        // Get the journeyStatusEast
        restJourneyStatusEastMockMvc
            .perform(get(ENTITY_API_URL_ID, journeyStatusEast.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(journeyStatusEast.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }

    @Test
    @Transactional
    void getNonExistingJourneyStatusEast() throws Exception {
        // Get the journeyStatusEast
        restJourneyStatusEastMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingJourneyStatusEast() throws Exception {
        // Initialize the database
        journeyStatusEastRepository.saveAndFlush(journeyStatusEast);

        int databaseSizeBeforeUpdate = journeyStatusEastRepository.findAll().size();

        // Update the journeyStatusEast
        JourneyStatusEast updatedJourneyStatusEast = journeyStatusEastRepository.findById(journeyStatusEast.getId()).get();
        // Disconnect from session so that the updates on updatedJourneyStatusEast are not directly saved in db
        em.detach(updatedJourneyStatusEast);
        updatedJourneyStatusEast.code(UPDATED_CODE).description(UPDATED_DESCRIPTION);
        JourneyStatusEastDTO journeyStatusEastDTO = journeyStatusEastMapper.toDto(updatedJourneyStatusEast);

        restJourneyStatusEastMockMvc
            .perform(
                put(ENTITY_API_URL_ID, journeyStatusEastDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(journeyStatusEastDTO))
            )
            .andExpect(status().isOk());

        // Validate the JourneyStatusEast in the database
        List<JourneyStatusEast> journeyStatusEastList = journeyStatusEastRepository.findAll();
        assertThat(journeyStatusEastList).hasSize(databaseSizeBeforeUpdate);
        JourneyStatusEast testJourneyStatusEast = journeyStatusEastList.get(journeyStatusEastList.size() - 1);
        assertThat(testJourneyStatusEast.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testJourneyStatusEast.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void putNonExistingJourneyStatusEast() throws Exception {
        int databaseSizeBeforeUpdate = journeyStatusEastRepository.findAll().size();
        journeyStatusEast.setId(count.incrementAndGet());

        // Create the JourneyStatusEast
        JourneyStatusEastDTO journeyStatusEastDTO = journeyStatusEastMapper.toDto(journeyStatusEast);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restJourneyStatusEastMockMvc
            .perform(
                put(ENTITY_API_URL_ID, journeyStatusEastDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(journeyStatusEastDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the JourneyStatusEast in the database
        List<JourneyStatusEast> journeyStatusEastList = journeyStatusEastRepository.findAll();
        assertThat(journeyStatusEastList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchJourneyStatusEast() throws Exception {
        int databaseSizeBeforeUpdate = journeyStatusEastRepository.findAll().size();
        journeyStatusEast.setId(count.incrementAndGet());

        // Create the JourneyStatusEast
        JourneyStatusEastDTO journeyStatusEastDTO = journeyStatusEastMapper.toDto(journeyStatusEast);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restJourneyStatusEastMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(journeyStatusEastDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the JourneyStatusEast in the database
        List<JourneyStatusEast> journeyStatusEastList = journeyStatusEastRepository.findAll();
        assertThat(journeyStatusEastList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamJourneyStatusEast() throws Exception {
        int databaseSizeBeforeUpdate = journeyStatusEastRepository.findAll().size();
        journeyStatusEast.setId(count.incrementAndGet());

        // Create the JourneyStatusEast
        JourneyStatusEastDTO journeyStatusEastDTO = journeyStatusEastMapper.toDto(journeyStatusEast);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restJourneyStatusEastMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(journeyStatusEastDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the JourneyStatusEast in the database
        List<JourneyStatusEast> journeyStatusEastList = journeyStatusEastRepository.findAll();
        assertThat(journeyStatusEastList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateJourneyStatusEastWithPatch() throws Exception {
        // Initialize the database
        journeyStatusEastRepository.saveAndFlush(journeyStatusEast);

        int databaseSizeBeforeUpdate = journeyStatusEastRepository.findAll().size();

        // Update the journeyStatusEast using partial update
        JourneyStatusEast partialUpdatedJourneyStatusEast = new JourneyStatusEast();
        partialUpdatedJourneyStatusEast.setId(journeyStatusEast.getId());

        restJourneyStatusEastMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedJourneyStatusEast.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedJourneyStatusEast))
            )
            .andExpect(status().isOk());

        // Validate the JourneyStatusEast in the database
        List<JourneyStatusEast> journeyStatusEastList = journeyStatusEastRepository.findAll();
        assertThat(journeyStatusEastList).hasSize(databaseSizeBeforeUpdate);
        JourneyStatusEast testJourneyStatusEast = journeyStatusEastList.get(journeyStatusEastList.size() - 1);
        assertThat(testJourneyStatusEast.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testJourneyStatusEast.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    void fullUpdateJourneyStatusEastWithPatch() throws Exception {
        // Initialize the database
        journeyStatusEastRepository.saveAndFlush(journeyStatusEast);

        int databaseSizeBeforeUpdate = journeyStatusEastRepository.findAll().size();

        // Update the journeyStatusEast using partial update
        JourneyStatusEast partialUpdatedJourneyStatusEast = new JourneyStatusEast();
        partialUpdatedJourneyStatusEast.setId(journeyStatusEast.getId());

        partialUpdatedJourneyStatusEast.code(UPDATED_CODE).description(UPDATED_DESCRIPTION);

        restJourneyStatusEastMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedJourneyStatusEast.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedJourneyStatusEast))
            )
            .andExpect(status().isOk());

        // Validate the JourneyStatusEast in the database
        List<JourneyStatusEast> journeyStatusEastList = journeyStatusEastRepository.findAll();
        assertThat(journeyStatusEastList).hasSize(databaseSizeBeforeUpdate);
        JourneyStatusEast testJourneyStatusEast = journeyStatusEastList.get(journeyStatusEastList.size() - 1);
        assertThat(testJourneyStatusEast.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testJourneyStatusEast.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void patchNonExistingJourneyStatusEast() throws Exception {
        int databaseSizeBeforeUpdate = journeyStatusEastRepository.findAll().size();
        journeyStatusEast.setId(count.incrementAndGet());

        // Create the JourneyStatusEast
        JourneyStatusEastDTO journeyStatusEastDTO = journeyStatusEastMapper.toDto(journeyStatusEast);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restJourneyStatusEastMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, journeyStatusEastDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(journeyStatusEastDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the JourneyStatusEast in the database
        List<JourneyStatusEast> journeyStatusEastList = journeyStatusEastRepository.findAll();
        assertThat(journeyStatusEastList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchJourneyStatusEast() throws Exception {
        int databaseSizeBeforeUpdate = journeyStatusEastRepository.findAll().size();
        journeyStatusEast.setId(count.incrementAndGet());

        // Create the JourneyStatusEast
        JourneyStatusEastDTO journeyStatusEastDTO = journeyStatusEastMapper.toDto(journeyStatusEast);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restJourneyStatusEastMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(journeyStatusEastDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the JourneyStatusEast in the database
        List<JourneyStatusEast> journeyStatusEastList = journeyStatusEastRepository.findAll();
        assertThat(journeyStatusEastList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamJourneyStatusEast() throws Exception {
        int databaseSizeBeforeUpdate = journeyStatusEastRepository.findAll().size();
        journeyStatusEast.setId(count.incrementAndGet());

        // Create the JourneyStatusEast
        JourneyStatusEastDTO journeyStatusEastDTO = journeyStatusEastMapper.toDto(journeyStatusEast);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restJourneyStatusEastMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(journeyStatusEastDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the JourneyStatusEast in the database
        List<JourneyStatusEast> journeyStatusEastList = journeyStatusEastRepository.findAll();
        assertThat(journeyStatusEastList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteJourneyStatusEast() throws Exception {
        // Initialize the database
        journeyStatusEastRepository.saveAndFlush(journeyStatusEast);

        int databaseSizeBeforeDelete = journeyStatusEastRepository.findAll().size();

        // Delete the journeyStatusEast
        restJourneyStatusEastMockMvc
            .perform(delete(ENTITY_API_URL_ID, journeyStatusEast.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<JourneyStatusEast> journeyStatusEastList = journeyStatusEastRepository.findAll();
        assertThat(journeyStatusEastList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
