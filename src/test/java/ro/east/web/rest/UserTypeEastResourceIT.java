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
import ro.east.domain.UserTypeEast;
import ro.east.repository.UserTypeEastRepository;
import ro.east.service.dto.UserTypeEastDTO;
import ro.east.service.mapper.UserTypeEastMapper;

/**
 * Integration tests for the {@link UserTypeEastResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class UserTypeEastResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final Double DEFAULT_DISCOUNT = 1D;
    private static final Double UPDATED_DISCOUNT = 2D;

    private static final String ENTITY_API_URL = "/api/user-type-easts";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private UserTypeEastRepository userTypeEastRepository;

    @Autowired
    private UserTypeEastMapper userTypeEastMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restUserTypeEastMockMvc;

    private UserTypeEast userTypeEast;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserTypeEast createEntity(EntityManager em) {
        UserTypeEast userTypeEast = new UserTypeEast().code(DEFAULT_CODE).discount(DEFAULT_DISCOUNT);
        return userTypeEast;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserTypeEast createUpdatedEntity(EntityManager em) {
        UserTypeEast userTypeEast = new UserTypeEast().code(UPDATED_CODE).discount(UPDATED_DISCOUNT);
        return userTypeEast;
    }

    @BeforeEach
    public void initTest() {
        userTypeEast = createEntity(em);
    }

    @Test
    @Transactional
    void createUserTypeEast() throws Exception {
        int databaseSizeBeforeCreate = userTypeEastRepository.findAll().size();
        // Create the UserTypeEast
        UserTypeEastDTO userTypeEastDTO = userTypeEastMapper.toDto(userTypeEast);
        restUserTypeEastMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(userTypeEastDTO))
            )
            .andExpect(status().isCreated());

        // Validate the UserTypeEast in the database
        List<UserTypeEast> userTypeEastList = userTypeEastRepository.findAll();
        assertThat(userTypeEastList).hasSize(databaseSizeBeforeCreate + 1);
        UserTypeEast testUserTypeEast = userTypeEastList.get(userTypeEastList.size() - 1);
        assertThat(testUserTypeEast.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testUserTypeEast.getDiscount()).isEqualTo(DEFAULT_DISCOUNT);
    }

    @Test
    @Transactional
    void createUserTypeEastWithExistingId() throws Exception {
        // Create the UserTypeEast with an existing ID
        userTypeEast.setId(1L);
        UserTypeEastDTO userTypeEastDTO = userTypeEastMapper.toDto(userTypeEast);

        int databaseSizeBeforeCreate = userTypeEastRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserTypeEastMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(userTypeEastDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserTypeEast in the database
        List<UserTypeEast> userTypeEastList = userTypeEastRepository.findAll();
        assertThat(userTypeEastList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllUserTypeEasts() throws Exception {
        // Initialize the database
        userTypeEastRepository.saveAndFlush(userTypeEast);

        // Get all the userTypeEastList
        restUserTypeEastMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userTypeEast.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].discount").value(hasItem(DEFAULT_DISCOUNT.doubleValue())));
    }

    @Test
    @Transactional
    void getUserTypeEast() throws Exception {
        // Initialize the database
        userTypeEastRepository.saveAndFlush(userTypeEast);

        // Get the userTypeEast
        restUserTypeEastMockMvc
            .perform(get(ENTITY_API_URL_ID, userTypeEast.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(userTypeEast.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.discount").value(DEFAULT_DISCOUNT.doubleValue()));
    }

    @Test
    @Transactional
    void getNonExistingUserTypeEast() throws Exception {
        // Get the userTypeEast
        restUserTypeEastMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingUserTypeEast() throws Exception {
        // Initialize the database
        userTypeEastRepository.saveAndFlush(userTypeEast);

        int databaseSizeBeforeUpdate = userTypeEastRepository.findAll().size();

        // Update the userTypeEast
        UserTypeEast updatedUserTypeEast = userTypeEastRepository.findById(userTypeEast.getId()).get();
        // Disconnect from session so that the updates on updatedUserTypeEast are not directly saved in db
        em.detach(updatedUserTypeEast);
        updatedUserTypeEast.code(UPDATED_CODE).discount(UPDATED_DISCOUNT);
        UserTypeEastDTO userTypeEastDTO = userTypeEastMapper.toDto(updatedUserTypeEast);

        restUserTypeEastMockMvc
            .perform(
                put(ENTITY_API_URL_ID, userTypeEastDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(userTypeEastDTO))
            )
            .andExpect(status().isOk());

        // Validate the UserTypeEast in the database
        List<UserTypeEast> userTypeEastList = userTypeEastRepository.findAll();
        assertThat(userTypeEastList).hasSize(databaseSizeBeforeUpdate);
        UserTypeEast testUserTypeEast = userTypeEastList.get(userTypeEastList.size() - 1);
        assertThat(testUserTypeEast.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testUserTypeEast.getDiscount()).isEqualTo(UPDATED_DISCOUNT);
    }

    @Test
    @Transactional
    void putNonExistingUserTypeEast() throws Exception {
        int databaseSizeBeforeUpdate = userTypeEastRepository.findAll().size();
        userTypeEast.setId(count.incrementAndGet());

        // Create the UserTypeEast
        UserTypeEastDTO userTypeEastDTO = userTypeEastMapper.toDto(userTypeEast);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserTypeEastMockMvc
            .perform(
                put(ENTITY_API_URL_ID, userTypeEastDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(userTypeEastDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserTypeEast in the database
        List<UserTypeEast> userTypeEastList = userTypeEastRepository.findAll();
        assertThat(userTypeEastList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchUserTypeEast() throws Exception {
        int databaseSizeBeforeUpdate = userTypeEastRepository.findAll().size();
        userTypeEast.setId(count.incrementAndGet());

        // Create the UserTypeEast
        UserTypeEastDTO userTypeEastDTO = userTypeEastMapper.toDto(userTypeEast);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserTypeEastMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(userTypeEastDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserTypeEast in the database
        List<UserTypeEast> userTypeEastList = userTypeEastRepository.findAll();
        assertThat(userTypeEastList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamUserTypeEast() throws Exception {
        int databaseSizeBeforeUpdate = userTypeEastRepository.findAll().size();
        userTypeEast.setId(count.incrementAndGet());

        // Create the UserTypeEast
        UserTypeEastDTO userTypeEastDTO = userTypeEastMapper.toDto(userTypeEast);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserTypeEastMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(userTypeEastDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the UserTypeEast in the database
        List<UserTypeEast> userTypeEastList = userTypeEastRepository.findAll();
        assertThat(userTypeEastList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateUserTypeEastWithPatch() throws Exception {
        // Initialize the database
        userTypeEastRepository.saveAndFlush(userTypeEast);

        int databaseSizeBeforeUpdate = userTypeEastRepository.findAll().size();

        // Update the userTypeEast using partial update
        UserTypeEast partialUpdatedUserTypeEast = new UserTypeEast();
        partialUpdatedUserTypeEast.setId(userTypeEast.getId());

        partialUpdatedUserTypeEast.discount(UPDATED_DISCOUNT);

        restUserTypeEastMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUserTypeEast.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedUserTypeEast))
            )
            .andExpect(status().isOk());

        // Validate the UserTypeEast in the database
        List<UserTypeEast> userTypeEastList = userTypeEastRepository.findAll();
        assertThat(userTypeEastList).hasSize(databaseSizeBeforeUpdate);
        UserTypeEast testUserTypeEast = userTypeEastList.get(userTypeEastList.size() - 1);
        assertThat(testUserTypeEast.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testUserTypeEast.getDiscount()).isEqualTo(UPDATED_DISCOUNT);
    }

    @Test
    @Transactional
    void fullUpdateUserTypeEastWithPatch() throws Exception {
        // Initialize the database
        userTypeEastRepository.saveAndFlush(userTypeEast);

        int databaseSizeBeforeUpdate = userTypeEastRepository.findAll().size();

        // Update the userTypeEast using partial update
        UserTypeEast partialUpdatedUserTypeEast = new UserTypeEast();
        partialUpdatedUserTypeEast.setId(userTypeEast.getId());

        partialUpdatedUserTypeEast.code(UPDATED_CODE).discount(UPDATED_DISCOUNT);

        restUserTypeEastMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUserTypeEast.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedUserTypeEast))
            )
            .andExpect(status().isOk());

        // Validate the UserTypeEast in the database
        List<UserTypeEast> userTypeEastList = userTypeEastRepository.findAll();
        assertThat(userTypeEastList).hasSize(databaseSizeBeforeUpdate);
        UserTypeEast testUserTypeEast = userTypeEastList.get(userTypeEastList.size() - 1);
        assertThat(testUserTypeEast.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testUserTypeEast.getDiscount()).isEqualTo(UPDATED_DISCOUNT);
    }

    @Test
    @Transactional
    void patchNonExistingUserTypeEast() throws Exception {
        int databaseSizeBeforeUpdate = userTypeEastRepository.findAll().size();
        userTypeEast.setId(count.incrementAndGet());

        // Create the UserTypeEast
        UserTypeEastDTO userTypeEastDTO = userTypeEastMapper.toDto(userTypeEast);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserTypeEastMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, userTypeEastDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(userTypeEastDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserTypeEast in the database
        List<UserTypeEast> userTypeEastList = userTypeEastRepository.findAll();
        assertThat(userTypeEastList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchUserTypeEast() throws Exception {
        int databaseSizeBeforeUpdate = userTypeEastRepository.findAll().size();
        userTypeEast.setId(count.incrementAndGet());

        // Create the UserTypeEast
        UserTypeEastDTO userTypeEastDTO = userTypeEastMapper.toDto(userTypeEast);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserTypeEastMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(userTypeEastDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserTypeEast in the database
        List<UserTypeEast> userTypeEastList = userTypeEastRepository.findAll();
        assertThat(userTypeEastList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamUserTypeEast() throws Exception {
        int databaseSizeBeforeUpdate = userTypeEastRepository.findAll().size();
        userTypeEast.setId(count.incrementAndGet());

        // Create the UserTypeEast
        UserTypeEastDTO userTypeEastDTO = userTypeEastMapper.toDto(userTypeEast);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserTypeEastMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(userTypeEastDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the UserTypeEast in the database
        List<UserTypeEast> userTypeEastList = userTypeEastRepository.findAll();
        assertThat(userTypeEastList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteUserTypeEast() throws Exception {
        // Initialize the database
        userTypeEastRepository.saveAndFlush(userTypeEast);

        int databaseSizeBeforeDelete = userTypeEastRepository.findAll().size();

        // Delete the userTypeEast
        restUserTypeEastMockMvc
            .perform(delete(ENTITY_API_URL_ID, userTypeEast.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<UserTypeEast> userTypeEastList = userTypeEastRepository.findAll();
        assertThat(userTypeEastList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
