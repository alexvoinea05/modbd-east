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
import ro.east.domain.LicenseEast;
import ro.east.repository.LicenseEastRepository;
import ro.east.service.dto.LicenseEastDTO;
import ro.east.service.mapper.LicenseEastMapper;

/**
 * Integration tests for the {@link LicenseEastResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class LicenseEastResourceIT {

    private static final Long DEFAULT_LICENSE_NUMBER = 1L;
    private static final Long UPDATED_LICENSE_NUMBER = 2L;

    private static final String DEFAULT_LICENSE_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_LICENSE_DESCRIPTION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/license-easts";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private LicenseEastRepository licenseEastRepository;

    @Autowired
    private LicenseEastMapper licenseEastMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLicenseEastMockMvc;

    private LicenseEast licenseEast;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LicenseEast createEntity(EntityManager em) {
        LicenseEast licenseEast = new LicenseEast().licenseNumber(DEFAULT_LICENSE_NUMBER).licenseDescription(DEFAULT_LICENSE_DESCRIPTION);
        return licenseEast;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LicenseEast createUpdatedEntity(EntityManager em) {
        LicenseEast licenseEast = new LicenseEast().licenseNumber(UPDATED_LICENSE_NUMBER).licenseDescription(UPDATED_LICENSE_DESCRIPTION);
        return licenseEast;
    }

    @BeforeEach
    public void initTest() {
        licenseEast = createEntity(em);
    }

    @Test
    @Transactional
    void createLicenseEast() throws Exception {
        int databaseSizeBeforeCreate = licenseEastRepository.findAll().size();
        // Create the LicenseEast
        LicenseEastDTO licenseEastDTO = licenseEastMapper.toDto(licenseEast);
        restLicenseEastMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(licenseEastDTO))
            )
            .andExpect(status().isCreated());

        // Validate the LicenseEast in the database
        List<LicenseEast> licenseEastList = licenseEastRepository.findAll();
        assertThat(licenseEastList).hasSize(databaseSizeBeforeCreate + 1);
        LicenseEast testLicenseEast = licenseEastList.get(licenseEastList.size() - 1);
        assertThat(testLicenseEast.getLicenseNumber()).isEqualTo(DEFAULT_LICENSE_NUMBER);
        assertThat(testLicenseEast.getLicenseDescription()).isEqualTo(DEFAULT_LICENSE_DESCRIPTION);
    }

    @Test
    @Transactional
    void createLicenseEastWithExistingId() throws Exception {
        // Create the LicenseEast with an existing ID
        licenseEast.setId(1L);
        LicenseEastDTO licenseEastDTO = licenseEastMapper.toDto(licenseEast);

        int databaseSizeBeforeCreate = licenseEastRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restLicenseEastMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(licenseEastDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the LicenseEast in the database
        List<LicenseEast> licenseEastList = licenseEastRepository.findAll();
        assertThat(licenseEastList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllLicenseEasts() throws Exception {
        // Initialize the database
        licenseEastRepository.saveAndFlush(licenseEast);

        // Get all the licenseEastList
        restLicenseEastMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(licenseEast.getId().intValue())))
            .andExpect(jsonPath("$.[*].licenseNumber").value(hasItem(DEFAULT_LICENSE_NUMBER.intValue())))
            .andExpect(jsonPath("$.[*].licenseDescription").value(hasItem(DEFAULT_LICENSE_DESCRIPTION)));
    }

    @Test
    @Transactional
    void getLicenseEast() throws Exception {
        // Initialize the database
        licenseEastRepository.saveAndFlush(licenseEast);

        // Get the licenseEast
        restLicenseEastMockMvc
            .perform(get(ENTITY_API_URL_ID, licenseEast.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(licenseEast.getId().intValue()))
            .andExpect(jsonPath("$.licenseNumber").value(DEFAULT_LICENSE_NUMBER.intValue()))
            .andExpect(jsonPath("$.licenseDescription").value(DEFAULT_LICENSE_DESCRIPTION));
    }

    @Test
    @Transactional
    void getNonExistingLicenseEast() throws Exception {
        // Get the licenseEast
        restLicenseEastMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingLicenseEast() throws Exception {
        // Initialize the database
        licenseEastRepository.saveAndFlush(licenseEast);

        int databaseSizeBeforeUpdate = licenseEastRepository.findAll().size();

        // Update the licenseEast
        LicenseEast updatedLicenseEast = licenseEastRepository.findById(licenseEast.getId()).get();
        // Disconnect from session so that the updates on updatedLicenseEast are not directly saved in db
        em.detach(updatedLicenseEast);
        updatedLicenseEast.licenseNumber(UPDATED_LICENSE_NUMBER).licenseDescription(UPDATED_LICENSE_DESCRIPTION);
        LicenseEastDTO licenseEastDTO = licenseEastMapper.toDto(updatedLicenseEast);

        restLicenseEastMockMvc
            .perform(
                put(ENTITY_API_URL_ID, licenseEastDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(licenseEastDTO))
            )
            .andExpect(status().isOk());

        // Validate the LicenseEast in the database
        List<LicenseEast> licenseEastList = licenseEastRepository.findAll();
        assertThat(licenseEastList).hasSize(databaseSizeBeforeUpdate);
        LicenseEast testLicenseEast = licenseEastList.get(licenseEastList.size() - 1);
        assertThat(testLicenseEast.getLicenseNumber()).isEqualTo(UPDATED_LICENSE_NUMBER);
        assertThat(testLicenseEast.getLicenseDescription()).isEqualTo(UPDATED_LICENSE_DESCRIPTION);
    }

    @Test
    @Transactional
    void putNonExistingLicenseEast() throws Exception {
        int databaseSizeBeforeUpdate = licenseEastRepository.findAll().size();
        licenseEast.setId(count.incrementAndGet());

        // Create the LicenseEast
        LicenseEastDTO licenseEastDTO = licenseEastMapper.toDto(licenseEast);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLicenseEastMockMvc
            .perform(
                put(ENTITY_API_URL_ID, licenseEastDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(licenseEastDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the LicenseEast in the database
        List<LicenseEast> licenseEastList = licenseEastRepository.findAll();
        assertThat(licenseEastList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchLicenseEast() throws Exception {
        int databaseSizeBeforeUpdate = licenseEastRepository.findAll().size();
        licenseEast.setId(count.incrementAndGet());

        // Create the LicenseEast
        LicenseEastDTO licenseEastDTO = licenseEastMapper.toDto(licenseEast);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLicenseEastMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(licenseEastDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the LicenseEast in the database
        List<LicenseEast> licenseEastList = licenseEastRepository.findAll();
        assertThat(licenseEastList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamLicenseEast() throws Exception {
        int databaseSizeBeforeUpdate = licenseEastRepository.findAll().size();
        licenseEast.setId(count.incrementAndGet());

        // Create the LicenseEast
        LicenseEastDTO licenseEastDTO = licenseEastMapper.toDto(licenseEast);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLicenseEastMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(licenseEastDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the LicenseEast in the database
        List<LicenseEast> licenseEastList = licenseEastRepository.findAll();
        assertThat(licenseEastList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateLicenseEastWithPatch() throws Exception {
        // Initialize the database
        licenseEastRepository.saveAndFlush(licenseEast);

        int databaseSizeBeforeUpdate = licenseEastRepository.findAll().size();

        // Update the licenseEast using partial update
        LicenseEast partialUpdatedLicenseEast = new LicenseEast();
        partialUpdatedLicenseEast.setId(licenseEast.getId());

        restLicenseEastMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLicenseEast.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLicenseEast))
            )
            .andExpect(status().isOk());

        // Validate the LicenseEast in the database
        List<LicenseEast> licenseEastList = licenseEastRepository.findAll();
        assertThat(licenseEastList).hasSize(databaseSizeBeforeUpdate);
        LicenseEast testLicenseEast = licenseEastList.get(licenseEastList.size() - 1);
        assertThat(testLicenseEast.getLicenseNumber()).isEqualTo(DEFAULT_LICENSE_NUMBER);
        assertThat(testLicenseEast.getLicenseDescription()).isEqualTo(DEFAULT_LICENSE_DESCRIPTION);
    }

    @Test
    @Transactional
    void fullUpdateLicenseEastWithPatch() throws Exception {
        // Initialize the database
        licenseEastRepository.saveAndFlush(licenseEast);

        int databaseSizeBeforeUpdate = licenseEastRepository.findAll().size();

        // Update the licenseEast using partial update
        LicenseEast partialUpdatedLicenseEast = new LicenseEast();
        partialUpdatedLicenseEast.setId(licenseEast.getId());

        partialUpdatedLicenseEast.licenseNumber(UPDATED_LICENSE_NUMBER).licenseDescription(UPDATED_LICENSE_DESCRIPTION);

        restLicenseEastMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLicenseEast.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLicenseEast))
            )
            .andExpect(status().isOk());

        // Validate the LicenseEast in the database
        List<LicenseEast> licenseEastList = licenseEastRepository.findAll();
        assertThat(licenseEastList).hasSize(databaseSizeBeforeUpdate);
        LicenseEast testLicenseEast = licenseEastList.get(licenseEastList.size() - 1);
        assertThat(testLicenseEast.getLicenseNumber()).isEqualTo(UPDATED_LICENSE_NUMBER);
        assertThat(testLicenseEast.getLicenseDescription()).isEqualTo(UPDATED_LICENSE_DESCRIPTION);
    }

    @Test
    @Transactional
    void patchNonExistingLicenseEast() throws Exception {
        int databaseSizeBeforeUpdate = licenseEastRepository.findAll().size();
        licenseEast.setId(count.incrementAndGet());

        // Create the LicenseEast
        LicenseEastDTO licenseEastDTO = licenseEastMapper.toDto(licenseEast);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLicenseEastMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, licenseEastDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(licenseEastDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the LicenseEast in the database
        List<LicenseEast> licenseEastList = licenseEastRepository.findAll();
        assertThat(licenseEastList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchLicenseEast() throws Exception {
        int databaseSizeBeforeUpdate = licenseEastRepository.findAll().size();
        licenseEast.setId(count.incrementAndGet());

        // Create the LicenseEast
        LicenseEastDTO licenseEastDTO = licenseEastMapper.toDto(licenseEast);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLicenseEastMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(licenseEastDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the LicenseEast in the database
        List<LicenseEast> licenseEastList = licenseEastRepository.findAll();
        assertThat(licenseEastList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamLicenseEast() throws Exception {
        int databaseSizeBeforeUpdate = licenseEastRepository.findAll().size();
        licenseEast.setId(count.incrementAndGet());

        // Create the LicenseEast
        LicenseEastDTO licenseEastDTO = licenseEastMapper.toDto(licenseEast);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLicenseEastMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(licenseEastDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the LicenseEast in the database
        List<LicenseEast> licenseEastList = licenseEastRepository.findAll();
        assertThat(licenseEastList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteLicenseEast() throws Exception {
        // Initialize the database
        licenseEastRepository.saveAndFlush(licenseEast);

        int databaseSizeBeforeDelete = licenseEastRepository.findAll().size();

        // Delete the licenseEast
        restLicenseEastMockMvc
            .perform(delete(ENTITY_API_URL_ID, licenseEast.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<LicenseEast> licenseEastList = licenseEastRepository.findAll();
        assertThat(licenseEastList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
