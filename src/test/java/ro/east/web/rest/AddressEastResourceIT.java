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
import ro.east.domain.AddressEast;
import ro.east.repository.AddressEastRepository;
import ro.east.service.dto.AddressEastDTO;
import ro.east.service.mapper.AddressEastMapper;

/**
 * Integration tests for the {@link AddressEastResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AddressEastResourceIT {

    private static final String DEFAULT_STREET_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_STREET_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_STREET = "AAAAAAAAAA";
    private static final String UPDATED_STREET = "BBBBBBBBBB";

    private static final String DEFAULT_ZIPCODE = "AAAAAAAAAA";
    private static final String UPDATED_ZIPCODE = "BBBBBBBBBB";

    private static final Long DEFAULT_CITY_ID = 1L;
    private static final Long UPDATED_CITY_ID = 2L;

    private static final String ENTITY_API_URL = "/api/address-easts";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AddressEastRepository addressEastRepository;

    @Autowired
    private AddressEastMapper addressEastMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAddressEastMockMvc;

    private AddressEast addressEast;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AddressEast createEntity(EntityManager em) {
        AddressEast addressEast = new AddressEast()
            .streetNumber(DEFAULT_STREET_NUMBER)
            .street(DEFAULT_STREET)
            .zipcode(DEFAULT_ZIPCODE)
            .cityId(DEFAULT_CITY_ID);
        return addressEast;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AddressEast createUpdatedEntity(EntityManager em) {
        AddressEast addressEast = new AddressEast()
            .streetNumber(UPDATED_STREET_NUMBER)
            .street(UPDATED_STREET)
            .zipcode(UPDATED_ZIPCODE)
            .cityId(UPDATED_CITY_ID);
        return addressEast;
    }

    @BeforeEach
    public void initTest() {
        addressEast = createEntity(em);
    }

    @Test
    @Transactional
    void createAddressEast() throws Exception {
        int databaseSizeBeforeCreate = addressEastRepository.findAll().size();
        // Create the AddressEast
        AddressEastDTO addressEastDTO = addressEastMapper.toDto(addressEast);
        restAddressEastMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(addressEastDTO))
            )
            .andExpect(status().isCreated());

        // Validate the AddressEast in the database
        List<AddressEast> addressEastList = addressEastRepository.findAll();
        assertThat(addressEastList).hasSize(databaseSizeBeforeCreate + 1);
        AddressEast testAddressEast = addressEastList.get(addressEastList.size() - 1);
        assertThat(testAddressEast.getStreetNumber()).isEqualTo(DEFAULT_STREET_NUMBER);
        assertThat(testAddressEast.getStreet()).isEqualTo(DEFAULT_STREET);
        assertThat(testAddressEast.getZipcode()).isEqualTo(DEFAULT_ZIPCODE);
        assertThat(testAddressEast.getCityId()).isEqualTo(DEFAULT_CITY_ID);
    }

    @Test
    @Transactional
    void createAddressEastWithExistingId() throws Exception {
        // Create the AddressEast with an existing ID
        addressEast.setId(1L);
        AddressEastDTO addressEastDTO = addressEastMapper.toDto(addressEast);

        int databaseSizeBeforeCreate = addressEastRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAddressEastMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(addressEastDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AddressEast in the database
        List<AddressEast> addressEastList = addressEastRepository.findAll();
        assertThat(addressEastList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllAddressEasts() throws Exception {
        // Initialize the database
        addressEastRepository.saveAndFlush(addressEast);

        // Get all the addressEastList
        restAddressEastMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(addressEast.getId().intValue())))
            .andExpect(jsonPath("$.[*].streetNumber").value(hasItem(DEFAULT_STREET_NUMBER)))
            .andExpect(jsonPath("$.[*].street").value(hasItem(DEFAULT_STREET)))
            .andExpect(jsonPath("$.[*].zipcode").value(hasItem(DEFAULT_ZIPCODE)))
            .andExpect(jsonPath("$.[*].cityId").value(hasItem(DEFAULT_CITY_ID.intValue())));
    }

    @Test
    @Transactional
    void getAddressEast() throws Exception {
        // Initialize the database
        addressEastRepository.saveAndFlush(addressEast);

        // Get the addressEast
        restAddressEastMockMvc
            .perform(get(ENTITY_API_URL_ID, addressEast.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(addressEast.getId().intValue()))
            .andExpect(jsonPath("$.streetNumber").value(DEFAULT_STREET_NUMBER))
            .andExpect(jsonPath("$.street").value(DEFAULT_STREET))
            .andExpect(jsonPath("$.zipcode").value(DEFAULT_ZIPCODE))
            .andExpect(jsonPath("$.cityId").value(DEFAULT_CITY_ID.intValue()));
    }

    @Test
    @Transactional
    void getNonExistingAddressEast() throws Exception {
        // Get the addressEast
        restAddressEastMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAddressEast() throws Exception {
        // Initialize the database
        addressEastRepository.saveAndFlush(addressEast);

        int databaseSizeBeforeUpdate = addressEastRepository.findAll().size();

        // Update the addressEast
        AddressEast updatedAddressEast = addressEastRepository.findById(addressEast.getId()).get();
        // Disconnect from session so that the updates on updatedAddressEast are not directly saved in db
        em.detach(updatedAddressEast);
        updatedAddressEast.streetNumber(UPDATED_STREET_NUMBER).street(UPDATED_STREET).zipcode(UPDATED_ZIPCODE).cityId(UPDATED_CITY_ID);
        AddressEastDTO addressEastDTO = addressEastMapper.toDto(updatedAddressEast);

        restAddressEastMockMvc
            .perform(
                put(ENTITY_API_URL_ID, addressEastDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(addressEastDTO))
            )
            .andExpect(status().isOk());

        // Validate the AddressEast in the database
        List<AddressEast> addressEastList = addressEastRepository.findAll();
        assertThat(addressEastList).hasSize(databaseSizeBeforeUpdate);
        AddressEast testAddressEast = addressEastList.get(addressEastList.size() - 1);
        assertThat(testAddressEast.getStreetNumber()).isEqualTo(UPDATED_STREET_NUMBER);
        assertThat(testAddressEast.getStreet()).isEqualTo(UPDATED_STREET);
        assertThat(testAddressEast.getZipcode()).isEqualTo(UPDATED_ZIPCODE);
        assertThat(testAddressEast.getCityId()).isEqualTo(UPDATED_CITY_ID);
    }

    @Test
    @Transactional
    void putNonExistingAddressEast() throws Exception {
        int databaseSizeBeforeUpdate = addressEastRepository.findAll().size();
        addressEast.setId(count.incrementAndGet());

        // Create the AddressEast
        AddressEastDTO addressEastDTO = addressEastMapper.toDto(addressEast);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAddressEastMockMvc
            .perform(
                put(ENTITY_API_URL_ID, addressEastDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(addressEastDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AddressEast in the database
        List<AddressEast> addressEastList = addressEastRepository.findAll();
        assertThat(addressEastList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAddressEast() throws Exception {
        int databaseSizeBeforeUpdate = addressEastRepository.findAll().size();
        addressEast.setId(count.incrementAndGet());

        // Create the AddressEast
        AddressEastDTO addressEastDTO = addressEastMapper.toDto(addressEast);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAddressEastMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(addressEastDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AddressEast in the database
        List<AddressEast> addressEastList = addressEastRepository.findAll();
        assertThat(addressEastList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAddressEast() throws Exception {
        int databaseSizeBeforeUpdate = addressEastRepository.findAll().size();
        addressEast.setId(count.incrementAndGet());

        // Create the AddressEast
        AddressEastDTO addressEastDTO = addressEastMapper.toDto(addressEast);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAddressEastMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(addressEastDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the AddressEast in the database
        List<AddressEast> addressEastList = addressEastRepository.findAll();
        assertThat(addressEastList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAddressEastWithPatch() throws Exception {
        // Initialize the database
        addressEastRepository.saveAndFlush(addressEast);

        int databaseSizeBeforeUpdate = addressEastRepository.findAll().size();

        // Update the addressEast using partial update
        AddressEast partialUpdatedAddressEast = new AddressEast();
        partialUpdatedAddressEast.setId(addressEast.getId());

        partialUpdatedAddressEast.streetNumber(UPDATED_STREET_NUMBER).street(UPDATED_STREET);

        restAddressEastMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAddressEast.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAddressEast))
            )
            .andExpect(status().isOk());

        // Validate the AddressEast in the database
        List<AddressEast> addressEastList = addressEastRepository.findAll();
        assertThat(addressEastList).hasSize(databaseSizeBeforeUpdate);
        AddressEast testAddressEast = addressEastList.get(addressEastList.size() - 1);
        assertThat(testAddressEast.getStreetNumber()).isEqualTo(UPDATED_STREET_NUMBER);
        assertThat(testAddressEast.getStreet()).isEqualTo(UPDATED_STREET);
        assertThat(testAddressEast.getZipcode()).isEqualTo(DEFAULT_ZIPCODE);
        assertThat(testAddressEast.getCityId()).isEqualTo(DEFAULT_CITY_ID);
    }

    @Test
    @Transactional
    void fullUpdateAddressEastWithPatch() throws Exception {
        // Initialize the database
        addressEastRepository.saveAndFlush(addressEast);

        int databaseSizeBeforeUpdate = addressEastRepository.findAll().size();

        // Update the addressEast using partial update
        AddressEast partialUpdatedAddressEast = new AddressEast();
        partialUpdatedAddressEast.setId(addressEast.getId());

        partialUpdatedAddressEast
            .streetNumber(UPDATED_STREET_NUMBER)
            .street(UPDATED_STREET)
            .zipcode(UPDATED_ZIPCODE)
            .cityId(UPDATED_CITY_ID);

        restAddressEastMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAddressEast.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAddressEast))
            )
            .andExpect(status().isOk());

        // Validate the AddressEast in the database
        List<AddressEast> addressEastList = addressEastRepository.findAll();
        assertThat(addressEastList).hasSize(databaseSizeBeforeUpdate);
        AddressEast testAddressEast = addressEastList.get(addressEastList.size() - 1);
        assertThat(testAddressEast.getStreetNumber()).isEqualTo(UPDATED_STREET_NUMBER);
        assertThat(testAddressEast.getStreet()).isEqualTo(UPDATED_STREET);
        assertThat(testAddressEast.getZipcode()).isEqualTo(UPDATED_ZIPCODE);
        assertThat(testAddressEast.getCityId()).isEqualTo(UPDATED_CITY_ID);
    }

    @Test
    @Transactional
    void patchNonExistingAddressEast() throws Exception {
        int databaseSizeBeforeUpdate = addressEastRepository.findAll().size();
        addressEast.setId(count.incrementAndGet());

        // Create the AddressEast
        AddressEastDTO addressEastDTO = addressEastMapper.toDto(addressEast);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAddressEastMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, addressEastDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(addressEastDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AddressEast in the database
        List<AddressEast> addressEastList = addressEastRepository.findAll();
        assertThat(addressEastList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAddressEast() throws Exception {
        int databaseSizeBeforeUpdate = addressEastRepository.findAll().size();
        addressEast.setId(count.incrementAndGet());

        // Create the AddressEast
        AddressEastDTO addressEastDTO = addressEastMapper.toDto(addressEast);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAddressEastMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(addressEastDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AddressEast in the database
        List<AddressEast> addressEastList = addressEastRepository.findAll();
        assertThat(addressEastList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAddressEast() throws Exception {
        int databaseSizeBeforeUpdate = addressEastRepository.findAll().size();
        addressEast.setId(count.incrementAndGet());

        // Create the AddressEast
        AddressEastDTO addressEastDTO = addressEastMapper.toDto(addressEast);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAddressEastMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(addressEastDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AddressEast in the database
        List<AddressEast> addressEastList = addressEastRepository.findAll();
        assertThat(addressEastList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAddressEast() throws Exception {
        // Initialize the database
        addressEastRepository.saveAndFlush(addressEast);

        int databaseSizeBeforeDelete = addressEastRepository.findAll().size();

        // Delete the addressEast
        restAddressEastMockMvc
            .perform(delete(ENTITY_API_URL_ID, addressEast.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AddressEast> addressEastList = addressEastRepository.findAll();
        assertThat(addressEastList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
