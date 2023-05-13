package ro.east.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static ro.east.web.rest.TestUtil.sameInstant;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
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
import ro.east.domain.TicketEast;
import ro.east.repository.TicketEastRepository;
import ro.east.service.dto.TicketEastDTO;
import ro.east.service.mapper.TicketEastMapper;

/**
 * Integration tests for the {@link TicketEastResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TicketEastResourceIT {

    private static final Double DEFAULT_FINAL_PRICE = 1D;
    private static final Double UPDATED_FINAL_PRICE = 2D;

    private static final Integer DEFAULT_QUANTITY = 1;
    private static final Integer UPDATED_QUANTITY = 2;

    private static final ZonedDateTime DEFAULT_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Long DEFAULT_APP_USER_ID = 1L;
    private static final Long UPDATED_APP_USER_ID = 2L;

    private static final Long DEFAULT_JOURNEY_ID = 1L;
    private static final Long UPDATED_JOURNEY_ID = 2L;

    private static final String ENTITY_API_URL = "/api/ticket-easts";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TicketEastRepository ticketEastRepository;

    @Autowired
    private TicketEastMapper ticketEastMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTicketEastMockMvc;

    private TicketEast ticketEast;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TicketEast createEntity(EntityManager em) {
        TicketEast ticketEast = new TicketEast()
            .finalPrice(DEFAULT_FINAL_PRICE)
            .quantity(DEFAULT_QUANTITY)
            .time(DEFAULT_TIME)
            .appUserId(DEFAULT_APP_USER_ID)
            .journeyId(DEFAULT_JOURNEY_ID);
        return ticketEast;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TicketEast createUpdatedEntity(EntityManager em) {
        TicketEast ticketEast = new TicketEast()
            .finalPrice(UPDATED_FINAL_PRICE)
            .quantity(UPDATED_QUANTITY)
            .time(UPDATED_TIME)
            .appUserId(UPDATED_APP_USER_ID)
            .journeyId(UPDATED_JOURNEY_ID);
        return ticketEast;
    }

    @BeforeEach
    public void initTest() {
        ticketEast = createEntity(em);
    }

    @Test
    @Transactional
    void createTicketEast() throws Exception {
        int databaseSizeBeforeCreate = ticketEastRepository.findAll().size();
        // Create the TicketEast
        TicketEastDTO ticketEastDTO = ticketEastMapper.toDto(ticketEast);
        restTicketEastMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ticketEastDTO)))
            .andExpect(status().isCreated());

        // Validate the TicketEast in the database
        List<TicketEast> ticketEastList = ticketEastRepository.findAll();
        assertThat(ticketEastList).hasSize(databaseSizeBeforeCreate + 1);
        TicketEast testTicketEast = ticketEastList.get(ticketEastList.size() - 1);
        assertThat(testTicketEast.getFinalPrice()).isEqualTo(DEFAULT_FINAL_PRICE);
        assertThat(testTicketEast.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
        assertThat(testTicketEast.getTime()).isEqualTo(DEFAULT_TIME);
        assertThat(testTicketEast.getAppUserId()).isEqualTo(DEFAULT_APP_USER_ID);
        assertThat(testTicketEast.getJourneyId()).isEqualTo(DEFAULT_JOURNEY_ID);
    }

    @Test
    @Transactional
    void createTicketEastWithExistingId() throws Exception {
        // Create the TicketEast with an existing ID
        ticketEast.setId(1L);
        TicketEastDTO ticketEastDTO = ticketEastMapper.toDto(ticketEast);

        int databaseSizeBeforeCreate = ticketEastRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTicketEastMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ticketEastDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TicketEast in the database
        List<TicketEast> ticketEastList = ticketEastRepository.findAll();
        assertThat(ticketEastList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllTicketEasts() throws Exception {
        // Initialize the database
        ticketEastRepository.saveAndFlush(ticketEast);

        // Get all the ticketEastList
        restTicketEastMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ticketEast.getId().intValue())))
            .andExpect(jsonPath("$.[*].finalPrice").value(hasItem(DEFAULT_FINAL_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY)))
            .andExpect(jsonPath("$.[*].time").value(hasItem(sameInstant(DEFAULT_TIME))))
            .andExpect(jsonPath("$.[*].appUserId").value(hasItem(DEFAULT_APP_USER_ID.intValue())))
            .andExpect(jsonPath("$.[*].journeyId").value(hasItem(DEFAULT_JOURNEY_ID.intValue())));
    }

    @Test
    @Transactional
    void getTicketEast() throws Exception {
        // Initialize the database
        ticketEastRepository.saveAndFlush(ticketEast);

        // Get the ticketEast
        restTicketEastMockMvc
            .perform(get(ENTITY_API_URL_ID, ticketEast.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(ticketEast.getId().intValue()))
            .andExpect(jsonPath("$.finalPrice").value(DEFAULT_FINAL_PRICE.doubleValue()))
            .andExpect(jsonPath("$.quantity").value(DEFAULT_QUANTITY))
            .andExpect(jsonPath("$.time").value(sameInstant(DEFAULT_TIME)))
            .andExpect(jsonPath("$.appUserId").value(DEFAULT_APP_USER_ID.intValue()))
            .andExpect(jsonPath("$.journeyId").value(DEFAULT_JOURNEY_ID.intValue()));
    }

    @Test
    @Transactional
    void getNonExistingTicketEast() throws Exception {
        // Get the ticketEast
        restTicketEastMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingTicketEast() throws Exception {
        // Initialize the database
        ticketEastRepository.saveAndFlush(ticketEast);

        int databaseSizeBeforeUpdate = ticketEastRepository.findAll().size();

        // Update the ticketEast
        TicketEast updatedTicketEast = ticketEastRepository.findById(ticketEast.getId()).get();
        // Disconnect from session so that the updates on updatedTicketEast are not directly saved in db
        em.detach(updatedTicketEast);
        updatedTicketEast
            .finalPrice(UPDATED_FINAL_PRICE)
            .quantity(UPDATED_QUANTITY)
            .time(UPDATED_TIME)
            .appUserId(UPDATED_APP_USER_ID)
            .journeyId(UPDATED_JOURNEY_ID);
        TicketEastDTO ticketEastDTO = ticketEastMapper.toDto(updatedTicketEast);

        restTicketEastMockMvc
            .perform(
                put(ENTITY_API_URL_ID, ticketEastDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ticketEastDTO))
            )
            .andExpect(status().isOk());

        // Validate the TicketEast in the database
        List<TicketEast> ticketEastList = ticketEastRepository.findAll();
        assertThat(ticketEastList).hasSize(databaseSizeBeforeUpdate);
        TicketEast testTicketEast = ticketEastList.get(ticketEastList.size() - 1);
        assertThat(testTicketEast.getFinalPrice()).isEqualTo(UPDATED_FINAL_PRICE);
        assertThat(testTicketEast.getQuantity()).isEqualTo(UPDATED_QUANTITY);
        assertThat(testTicketEast.getTime()).isEqualTo(UPDATED_TIME);
        assertThat(testTicketEast.getAppUserId()).isEqualTo(UPDATED_APP_USER_ID);
        assertThat(testTicketEast.getJourneyId()).isEqualTo(UPDATED_JOURNEY_ID);
    }

    @Test
    @Transactional
    void putNonExistingTicketEast() throws Exception {
        int databaseSizeBeforeUpdate = ticketEastRepository.findAll().size();
        ticketEast.setId(count.incrementAndGet());

        // Create the TicketEast
        TicketEastDTO ticketEastDTO = ticketEastMapper.toDto(ticketEast);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTicketEastMockMvc
            .perform(
                put(ENTITY_API_URL_ID, ticketEastDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ticketEastDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TicketEast in the database
        List<TicketEast> ticketEastList = ticketEastRepository.findAll();
        assertThat(ticketEastList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTicketEast() throws Exception {
        int databaseSizeBeforeUpdate = ticketEastRepository.findAll().size();
        ticketEast.setId(count.incrementAndGet());

        // Create the TicketEast
        TicketEastDTO ticketEastDTO = ticketEastMapper.toDto(ticketEast);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTicketEastMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ticketEastDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TicketEast in the database
        List<TicketEast> ticketEastList = ticketEastRepository.findAll();
        assertThat(ticketEastList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTicketEast() throws Exception {
        int databaseSizeBeforeUpdate = ticketEastRepository.findAll().size();
        ticketEast.setId(count.incrementAndGet());

        // Create the TicketEast
        TicketEastDTO ticketEastDTO = ticketEastMapper.toDto(ticketEast);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTicketEastMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ticketEastDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the TicketEast in the database
        List<TicketEast> ticketEastList = ticketEastRepository.findAll();
        assertThat(ticketEastList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTicketEastWithPatch() throws Exception {
        // Initialize the database
        ticketEastRepository.saveAndFlush(ticketEast);

        int databaseSizeBeforeUpdate = ticketEastRepository.findAll().size();

        // Update the ticketEast using partial update
        TicketEast partialUpdatedTicketEast = new TicketEast();
        partialUpdatedTicketEast.setId(ticketEast.getId());

        partialUpdatedTicketEast.time(UPDATED_TIME).appUserId(UPDATED_APP_USER_ID).journeyId(UPDATED_JOURNEY_ID);

        restTicketEastMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTicketEast.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTicketEast))
            )
            .andExpect(status().isOk());

        // Validate the TicketEast in the database
        List<TicketEast> ticketEastList = ticketEastRepository.findAll();
        assertThat(ticketEastList).hasSize(databaseSizeBeforeUpdate);
        TicketEast testTicketEast = ticketEastList.get(ticketEastList.size() - 1);
        assertThat(testTicketEast.getFinalPrice()).isEqualTo(DEFAULT_FINAL_PRICE);
        assertThat(testTicketEast.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
        assertThat(testTicketEast.getTime()).isEqualTo(UPDATED_TIME);
        assertThat(testTicketEast.getAppUserId()).isEqualTo(UPDATED_APP_USER_ID);
        assertThat(testTicketEast.getJourneyId()).isEqualTo(UPDATED_JOURNEY_ID);
    }

    @Test
    @Transactional
    void fullUpdateTicketEastWithPatch() throws Exception {
        // Initialize the database
        ticketEastRepository.saveAndFlush(ticketEast);

        int databaseSizeBeforeUpdate = ticketEastRepository.findAll().size();

        // Update the ticketEast using partial update
        TicketEast partialUpdatedTicketEast = new TicketEast();
        partialUpdatedTicketEast.setId(ticketEast.getId());

        partialUpdatedTicketEast
            .finalPrice(UPDATED_FINAL_PRICE)
            .quantity(UPDATED_QUANTITY)
            .time(UPDATED_TIME)
            .appUserId(UPDATED_APP_USER_ID)
            .journeyId(UPDATED_JOURNEY_ID);

        restTicketEastMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTicketEast.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTicketEast))
            )
            .andExpect(status().isOk());

        // Validate the TicketEast in the database
        List<TicketEast> ticketEastList = ticketEastRepository.findAll();
        assertThat(ticketEastList).hasSize(databaseSizeBeforeUpdate);
        TicketEast testTicketEast = ticketEastList.get(ticketEastList.size() - 1);
        assertThat(testTicketEast.getFinalPrice()).isEqualTo(UPDATED_FINAL_PRICE);
        assertThat(testTicketEast.getQuantity()).isEqualTo(UPDATED_QUANTITY);
        assertThat(testTicketEast.getTime()).isEqualTo(UPDATED_TIME);
        assertThat(testTicketEast.getAppUserId()).isEqualTo(UPDATED_APP_USER_ID);
        assertThat(testTicketEast.getJourneyId()).isEqualTo(UPDATED_JOURNEY_ID);
    }

    @Test
    @Transactional
    void patchNonExistingTicketEast() throws Exception {
        int databaseSizeBeforeUpdate = ticketEastRepository.findAll().size();
        ticketEast.setId(count.incrementAndGet());

        // Create the TicketEast
        TicketEastDTO ticketEastDTO = ticketEastMapper.toDto(ticketEast);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTicketEastMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, ticketEastDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(ticketEastDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TicketEast in the database
        List<TicketEast> ticketEastList = ticketEastRepository.findAll();
        assertThat(ticketEastList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTicketEast() throws Exception {
        int databaseSizeBeforeUpdate = ticketEastRepository.findAll().size();
        ticketEast.setId(count.incrementAndGet());

        // Create the TicketEast
        TicketEastDTO ticketEastDTO = ticketEastMapper.toDto(ticketEast);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTicketEastMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(ticketEastDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TicketEast in the database
        List<TicketEast> ticketEastList = ticketEastRepository.findAll();
        assertThat(ticketEastList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTicketEast() throws Exception {
        int databaseSizeBeforeUpdate = ticketEastRepository.findAll().size();
        ticketEast.setId(count.incrementAndGet());

        // Create the TicketEast
        TicketEastDTO ticketEastDTO = ticketEastMapper.toDto(ticketEast);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTicketEastMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(ticketEastDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TicketEast in the database
        List<TicketEast> ticketEastList = ticketEastRepository.findAll();
        assertThat(ticketEastList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTicketEast() throws Exception {
        // Initialize the database
        ticketEastRepository.saveAndFlush(ticketEast);

        int databaseSizeBeforeDelete = ticketEastRepository.findAll().size();

        // Delete the ticketEast
        restTicketEastMockMvc
            .perform(delete(ENTITY_API_URL_ID, ticketEast.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TicketEast> ticketEastList = ticketEastRepository.findAll();
        assertThat(ticketEastList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
