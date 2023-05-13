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
import ro.east.domain.CompanyLicenseEast;
import ro.east.repository.CompanyLicenseEastRepository;
import ro.east.service.dto.CompanyLicenseEastDTO;
import ro.east.service.mapper.CompanyLicenseEastMapper;

/**
 * Integration tests for the {@link CompanyLicenseEastResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CompanyLicenseEastResourceIT {

    private static final String ENTITY_API_URL = "/api/company-license-easts";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CompanyLicenseEastRepository companyLicenseEastRepository;

    @Autowired
    private CompanyLicenseEastMapper companyLicenseEastMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCompanyLicenseEastMockMvc;

    private CompanyLicenseEast companyLicenseEast;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CompanyLicenseEast createEntity(EntityManager em) {
        CompanyLicenseEast companyLicenseEast = new CompanyLicenseEast();
        return companyLicenseEast;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CompanyLicenseEast createUpdatedEntity(EntityManager em) {
        CompanyLicenseEast companyLicenseEast = new CompanyLicenseEast();
        return companyLicenseEast;
    }

    @BeforeEach
    public void initTest() {
        companyLicenseEast = createEntity(em);
    }

    @Test
    @Transactional
    void createCompanyLicenseEast() throws Exception {
        int databaseSizeBeforeCreate = companyLicenseEastRepository.findAll().size();
        // Create the CompanyLicenseEast
        CompanyLicenseEastDTO companyLicenseEastDTO = companyLicenseEastMapper.toDto(companyLicenseEast);
        restCompanyLicenseEastMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(companyLicenseEastDTO))
            )
            .andExpect(status().isCreated());

        // Validate the CompanyLicenseEast in the database
        List<CompanyLicenseEast> companyLicenseEastList = companyLicenseEastRepository.findAll();
        assertThat(companyLicenseEastList).hasSize(databaseSizeBeforeCreate + 1);
        CompanyLicenseEast testCompanyLicenseEast = companyLicenseEastList.get(companyLicenseEastList.size() - 1);
    }

    @Test
    @Transactional
    void createCompanyLicenseEastWithExistingId() throws Exception {
        // Create the CompanyLicenseEast with an existing ID
        companyLicenseEast.setId(1L);
        CompanyLicenseEastDTO companyLicenseEastDTO = companyLicenseEastMapper.toDto(companyLicenseEast);

        int databaseSizeBeforeCreate = companyLicenseEastRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCompanyLicenseEastMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(companyLicenseEastDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CompanyLicenseEast in the database
        List<CompanyLicenseEast> companyLicenseEastList = companyLicenseEastRepository.findAll();
        assertThat(companyLicenseEastList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllCompanyLicenseEasts() throws Exception {
        // Initialize the database
        companyLicenseEastRepository.saveAndFlush(companyLicenseEast);

        // Get all the companyLicenseEastList
        restCompanyLicenseEastMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(companyLicenseEast.getId().intValue())));
    }

    @Test
    @Transactional
    void getCompanyLicenseEast() throws Exception {
        // Initialize the database
        companyLicenseEastRepository.saveAndFlush(companyLicenseEast);

        // Get the companyLicenseEast
        restCompanyLicenseEastMockMvc
            .perform(get(ENTITY_API_URL_ID, companyLicenseEast.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(companyLicenseEast.getId().intValue()));
    }

    @Test
    @Transactional
    void getNonExistingCompanyLicenseEast() throws Exception {
        // Get the companyLicenseEast
        restCompanyLicenseEastMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCompanyLicenseEast() throws Exception {
        // Initialize the database
        companyLicenseEastRepository.saveAndFlush(companyLicenseEast);

        int databaseSizeBeforeUpdate = companyLicenseEastRepository.findAll().size();

        // Update the companyLicenseEast
        CompanyLicenseEast updatedCompanyLicenseEast = companyLicenseEastRepository.findById(companyLicenseEast.getId()).get();
        // Disconnect from session so that the updates on updatedCompanyLicenseEast are not directly saved in db
        em.detach(updatedCompanyLicenseEast);
        CompanyLicenseEastDTO companyLicenseEastDTO = companyLicenseEastMapper.toDto(updatedCompanyLicenseEast);

        restCompanyLicenseEastMockMvc
            .perform(
                put(ENTITY_API_URL_ID, companyLicenseEastDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(companyLicenseEastDTO))
            )
            .andExpect(status().isOk());

        // Validate the CompanyLicenseEast in the database
        List<CompanyLicenseEast> companyLicenseEastList = companyLicenseEastRepository.findAll();
        assertThat(companyLicenseEastList).hasSize(databaseSizeBeforeUpdate);
        CompanyLicenseEast testCompanyLicenseEast = companyLicenseEastList.get(companyLicenseEastList.size() - 1);
    }

    @Test
    @Transactional
    void putNonExistingCompanyLicenseEast() throws Exception {
        int databaseSizeBeforeUpdate = companyLicenseEastRepository.findAll().size();
        companyLicenseEast.setId(count.incrementAndGet());

        // Create the CompanyLicenseEast
        CompanyLicenseEastDTO companyLicenseEastDTO = companyLicenseEastMapper.toDto(companyLicenseEast);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCompanyLicenseEastMockMvc
            .perform(
                put(ENTITY_API_URL_ID, companyLicenseEastDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(companyLicenseEastDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CompanyLicenseEast in the database
        List<CompanyLicenseEast> companyLicenseEastList = companyLicenseEastRepository.findAll();
        assertThat(companyLicenseEastList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCompanyLicenseEast() throws Exception {
        int databaseSizeBeforeUpdate = companyLicenseEastRepository.findAll().size();
        companyLicenseEast.setId(count.incrementAndGet());

        // Create the CompanyLicenseEast
        CompanyLicenseEastDTO companyLicenseEastDTO = companyLicenseEastMapper.toDto(companyLicenseEast);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCompanyLicenseEastMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(companyLicenseEastDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CompanyLicenseEast in the database
        List<CompanyLicenseEast> companyLicenseEastList = companyLicenseEastRepository.findAll();
        assertThat(companyLicenseEastList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCompanyLicenseEast() throws Exception {
        int databaseSizeBeforeUpdate = companyLicenseEastRepository.findAll().size();
        companyLicenseEast.setId(count.incrementAndGet());

        // Create the CompanyLicenseEast
        CompanyLicenseEastDTO companyLicenseEastDTO = companyLicenseEastMapper.toDto(companyLicenseEast);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCompanyLicenseEastMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(companyLicenseEastDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CompanyLicenseEast in the database
        List<CompanyLicenseEast> companyLicenseEastList = companyLicenseEastRepository.findAll();
        assertThat(companyLicenseEastList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCompanyLicenseEastWithPatch() throws Exception {
        // Initialize the database
        companyLicenseEastRepository.saveAndFlush(companyLicenseEast);

        int databaseSizeBeforeUpdate = companyLicenseEastRepository.findAll().size();

        // Update the companyLicenseEast using partial update
        CompanyLicenseEast partialUpdatedCompanyLicenseEast = new CompanyLicenseEast();
        partialUpdatedCompanyLicenseEast.setId(companyLicenseEast.getId());

        restCompanyLicenseEastMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCompanyLicenseEast.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCompanyLicenseEast))
            )
            .andExpect(status().isOk());

        // Validate the CompanyLicenseEast in the database
        List<CompanyLicenseEast> companyLicenseEastList = companyLicenseEastRepository.findAll();
        assertThat(companyLicenseEastList).hasSize(databaseSizeBeforeUpdate);
        CompanyLicenseEast testCompanyLicenseEast = companyLicenseEastList.get(companyLicenseEastList.size() - 1);
    }

    @Test
    @Transactional
    void fullUpdateCompanyLicenseEastWithPatch() throws Exception {
        // Initialize the database
        companyLicenseEastRepository.saveAndFlush(companyLicenseEast);

        int databaseSizeBeforeUpdate = companyLicenseEastRepository.findAll().size();

        // Update the companyLicenseEast using partial update
        CompanyLicenseEast partialUpdatedCompanyLicenseEast = new CompanyLicenseEast();
        partialUpdatedCompanyLicenseEast.setId(companyLicenseEast.getId());

        restCompanyLicenseEastMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCompanyLicenseEast.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCompanyLicenseEast))
            )
            .andExpect(status().isOk());

        // Validate the CompanyLicenseEast in the database
        List<CompanyLicenseEast> companyLicenseEastList = companyLicenseEastRepository.findAll();
        assertThat(companyLicenseEastList).hasSize(databaseSizeBeforeUpdate);
        CompanyLicenseEast testCompanyLicenseEast = companyLicenseEastList.get(companyLicenseEastList.size() - 1);
    }

    @Test
    @Transactional
    void patchNonExistingCompanyLicenseEast() throws Exception {
        int databaseSizeBeforeUpdate = companyLicenseEastRepository.findAll().size();
        companyLicenseEast.setId(count.incrementAndGet());

        // Create the CompanyLicenseEast
        CompanyLicenseEastDTO companyLicenseEastDTO = companyLicenseEastMapper.toDto(companyLicenseEast);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCompanyLicenseEastMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, companyLicenseEastDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(companyLicenseEastDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CompanyLicenseEast in the database
        List<CompanyLicenseEast> companyLicenseEastList = companyLicenseEastRepository.findAll();
        assertThat(companyLicenseEastList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCompanyLicenseEast() throws Exception {
        int databaseSizeBeforeUpdate = companyLicenseEastRepository.findAll().size();
        companyLicenseEast.setId(count.incrementAndGet());

        // Create the CompanyLicenseEast
        CompanyLicenseEastDTO companyLicenseEastDTO = companyLicenseEastMapper.toDto(companyLicenseEast);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCompanyLicenseEastMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(companyLicenseEastDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CompanyLicenseEast in the database
        List<CompanyLicenseEast> companyLicenseEastList = companyLicenseEastRepository.findAll();
        assertThat(companyLicenseEastList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCompanyLicenseEast() throws Exception {
        int databaseSizeBeforeUpdate = companyLicenseEastRepository.findAll().size();
        companyLicenseEast.setId(count.incrementAndGet());

        // Create the CompanyLicenseEast
        CompanyLicenseEastDTO companyLicenseEastDTO = companyLicenseEastMapper.toDto(companyLicenseEast);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCompanyLicenseEastMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(companyLicenseEastDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CompanyLicenseEast in the database
        List<CompanyLicenseEast> companyLicenseEastList = companyLicenseEastRepository.findAll();
        assertThat(companyLicenseEastList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCompanyLicenseEast() throws Exception {
        // Initialize the database
        companyLicenseEastRepository.saveAndFlush(companyLicenseEast);

        int databaseSizeBeforeDelete = companyLicenseEastRepository.findAll().size();

        // Delete the companyLicenseEast
        restCompanyLicenseEastMockMvc
            .perform(delete(ENTITY_API_URL_ID, companyLicenseEast.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CompanyLicenseEast> companyLicenseEastList = companyLicenseEastRepository.findAll();
        assertThat(companyLicenseEastList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
