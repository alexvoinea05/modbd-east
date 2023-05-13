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
import ro.east.domain.AppUserEast;
import ro.east.repository.AppUserEastRepository;
import ro.east.service.dto.AppUserEastDTO;
import ro.east.service.mapper.AppUserEastMapper;

/**
 * Integration tests for the {@link AppUserEastResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AppUserEastResourceIT {

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final Double DEFAULT_BALANCE = 1D;
    private static final Double UPDATED_BALANCE = 2D;

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final Long DEFAULT_USER_TYPE_ID = 1L;
    private static final Long UPDATED_USER_TYPE_ID = 2L;

    private static final String ENTITY_API_URL = "/api/app-user-easts";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AppUserEastRepository appUserEastRepository;

    @Autowired
    private AppUserEastMapper appUserEastMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAppUserEastMockMvc;

    private AppUserEast appUserEast;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AppUserEast createEntity(EntityManager em) {
        AppUserEast appUserEast = new AppUserEast()
            .email(DEFAULT_EMAIL)
            .balance(DEFAULT_BALANCE)
            .lastName(DEFAULT_LAST_NAME)
            .firstName(DEFAULT_FIRST_NAME)
            .userTypeId(DEFAULT_USER_TYPE_ID);
        return appUserEast;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AppUserEast createUpdatedEntity(EntityManager em) {
        AppUserEast appUserEast = new AppUserEast()
            .email(UPDATED_EMAIL)
            .balance(UPDATED_BALANCE)
            .lastName(UPDATED_LAST_NAME)
            .firstName(UPDATED_FIRST_NAME)
            .userTypeId(UPDATED_USER_TYPE_ID);
        return appUserEast;
    }

    @BeforeEach
    public void initTest() {
        appUserEast = createEntity(em);
    }

    @Test
    @Transactional
    void createAppUserEast() throws Exception {
        int databaseSizeBeforeCreate = appUserEastRepository.findAll().size();
        // Create the AppUserEast
        AppUserEastDTO appUserEastDTO = appUserEastMapper.toDto(appUserEast);
        restAppUserEastMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(appUserEastDTO))
            )
            .andExpect(status().isCreated());

        // Validate the AppUserEast in the database
        List<AppUserEast> appUserEastList = appUserEastRepository.findAll();
        assertThat(appUserEastList).hasSize(databaseSizeBeforeCreate + 1);
        AppUserEast testAppUserEast = appUserEastList.get(appUserEastList.size() - 1);
        assertThat(testAppUserEast.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testAppUserEast.getBalance()).isEqualTo(DEFAULT_BALANCE);
        assertThat(testAppUserEast.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testAppUserEast.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testAppUserEast.getUserTypeId()).isEqualTo(DEFAULT_USER_TYPE_ID);
    }

    @Test
    @Transactional
    void createAppUserEastWithExistingId() throws Exception {
        // Create the AppUserEast with an existing ID
        appUserEast.setId(1L);
        AppUserEastDTO appUserEastDTO = appUserEastMapper.toDto(appUserEast);

        int databaseSizeBeforeCreate = appUserEastRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAppUserEastMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(appUserEastDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AppUserEast in the database
        List<AppUserEast> appUserEastList = appUserEastRepository.findAll();
        assertThat(appUserEastList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllAppUserEasts() throws Exception {
        // Initialize the database
        appUserEastRepository.saveAndFlush(appUserEast);

        // Get all the appUserEastList
        restAppUserEastMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(appUserEast.getId().intValue())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].balance").value(hasItem(DEFAULT_BALANCE.doubleValue())))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME)))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].userTypeId").value(hasItem(DEFAULT_USER_TYPE_ID.intValue())));
    }

    @Test
    @Transactional
    void getAppUserEast() throws Exception {
        // Initialize the database
        appUserEastRepository.saveAndFlush(appUserEast);

        // Get the appUserEast
        restAppUserEastMockMvc
            .perform(get(ENTITY_API_URL_ID, appUserEast.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(appUserEast.getId().intValue()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.balance").value(DEFAULT_BALANCE.doubleValue()))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME))
            .andExpect(jsonPath("$.userTypeId").value(DEFAULT_USER_TYPE_ID.intValue()));
    }

    @Test
    @Transactional
    void getNonExistingAppUserEast() throws Exception {
        // Get the appUserEast
        restAppUserEastMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAppUserEast() throws Exception {
        // Initialize the database
        appUserEastRepository.saveAndFlush(appUserEast);

        int databaseSizeBeforeUpdate = appUserEastRepository.findAll().size();

        // Update the appUserEast
        AppUserEast updatedAppUserEast = appUserEastRepository.findById(appUserEast.getId()).get();
        // Disconnect from session so that the updates on updatedAppUserEast are not directly saved in db
        em.detach(updatedAppUserEast);
        updatedAppUserEast
            .email(UPDATED_EMAIL)
            .balance(UPDATED_BALANCE)
            .lastName(UPDATED_LAST_NAME)
            .firstName(UPDATED_FIRST_NAME)
            .userTypeId(UPDATED_USER_TYPE_ID);
        AppUserEastDTO appUserEastDTO = appUserEastMapper.toDto(updatedAppUserEast);

        restAppUserEastMockMvc
            .perform(
                put(ENTITY_API_URL_ID, appUserEastDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(appUserEastDTO))
            )
            .andExpect(status().isOk());

        // Validate the AppUserEast in the database
        List<AppUserEast> appUserEastList = appUserEastRepository.findAll();
        assertThat(appUserEastList).hasSize(databaseSizeBeforeUpdate);
        AppUserEast testAppUserEast = appUserEastList.get(appUserEastList.size() - 1);
        assertThat(testAppUserEast.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testAppUserEast.getBalance()).isEqualTo(UPDATED_BALANCE);
        assertThat(testAppUserEast.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testAppUserEast.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testAppUserEast.getUserTypeId()).isEqualTo(UPDATED_USER_TYPE_ID);
    }

    @Test
    @Transactional
    void putNonExistingAppUserEast() throws Exception {
        int databaseSizeBeforeUpdate = appUserEastRepository.findAll().size();
        appUserEast.setId(count.incrementAndGet());

        // Create the AppUserEast
        AppUserEastDTO appUserEastDTO = appUserEastMapper.toDto(appUserEast);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAppUserEastMockMvc
            .perform(
                put(ENTITY_API_URL_ID, appUserEastDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(appUserEastDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AppUserEast in the database
        List<AppUserEast> appUserEastList = appUserEastRepository.findAll();
        assertThat(appUserEastList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAppUserEast() throws Exception {
        int databaseSizeBeforeUpdate = appUserEastRepository.findAll().size();
        appUserEast.setId(count.incrementAndGet());

        // Create the AppUserEast
        AppUserEastDTO appUserEastDTO = appUserEastMapper.toDto(appUserEast);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAppUserEastMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(appUserEastDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AppUserEast in the database
        List<AppUserEast> appUserEastList = appUserEastRepository.findAll();
        assertThat(appUserEastList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAppUserEast() throws Exception {
        int databaseSizeBeforeUpdate = appUserEastRepository.findAll().size();
        appUserEast.setId(count.incrementAndGet());

        // Create the AppUserEast
        AppUserEastDTO appUserEastDTO = appUserEastMapper.toDto(appUserEast);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAppUserEastMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(appUserEastDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the AppUserEast in the database
        List<AppUserEast> appUserEastList = appUserEastRepository.findAll();
        assertThat(appUserEastList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAppUserEastWithPatch() throws Exception {
        // Initialize the database
        appUserEastRepository.saveAndFlush(appUserEast);

        int databaseSizeBeforeUpdate = appUserEastRepository.findAll().size();

        // Update the appUserEast using partial update
        AppUserEast partialUpdatedAppUserEast = new AppUserEast();
        partialUpdatedAppUserEast.setId(appUserEast.getId());

        partialUpdatedAppUserEast.balance(UPDATED_BALANCE).lastName(UPDATED_LAST_NAME);

        restAppUserEastMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAppUserEast.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAppUserEast))
            )
            .andExpect(status().isOk());

        // Validate the AppUserEast in the database
        List<AppUserEast> appUserEastList = appUserEastRepository.findAll();
        assertThat(appUserEastList).hasSize(databaseSizeBeforeUpdate);
        AppUserEast testAppUserEast = appUserEastList.get(appUserEastList.size() - 1);
        assertThat(testAppUserEast.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testAppUserEast.getBalance()).isEqualTo(UPDATED_BALANCE);
        assertThat(testAppUserEast.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testAppUserEast.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testAppUserEast.getUserTypeId()).isEqualTo(DEFAULT_USER_TYPE_ID);
    }

    @Test
    @Transactional
    void fullUpdateAppUserEastWithPatch() throws Exception {
        // Initialize the database
        appUserEastRepository.saveAndFlush(appUserEast);

        int databaseSizeBeforeUpdate = appUserEastRepository.findAll().size();

        // Update the appUserEast using partial update
        AppUserEast partialUpdatedAppUserEast = new AppUserEast();
        partialUpdatedAppUserEast.setId(appUserEast.getId());

        partialUpdatedAppUserEast
            .email(UPDATED_EMAIL)
            .balance(UPDATED_BALANCE)
            .lastName(UPDATED_LAST_NAME)
            .firstName(UPDATED_FIRST_NAME)
            .userTypeId(UPDATED_USER_TYPE_ID);

        restAppUserEastMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAppUserEast.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAppUserEast))
            )
            .andExpect(status().isOk());

        // Validate the AppUserEast in the database
        List<AppUserEast> appUserEastList = appUserEastRepository.findAll();
        assertThat(appUserEastList).hasSize(databaseSizeBeforeUpdate);
        AppUserEast testAppUserEast = appUserEastList.get(appUserEastList.size() - 1);
        assertThat(testAppUserEast.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testAppUserEast.getBalance()).isEqualTo(UPDATED_BALANCE);
        assertThat(testAppUserEast.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testAppUserEast.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testAppUserEast.getUserTypeId()).isEqualTo(UPDATED_USER_TYPE_ID);
    }

    @Test
    @Transactional
    void patchNonExistingAppUserEast() throws Exception {
        int databaseSizeBeforeUpdate = appUserEastRepository.findAll().size();
        appUserEast.setId(count.incrementAndGet());

        // Create the AppUserEast
        AppUserEastDTO appUserEastDTO = appUserEastMapper.toDto(appUserEast);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAppUserEastMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, appUserEastDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(appUserEastDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AppUserEast in the database
        List<AppUserEast> appUserEastList = appUserEastRepository.findAll();
        assertThat(appUserEastList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAppUserEast() throws Exception {
        int databaseSizeBeforeUpdate = appUserEastRepository.findAll().size();
        appUserEast.setId(count.incrementAndGet());

        // Create the AppUserEast
        AppUserEastDTO appUserEastDTO = appUserEastMapper.toDto(appUserEast);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAppUserEastMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(appUserEastDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AppUserEast in the database
        List<AppUserEast> appUserEastList = appUserEastRepository.findAll();
        assertThat(appUserEastList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAppUserEast() throws Exception {
        int databaseSizeBeforeUpdate = appUserEastRepository.findAll().size();
        appUserEast.setId(count.incrementAndGet());

        // Create the AppUserEast
        AppUserEastDTO appUserEastDTO = appUserEastMapper.toDto(appUserEast);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAppUserEastMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(appUserEastDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AppUserEast in the database
        List<AppUserEast> appUserEastList = appUserEastRepository.findAll();
        assertThat(appUserEastList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAppUserEast() throws Exception {
        // Initialize the database
        appUserEastRepository.saveAndFlush(appUserEast);

        int databaseSizeBeforeDelete = appUserEastRepository.findAll().size();

        // Delete the appUserEast
        restAppUserEastMockMvc
            .perform(delete(ENTITY_API_URL_ID, appUserEast.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AppUserEast> appUserEastList = appUserEastRepository.findAll();
        assertThat(appUserEastList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
