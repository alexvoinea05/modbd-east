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
import ro.east.domain.CityEast;
import ro.east.repository.CityEastRepository;
import ro.east.service.dto.CityEastDTO;
import ro.east.service.mapper.CityEastMapper;

/**
 * Integration tests for the {@link CityEastResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CityEastResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Long DEFAULT_DISTRICT_ID = 1L;
    private static final Long UPDATED_DISTRICT_ID = 2L;

    private static final String ENTITY_API_URL = "/api/city-easts";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CityEastRepository cityEastRepository;

    @Autowired
    private CityEastMapper cityEastMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCityEastMockMvc;

    private CityEast cityEast;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CityEast createEntity(EntityManager em) {
        CityEast cityEast = new CityEast().name(DEFAULT_NAME).districtId(DEFAULT_DISTRICT_ID);
        return cityEast;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CityEast createUpdatedEntity(EntityManager em) {
        CityEast cityEast = new CityEast().name(UPDATED_NAME).districtId(UPDATED_DISTRICT_ID);
        return cityEast;
    }

    @BeforeEach
    public void initTest() {
        cityEast = createEntity(em);
    }

    @Test
    @Transactional
    void createCityEast() throws Exception {
        int databaseSizeBeforeCreate = cityEastRepository.findAll().size();
        // Create the CityEast
        CityEastDTO cityEastDTO = cityEastMapper.toDto(cityEast);
        restCityEastMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cityEastDTO)))
            .andExpect(status().isCreated());

        // Validate the CityEast in the database
        List<CityEast> cityEastList = cityEastRepository.findAll();
        assertThat(cityEastList).hasSize(databaseSizeBeforeCreate + 1);
        CityEast testCityEast = cityEastList.get(cityEastList.size() - 1);
        assertThat(testCityEast.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCityEast.getDistrictId()).isEqualTo(DEFAULT_DISTRICT_ID);
    }

    @Test
    @Transactional
    void createCityEastWithExistingId() throws Exception {
        // Create the CityEast with an existing ID
        cityEast.setId(1L);
        CityEastDTO cityEastDTO = cityEastMapper.toDto(cityEast);

        int databaseSizeBeforeCreate = cityEastRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCityEastMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cityEastDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CityEast in the database
        List<CityEast> cityEastList = cityEastRepository.findAll();
        assertThat(cityEastList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllCityEasts() throws Exception {
        // Initialize the database
        cityEastRepository.saveAndFlush(cityEast);

        // Get all the cityEastList
        restCityEastMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cityEast.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].districtId").value(hasItem(DEFAULT_DISTRICT_ID.intValue())));
    }

    @Test
    @Transactional
    void getCityEast() throws Exception {
        // Initialize the database
        cityEastRepository.saveAndFlush(cityEast);

        // Get the cityEast
        restCityEastMockMvc
            .perform(get(ENTITY_API_URL_ID, cityEast.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(cityEast.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.districtId").value(DEFAULT_DISTRICT_ID.intValue()));
    }

    @Test
    @Transactional
    void getNonExistingCityEast() throws Exception {
        // Get the cityEast
        restCityEastMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCityEast() throws Exception {
        // Initialize the database
        cityEastRepository.saveAndFlush(cityEast);

        int databaseSizeBeforeUpdate = cityEastRepository.findAll().size();

        // Update the cityEast
        CityEast updatedCityEast = cityEastRepository.findById(cityEast.getId()).get();
        // Disconnect from session so that the updates on updatedCityEast are not directly saved in db
        em.detach(updatedCityEast);
        updatedCityEast.name(UPDATED_NAME).districtId(UPDATED_DISTRICT_ID);
        CityEastDTO cityEastDTO = cityEastMapper.toDto(updatedCityEast);

        restCityEastMockMvc
            .perform(
                put(ENTITY_API_URL_ID, cityEastDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(cityEastDTO))
            )
            .andExpect(status().isOk());

        // Validate the CityEast in the database
        List<CityEast> cityEastList = cityEastRepository.findAll();
        assertThat(cityEastList).hasSize(databaseSizeBeforeUpdate);
        CityEast testCityEast = cityEastList.get(cityEastList.size() - 1);
        assertThat(testCityEast.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCityEast.getDistrictId()).isEqualTo(UPDATED_DISTRICT_ID);
    }

    @Test
    @Transactional
    void putNonExistingCityEast() throws Exception {
        int databaseSizeBeforeUpdate = cityEastRepository.findAll().size();
        cityEast.setId(count.incrementAndGet());

        // Create the CityEast
        CityEastDTO cityEastDTO = cityEastMapper.toDto(cityEast);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCityEastMockMvc
            .perform(
                put(ENTITY_API_URL_ID, cityEastDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(cityEastDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CityEast in the database
        List<CityEast> cityEastList = cityEastRepository.findAll();
        assertThat(cityEastList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCityEast() throws Exception {
        int databaseSizeBeforeUpdate = cityEastRepository.findAll().size();
        cityEast.setId(count.incrementAndGet());

        // Create the CityEast
        CityEastDTO cityEastDTO = cityEastMapper.toDto(cityEast);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCityEastMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(cityEastDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CityEast in the database
        List<CityEast> cityEastList = cityEastRepository.findAll();
        assertThat(cityEastList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCityEast() throws Exception {
        int databaseSizeBeforeUpdate = cityEastRepository.findAll().size();
        cityEast.setId(count.incrementAndGet());

        // Create the CityEast
        CityEastDTO cityEastDTO = cityEastMapper.toDto(cityEast);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCityEastMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cityEastDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the CityEast in the database
        List<CityEast> cityEastList = cityEastRepository.findAll();
        assertThat(cityEastList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCityEastWithPatch() throws Exception {
        // Initialize the database
        cityEastRepository.saveAndFlush(cityEast);

        int databaseSizeBeforeUpdate = cityEastRepository.findAll().size();

        // Update the cityEast using partial update
        CityEast partialUpdatedCityEast = new CityEast();
        partialUpdatedCityEast.setId(cityEast.getId());

        partialUpdatedCityEast.name(UPDATED_NAME);

        restCityEastMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCityEast.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCityEast))
            )
            .andExpect(status().isOk());

        // Validate the CityEast in the database
        List<CityEast> cityEastList = cityEastRepository.findAll();
        assertThat(cityEastList).hasSize(databaseSizeBeforeUpdate);
        CityEast testCityEast = cityEastList.get(cityEastList.size() - 1);
        assertThat(testCityEast.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCityEast.getDistrictId()).isEqualTo(DEFAULT_DISTRICT_ID);
    }

    @Test
    @Transactional
    void fullUpdateCityEastWithPatch() throws Exception {
        // Initialize the database
        cityEastRepository.saveAndFlush(cityEast);

        int databaseSizeBeforeUpdate = cityEastRepository.findAll().size();

        // Update the cityEast using partial update
        CityEast partialUpdatedCityEast = new CityEast();
        partialUpdatedCityEast.setId(cityEast.getId());

        partialUpdatedCityEast.name(UPDATED_NAME).districtId(UPDATED_DISTRICT_ID);

        restCityEastMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCityEast.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCityEast))
            )
            .andExpect(status().isOk());

        // Validate the CityEast in the database
        List<CityEast> cityEastList = cityEastRepository.findAll();
        assertThat(cityEastList).hasSize(databaseSizeBeforeUpdate);
        CityEast testCityEast = cityEastList.get(cityEastList.size() - 1);
        assertThat(testCityEast.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCityEast.getDistrictId()).isEqualTo(UPDATED_DISTRICT_ID);
    }

    @Test
    @Transactional
    void patchNonExistingCityEast() throws Exception {
        int databaseSizeBeforeUpdate = cityEastRepository.findAll().size();
        cityEast.setId(count.incrementAndGet());

        // Create the CityEast
        CityEastDTO cityEastDTO = cityEastMapper.toDto(cityEast);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCityEastMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, cityEastDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(cityEastDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CityEast in the database
        List<CityEast> cityEastList = cityEastRepository.findAll();
        assertThat(cityEastList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCityEast() throws Exception {
        int databaseSizeBeforeUpdate = cityEastRepository.findAll().size();
        cityEast.setId(count.incrementAndGet());

        // Create the CityEast
        CityEastDTO cityEastDTO = cityEastMapper.toDto(cityEast);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCityEastMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(cityEastDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CityEast in the database
        List<CityEast> cityEastList = cityEastRepository.findAll();
        assertThat(cityEastList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCityEast() throws Exception {
        int databaseSizeBeforeUpdate = cityEastRepository.findAll().size();
        cityEast.setId(count.incrementAndGet());

        // Create the CityEast
        CityEastDTO cityEastDTO = cityEastMapper.toDto(cityEast);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCityEastMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(cityEastDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CityEast in the database
        List<CityEast> cityEastList = cityEastRepository.findAll();
        assertThat(cityEastList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCityEast() throws Exception {
        // Initialize the database
        cityEastRepository.saveAndFlush(cityEast);

        int databaseSizeBeforeDelete = cityEastRepository.findAll().size();

        // Delete the cityEast
        restCityEastMockMvc
            .perform(delete(ENTITY_API_URL_ID, cityEast.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CityEast> cityEastList = cityEastRepository.findAll();
        assertThat(cityEastList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
