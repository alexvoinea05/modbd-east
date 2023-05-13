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
import ro.east.domain.CompanyEast;
import ro.east.repository.CompanyEastRepository;
import ro.east.service.dto.CompanyEastDTO;
import ro.east.service.mapper.CompanyEastMapper;

/**
 * Integration tests for the {@link CompanyEastResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CompanyEastResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_IDENTIFICATION_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_IDENTIFICATION_NUMBER = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/company-easts";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CompanyEastRepository companyEastRepository;

    @Autowired
    private CompanyEastMapper companyEastMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCompanyEastMockMvc;

    private CompanyEast companyEast;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CompanyEast createEntity(EntityManager em) {
        CompanyEast companyEast = new CompanyEast().name(DEFAULT_NAME).identificationNumber(DEFAULT_IDENTIFICATION_NUMBER);
        return companyEast;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CompanyEast createUpdatedEntity(EntityManager em) {
        CompanyEast companyEast = new CompanyEast().name(UPDATED_NAME).identificationNumber(UPDATED_IDENTIFICATION_NUMBER);
        return companyEast;
    }

    @BeforeEach
    public void initTest() {
        companyEast = createEntity(em);
    }

    @Test
    @Transactional
    void createCompanyEast() throws Exception {
        int databaseSizeBeforeCreate = companyEastRepository.findAll().size();
        // Create the CompanyEast
        CompanyEastDTO companyEastDTO = companyEastMapper.toDto(companyEast);
        restCompanyEastMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(companyEastDTO))
            )
            .andExpect(status().isCreated());

        // Validate the CompanyEast in the database
        List<CompanyEast> companyEastList = companyEastRepository.findAll();
        assertThat(companyEastList).hasSize(databaseSizeBeforeCreate + 1);
        CompanyEast testCompanyEast = companyEastList.get(companyEastList.size() - 1);
        assertThat(testCompanyEast.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCompanyEast.getIdentificationNumber()).isEqualTo(DEFAULT_IDENTIFICATION_NUMBER);
    }

    @Test
    @Transactional
    void createCompanyEastWithExistingId() throws Exception {
        // Create the CompanyEast with an existing ID
        companyEast.setId(1L);
        CompanyEastDTO companyEastDTO = companyEastMapper.toDto(companyEast);

        int databaseSizeBeforeCreate = companyEastRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCompanyEastMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(companyEastDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CompanyEast in the database
        List<CompanyEast> companyEastList = companyEastRepository.findAll();
        assertThat(companyEastList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllCompanyEasts() throws Exception {
        // Initialize the database
        companyEastRepository.saveAndFlush(companyEast);

        // Get all the companyEastList
        restCompanyEastMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(companyEast.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].identificationNumber").value(hasItem(DEFAULT_IDENTIFICATION_NUMBER)));
    }

    @Test
    @Transactional
    void getCompanyEast() throws Exception {
        // Initialize the database
        companyEastRepository.saveAndFlush(companyEast);

        // Get the companyEast
        restCompanyEastMockMvc
            .perform(get(ENTITY_API_URL_ID, companyEast.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(companyEast.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.identificationNumber").value(DEFAULT_IDENTIFICATION_NUMBER));
    }

    @Test
    @Transactional
    void getNonExistingCompanyEast() throws Exception {
        // Get the companyEast
        restCompanyEastMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCompanyEast() throws Exception {
        // Initialize the database
        companyEastRepository.saveAndFlush(companyEast);

        int databaseSizeBeforeUpdate = companyEastRepository.findAll().size();

        // Update the companyEast
        CompanyEast updatedCompanyEast = companyEastRepository.findById(companyEast.getId()).get();
        // Disconnect from session so that the updates on updatedCompanyEast are not directly saved in db
        em.detach(updatedCompanyEast);
        updatedCompanyEast.name(UPDATED_NAME).identificationNumber(UPDATED_IDENTIFICATION_NUMBER);
        CompanyEastDTO companyEastDTO = companyEastMapper.toDto(updatedCompanyEast);

        restCompanyEastMockMvc
            .perform(
                put(ENTITY_API_URL_ID, companyEastDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(companyEastDTO))
            )
            .andExpect(status().isOk());

        // Validate the CompanyEast in the database
        List<CompanyEast> companyEastList = companyEastRepository.findAll();
        assertThat(companyEastList).hasSize(databaseSizeBeforeUpdate);
        CompanyEast testCompanyEast = companyEastList.get(companyEastList.size() - 1);
        assertThat(testCompanyEast.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCompanyEast.getIdentificationNumber()).isEqualTo(UPDATED_IDENTIFICATION_NUMBER);
    }

    @Test
    @Transactional
    void putNonExistingCompanyEast() throws Exception {
        int databaseSizeBeforeUpdate = companyEastRepository.findAll().size();
        companyEast.setId(count.incrementAndGet());

        // Create the CompanyEast
        CompanyEastDTO companyEastDTO = companyEastMapper.toDto(companyEast);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCompanyEastMockMvc
            .perform(
                put(ENTITY_API_URL_ID, companyEastDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(companyEastDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CompanyEast in the database
        List<CompanyEast> companyEastList = companyEastRepository.findAll();
        assertThat(companyEastList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCompanyEast() throws Exception {
        int databaseSizeBeforeUpdate = companyEastRepository.findAll().size();
        companyEast.setId(count.incrementAndGet());

        // Create the CompanyEast
        CompanyEastDTO companyEastDTO = companyEastMapper.toDto(companyEast);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCompanyEastMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(companyEastDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CompanyEast in the database
        List<CompanyEast> companyEastList = companyEastRepository.findAll();
        assertThat(companyEastList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCompanyEast() throws Exception {
        int databaseSizeBeforeUpdate = companyEastRepository.findAll().size();
        companyEast.setId(count.incrementAndGet());

        // Create the CompanyEast
        CompanyEastDTO companyEastDTO = companyEastMapper.toDto(companyEast);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCompanyEastMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(companyEastDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the CompanyEast in the database
        List<CompanyEast> companyEastList = companyEastRepository.findAll();
        assertThat(companyEastList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCompanyEastWithPatch() throws Exception {
        // Initialize the database
        companyEastRepository.saveAndFlush(companyEast);

        int databaseSizeBeforeUpdate = companyEastRepository.findAll().size();

        // Update the companyEast using partial update
        CompanyEast partialUpdatedCompanyEast = new CompanyEast();
        partialUpdatedCompanyEast.setId(companyEast.getId());

        restCompanyEastMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCompanyEast.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCompanyEast))
            )
            .andExpect(status().isOk());

        // Validate the CompanyEast in the database
        List<CompanyEast> companyEastList = companyEastRepository.findAll();
        assertThat(companyEastList).hasSize(databaseSizeBeforeUpdate);
        CompanyEast testCompanyEast = companyEastList.get(companyEastList.size() - 1);
        assertThat(testCompanyEast.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCompanyEast.getIdentificationNumber()).isEqualTo(DEFAULT_IDENTIFICATION_NUMBER);
    }

    @Test
    @Transactional
    void fullUpdateCompanyEastWithPatch() throws Exception {
        // Initialize the database
        companyEastRepository.saveAndFlush(companyEast);

        int databaseSizeBeforeUpdate = companyEastRepository.findAll().size();

        // Update the companyEast using partial update
        CompanyEast partialUpdatedCompanyEast = new CompanyEast();
        partialUpdatedCompanyEast.setId(companyEast.getId());

        partialUpdatedCompanyEast.name(UPDATED_NAME).identificationNumber(UPDATED_IDENTIFICATION_NUMBER);

        restCompanyEastMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCompanyEast.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCompanyEast))
            )
            .andExpect(status().isOk());

        // Validate the CompanyEast in the database
        List<CompanyEast> companyEastList = companyEastRepository.findAll();
        assertThat(companyEastList).hasSize(databaseSizeBeforeUpdate);
        CompanyEast testCompanyEast = companyEastList.get(companyEastList.size() - 1);
        assertThat(testCompanyEast.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCompanyEast.getIdentificationNumber()).isEqualTo(UPDATED_IDENTIFICATION_NUMBER);
    }

    @Test
    @Transactional
    void patchNonExistingCompanyEast() throws Exception {
        int databaseSizeBeforeUpdate = companyEastRepository.findAll().size();
        companyEast.setId(count.incrementAndGet());

        // Create the CompanyEast
        CompanyEastDTO companyEastDTO = companyEastMapper.toDto(companyEast);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCompanyEastMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, companyEastDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(companyEastDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CompanyEast in the database
        List<CompanyEast> companyEastList = companyEastRepository.findAll();
        assertThat(companyEastList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCompanyEast() throws Exception {
        int databaseSizeBeforeUpdate = companyEastRepository.findAll().size();
        companyEast.setId(count.incrementAndGet());

        // Create the CompanyEast
        CompanyEastDTO companyEastDTO = companyEastMapper.toDto(companyEast);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCompanyEastMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(companyEastDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CompanyEast in the database
        List<CompanyEast> companyEastList = companyEastRepository.findAll();
        assertThat(companyEastList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCompanyEast() throws Exception {
        int databaseSizeBeforeUpdate = companyEastRepository.findAll().size();
        companyEast.setId(count.incrementAndGet());

        // Create the CompanyEast
        CompanyEastDTO companyEastDTO = companyEastMapper.toDto(companyEast);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCompanyEastMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(companyEastDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CompanyEast in the database
        List<CompanyEast> companyEastList = companyEastRepository.findAll();
        assertThat(companyEastList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCompanyEast() throws Exception {
        // Initialize the database
        companyEastRepository.saveAndFlush(companyEast);

        int databaseSizeBeforeDelete = companyEastRepository.findAll().size();

        // Delete the companyEast
        restCompanyEastMockMvc
            .perform(delete(ENTITY_API_URL_ID, companyEast.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CompanyEast> companyEastList = companyEastRepository.findAll();
        assertThat(companyEastList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
