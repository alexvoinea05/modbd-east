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
import ro.east.domain.JourneyEast;
import ro.east.repository.JourneyEastRepository;
import ro.east.service.dto.JourneyEastDTO;
import ro.east.service.mapper.JourneyEastMapper;

/**
 * Integration tests for the {@link JourneyEastResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class JourneyEastResourceIT {

    private static final Double DEFAULT_DISTANCE = 1D;
    private static final Double UPDATED_DISTANCE = 2D;

    private static final Double DEFAULT_JOURNEY_DURATION = 1D;
    private static final Double UPDATED_JOURNEY_DURATION = 2D;

    private static final ZonedDateTime DEFAULT_ACTUAL_DEPARTURE_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_ACTUAL_DEPARTURE_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_PLANNED_DEPARTURE_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_PLANNED_DEPARTURE_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_ACTUAL_ARRIVAL_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_ACTUAL_ARRIVAL_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_PLANNED_ARRIVAL_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_PLANNED_ARRIVAL_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Double DEFAULT_TICKET_PRICE = 1D;
    private static final Double UPDATED_TICKET_PRICE = 2D;

    private static final Integer DEFAULT_NUMBER_OF_STOPS = 1;
    private static final Integer UPDATED_NUMBER_OF_STOPS = 2;

    private static final Double DEFAULT_TIME_OF_STOPS = 1D;
    private static final Double UPDATED_TIME_OF_STOPS = 2D;

    private static final Double DEFAULT_MINUTES_LATE = 1D;
    private static final Double UPDATED_MINUTES_LATE = 2D;

    private static final Long DEFAULT_JOURNEY_STATUS_ID = 1L;
    private static final Long UPDATED_JOURNEY_STATUS_ID = 2L;

    private static final Long DEFAULT_TRAIN_ID = 1L;
    private static final Long UPDATED_TRAIN_ID = 2L;

    private static final Long DEFAULT_COMPANY_ID = 1L;
    private static final Long UPDATED_COMPANY_ID = 2L;

    private static final Long DEFAULT_DEPARTURE_RAILWAY_STATION_ID = 1L;
    private static final Long UPDATED_DEPARTURE_RAILWAY_STATION_ID = 2L;

    private static final Long DEFAULT_ARIVAL_RAILWAY_STATION_ID = 1L;
    private static final Long UPDATED_ARIVAL_RAILWAY_STATION_ID = 2L;

    private static final String ENTITY_API_URL = "/api/journey-easts";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private JourneyEastRepository journeyEastRepository;

    @Autowired
    private JourneyEastMapper journeyEastMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restJourneyEastMockMvc;

    private JourneyEast journeyEast;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static JourneyEast createEntity(EntityManager em) {
        JourneyEast journeyEast = new JourneyEast()
            .distance(DEFAULT_DISTANCE)
            .journeyDuration(DEFAULT_JOURNEY_DURATION)
            .actualDepartureTime(DEFAULT_ACTUAL_DEPARTURE_TIME)
            .plannedDepartureTime(DEFAULT_PLANNED_DEPARTURE_TIME)
            .actualArrivalTime(DEFAULT_ACTUAL_ARRIVAL_TIME)
            .plannedArrivalTime(DEFAULT_PLANNED_ARRIVAL_TIME)
            .ticketPrice(DEFAULT_TICKET_PRICE)
            .numberOfStops(DEFAULT_NUMBER_OF_STOPS)
            .timeOfStops(DEFAULT_TIME_OF_STOPS)
            .minutesLate(DEFAULT_MINUTES_LATE)
            .journeyStatusId(DEFAULT_JOURNEY_STATUS_ID)
            .trainId(DEFAULT_TRAIN_ID)
            .companyId(DEFAULT_COMPANY_ID)
            .departureRailwayStationId(DEFAULT_DEPARTURE_RAILWAY_STATION_ID)
            .arivalRailwayStationId(DEFAULT_ARIVAL_RAILWAY_STATION_ID);
        return journeyEast;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static JourneyEast createUpdatedEntity(EntityManager em) {
        JourneyEast journeyEast = new JourneyEast()
            .distance(UPDATED_DISTANCE)
            .journeyDuration(UPDATED_JOURNEY_DURATION)
            .actualDepartureTime(UPDATED_ACTUAL_DEPARTURE_TIME)
            .plannedDepartureTime(UPDATED_PLANNED_DEPARTURE_TIME)
            .actualArrivalTime(UPDATED_ACTUAL_ARRIVAL_TIME)
            .plannedArrivalTime(UPDATED_PLANNED_ARRIVAL_TIME)
            .ticketPrice(UPDATED_TICKET_PRICE)
            .numberOfStops(UPDATED_NUMBER_OF_STOPS)
            .timeOfStops(UPDATED_TIME_OF_STOPS)
            .minutesLate(UPDATED_MINUTES_LATE)
            .journeyStatusId(UPDATED_JOURNEY_STATUS_ID)
            .trainId(UPDATED_TRAIN_ID)
            .companyId(UPDATED_COMPANY_ID)
            .departureRailwayStationId(UPDATED_DEPARTURE_RAILWAY_STATION_ID)
            .arivalRailwayStationId(UPDATED_ARIVAL_RAILWAY_STATION_ID);
        return journeyEast;
    }

    @BeforeEach
    public void initTest() {
        journeyEast = createEntity(em);
    }

    @Test
    @Transactional
    void createJourneyEast() throws Exception {
        int databaseSizeBeforeCreate = journeyEastRepository.findAll().size();
        // Create the JourneyEast
        JourneyEastDTO journeyEastDTO = journeyEastMapper.toDto(journeyEast);
        restJourneyEastMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(journeyEastDTO))
            )
            .andExpect(status().isCreated());

        // Validate the JourneyEast in the database
        List<JourneyEast> journeyEastList = journeyEastRepository.findAll();
        assertThat(journeyEastList).hasSize(databaseSizeBeforeCreate + 1);
        JourneyEast testJourneyEast = journeyEastList.get(journeyEastList.size() - 1);
        assertThat(testJourneyEast.getDistance()).isEqualTo(DEFAULT_DISTANCE);
        assertThat(testJourneyEast.getJourneyDuration()).isEqualTo(DEFAULT_JOURNEY_DURATION);
        assertThat(testJourneyEast.getActualDepartureTime()).isEqualTo(DEFAULT_ACTUAL_DEPARTURE_TIME);
        assertThat(testJourneyEast.getPlannedDepartureTime()).isEqualTo(DEFAULT_PLANNED_DEPARTURE_TIME);
        assertThat(testJourneyEast.getActualArrivalTime()).isEqualTo(DEFAULT_ACTUAL_ARRIVAL_TIME);
        assertThat(testJourneyEast.getPlannedArrivalTime()).isEqualTo(DEFAULT_PLANNED_ARRIVAL_TIME);
        assertThat(testJourneyEast.getTicketPrice()).isEqualTo(DEFAULT_TICKET_PRICE);
        assertThat(testJourneyEast.getNumberOfStops()).isEqualTo(DEFAULT_NUMBER_OF_STOPS);
        assertThat(testJourneyEast.getTimeOfStops()).isEqualTo(DEFAULT_TIME_OF_STOPS);
        assertThat(testJourneyEast.getMinutesLate()).isEqualTo(DEFAULT_MINUTES_LATE);
        assertThat(testJourneyEast.getJourneyStatusId()).isEqualTo(DEFAULT_JOURNEY_STATUS_ID);
        assertThat(testJourneyEast.getTrainId()).isEqualTo(DEFAULT_TRAIN_ID);
        assertThat(testJourneyEast.getCompanyId()).isEqualTo(DEFAULT_COMPANY_ID);
        assertThat(testJourneyEast.getDepartureRailwayStationId()).isEqualTo(DEFAULT_DEPARTURE_RAILWAY_STATION_ID);
        assertThat(testJourneyEast.getArivalRailwayStationId()).isEqualTo(DEFAULT_ARIVAL_RAILWAY_STATION_ID);
    }

    @Test
    @Transactional
    void createJourneyEastWithExistingId() throws Exception {
        // Create the JourneyEast with an existing ID
        journeyEast.setId(1L);
        JourneyEastDTO journeyEastDTO = journeyEastMapper.toDto(journeyEast);

        int databaseSizeBeforeCreate = journeyEastRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restJourneyEastMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(journeyEastDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the JourneyEast in the database
        List<JourneyEast> journeyEastList = journeyEastRepository.findAll();
        assertThat(journeyEastList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllJourneyEasts() throws Exception {
        // Initialize the database
        journeyEastRepository.saveAndFlush(journeyEast);

        // Get all the journeyEastList
        restJourneyEastMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(journeyEast.getId().intValue())))
            .andExpect(jsonPath("$.[*].distance").value(hasItem(DEFAULT_DISTANCE.doubleValue())))
            .andExpect(jsonPath("$.[*].journeyDuration").value(hasItem(DEFAULT_JOURNEY_DURATION.doubleValue())))
            .andExpect(jsonPath("$.[*].actualDepartureTime").value(hasItem(sameInstant(DEFAULT_ACTUAL_DEPARTURE_TIME))))
            .andExpect(jsonPath("$.[*].plannedDepartureTime").value(hasItem(sameInstant(DEFAULT_PLANNED_DEPARTURE_TIME))))
            .andExpect(jsonPath("$.[*].actualArrivalTime").value(hasItem(sameInstant(DEFAULT_ACTUAL_ARRIVAL_TIME))))
            .andExpect(jsonPath("$.[*].plannedArrivalTime").value(hasItem(sameInstant(DEFAULT_PLANNED_ARRIVAL_TIME))))
            .andExpect(jsonPath("$.[*].ticketPrice").value(hasItem(DEFAULT_TICKET_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].numberOfStops").value(hasItem(DEFAULT_NUMBER_OF_STOPS)))
            .andExpect(jsonPath("$.[*].timeOfStops").value(hasItem(DEFAULT_TIME_OF_STOPS.doubleValue())))
            .andExpect(jsonPath("$.[*].minutesLate").value(hasItem(DEFAULT_MINUTES_LATE.doubleValue())))
            .andExpect(jsonPath("$.[*].journeyStatusId").value(hasItem(DEFAULT_JOURNEY_STATUS_ID.intValue())))
            .andExpect(jsonPath("$.[*].trainId").value(hasItem(DEFAULT_TRAIN_ID.intValue())))
            .andExpect(jsonPath("$.[*].companyId").value(hasItem(DEFAULT_COMPANY_ID.intValue())))
            .andExpect(jsonPath("$.[*].departureRailwayStationId").value(hasItem(DEFAULT_DEPARTURE_RAILWAY_STATION_ID.intValue())))
            .andExpect(jsonPath("$.[*].arivalRailwayStationId").value(hasItem(DEFAULT_ARIVAL_RAILWAY_STATION_ID.intValue())));
    }

    @Test
    @Transactional
    void getJourneyEast() throws Exception {
        // Initialize the database
        journeyEastRepository.saveAndFlush(journeyEast);

        // Get the journeyEast
        restJourneyEastMockMvc
            .perform(get(ENTITY_API_URL_ID, journeyEast.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(journeyEast.getId().intValue()))
            .andExpect(jsonPath("$.distance").value(DEFAULT_DISTANCE.doubleValue()))
            .andExpect(jsonPath("$.journeyDuration").value(DEFAULT_JOURNEY_DURATION.doubleValue()))
            .andExpect(jsonPath("$.actualDepartureTime").value(sameInstant(DEFAULT_ACTUAL_DEPARTURE_TIME)))
            .andExpect(jsonPath("$.plannedDepartureTime").value(sameInstant(DEFAULT_PLANNED_DEPARTURE_TIME)))
            .andExpect(jsonPath("$.actualArrivalTime").value(sameInstant(DEFAULT_ACTUAL_ARRIVAL_TIME)))
            .andExpect(jsonPath("$.plannedArrivalTime").value(sameInstant(DEFAULT_PLANNED_ARRIVAL_TIME)))
            .andExpect(jsonPath("$.ticketPrice").value(DEFAULT_TICKET_PRICE.doubleValue()))
            .andExpect(jsonPath("$.numberOfStops").value(DEFAULT_NUMBER_OF_STOPS))
            .andExpect(jsonPath("$.timeOfStops").value(DEFAULT_TIME_OF_STOPS.doubleValue()))
            .andExpect(jsonPath("$.minutesLate").value(DEFAULT_MINUTES_LATE.doubleValue()))
            .andExpect(jsonPath("$.journeyStatusId").value(DEFAULT_JOURNEY_STATUS_ID.intValue()))
            .andExpect(jsonPath("$.trainId").value(DEFAULT_TRAIN_ID.intValue()))
            .andExpect(jsonPath("$.companyId").value(DEFAULT_COMPANY_ID.intValue()))
            .andExpect(jsonPath("$.departureRailwayStationId").value(DEFAULT_DEPARTURE_RAILWAY_STATION_ID.intValue()))
            .andExpect(jsonPath("$.arivalRailwayStationId").value(DEFAULT_ARIVAL_RAILWAY_STATION_ID.intValue()));
    }

    @Test
    @Transactional
    void getNonExistingJourneyEast() throws Exception {
        // Get the journeyEast
        restJourneyEastMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingJourneyEast() throws Exception {
        // Initialize the database
        journeyEastRepository.saveAndFlush(journeyEast);

        int databaseSizeBeforeUpdate = journeyEastRepository.findAll().size();

        // Update the journeyEast
        JourneyEast updatedJourneyEast = journeyEastRepository.findById(journeyEast.getId()).get();
        // Disconnect from session so that the updates on updatedJourneyEast are not directly saved in db
        em.detach(updatedJourneyEast);
        updatedJourneyEast
            .distance(UPDATED_DISTANCE)
            .journeyDuration(UPDATED_JOURNEY_DURATION)
            .actualDepartureTime(UPDATED_ACTUAL_DEPARTURE_TIME)
            .plannedDepartureTime(UPDATED_PLANNED_DEPARTURE_TIME)
            .actualArrivalTime(UPDATED_ACTUAL_ARRIVAL_TIME)
            .plannedArrivalTime(UPDATED_PLANNED_ARRIVAL_TIME)
            .ticketPrice(UPDATED_TICKET_PRICE)
            .numberOfStops(UPDATED_NUMBER_OF_STOPS)
            .timeOfStops(UPDATED_TIME_OF_STOPS)
            .minutesLate(UPDATED_MINUTES_LATE)
            .journeyStatusId(UPDATED_JOURNEY_STATUS_ID)
            .trainId(UPDATED_TRAIN_ID)
            .companyId(UPDATED_COMPANY_ID)
            .departureRailwayStationId(UPDATED_DEPARTURE_RAILWAY_STATION_ID)
            .arivalRailwayStationId(UPDATED_ARIVAL_RAILWAY_STATION_ID);
        JourneyEastDTO journeyEastDTO = journeyEastMapper.toDto(updatedJourneyEast);

        restJourneyEastMockMvc
            .perform(
                put(ENTITY_API_URL_ID, journeyEastDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(journeyEastDTO))
            )
            .andExpect(status().isOk());

        // Validate the JourneyEast in the database
        List<JourneyEast> journeyEastList = journeyEastRepository.findAll();
        assertThat(journeyEastList).hasSize(databaseSizeBeforeUpdate);
        JourneyEast testJourneyEast = journeyEastList.get(journeyEastList.size() - 1);
        assertThat(testJourneyEast.getDistance()).isEqualTo(UPDATED_DISTANCE);
        assertThat(testJourneyEast.getJourneyDuration()).isEqualTo(UPDATED_JOURNEY_DURATION);
        assertThat(testJourneyEast.getActualDepartureTime()).isEqualTo(UPDATED_ACTUAL_DEPARTURE_TIME);
        assertThat(testJourneyEast.getPlannedDepartureTime()).isEqualTo(UPDATED_PLANNED_DEPARTURE_TIME);
        assertThat(testJourneyEast.getActualArrivalTime()).isEqualTo(UPDATED_ACTUAL_ARRIVAL_TIME);
        assertThat(testJourneyEast.getPlannedArrivalTime()).isEqualTo(UPDATED_PLANNED_ARRIVAL_TIME);
        assertThat(testJourneyEast.getTicketPrice()).isEqualTo(UPDATED_TICKET_PRICE);
        assertThat(testJourneyEast.getNumberOfStops()).isEqualTo(UPDATED_NUMBER_OF_STOPS);
        assertThat(testJourneyEast.getTimeOfStops()).isEqualTo(UPDATED_TIME_OF_STOPS);
        assertThat(testJourneyEast.getMinutesLate()).isEqualTo(UPDATED_MINUTES_LATE);
        assertThat(testJourneyEast.getJourneyStatusId()).isEqualTo(UPDATED_JOURNEY_STATUS_ID);
        assertThat(testJourneyEast.getTrainId()).isEqualTo(UPDATED_TRAIN_ID);
        assertThat(testJourneyEast.getCompanyId()).isEqualTo(UPDATED_COMPANY_ID);
        assertThat(testJourneyEast.getDepartureRailwayStationId()).isEqualTo(UPDATED_DEPARTURE_RAILWAY_STATION_ID);
        assertThat(testJourneyEast.getArivalRailwayStationId()).isEqualTo(UPDATED_ARIVAL_RAILWAY_STATION_ID);
    }

    @Test
    @Transactional
    void putNonExistingJourneyEast() throws Exception {
        int databaseSizeBeforeUpdate = journeyEastRepository.findAll().size();
        journeyEast.setId(count.incrementAndGet());

        // Create the JourneyEast
        JourneyEastDTO journeyEastDTO = journeyEastMapper.toDto(journeyEast);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restJourneyEastMockMvc
            .perform(
                put(ENTITY_API_URL_ID, journeyEastDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(journeyEastDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the JourneyEast in the database
        List<JourneyEast> journeyEastList = journeyEastRepository.findAll();
        assertThat(journeyEastList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchJourneyEast() throws Exception {
        int databaseSizeBeforeUpdate = journeyEastRepository.findAll().size();
        journeyEast.setId(count.incrementAndGet());

        // Create the JourneyEast
        JourneyEastDTO journeyEastDTO = journeyEastMapper.toDto(journeyEast);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restJourneyEastMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(journeyEastDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the JourneyEast in the database
        List<JourneyEast> journeyEastList = journeyEastRepository.findAll();
        assertThat(journeyEastList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamJourneyEast() throws Exception {
        int databaseSizeBeforeUpdate = journeyEastRepository.findAll().size();
        journeyEast.setId(count.incrementAndGet());

        // Create the JourneyEast
        JourneyEastDTO journeyEastDTO = journeyEastMapper.toDto(journeyEast);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restJourneyEastMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(journeyEastDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the JourneyEast in the database
        List<JourneyEast> journeyEastList = journeyEastRepository.findAll();
        assertThat(journeyEastList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateJourneyEastWithPatch() throws Exception {
        // Initialize the database
        journeyEastRepository.saveAndFlush(journeyEast);

        int databaseSizeBeforeUpdate = journeyEastRepository.findAll().size();

        // Update the journeyEast using partial update
        JourneyEast partialUpdatedJourneyEast = new JourneyEast();
        partialUpdatedJourneyEast.setId(journeyEast.getId());

        partialUpdatedJourneyEast
            .distance(UPDATED_DISTANCE)
            .plannedDepartureTime(UPDATED_PLANNED_DEPARTURE_TIME)
            .plannedArrivalTime(UPDATED_PLANNED_ARRIVAL_TIME)
            .numberOfStops(UPDATED_NUMBER_OF_STOPS)
            .minutesLate(UPDATED_MINUTES_LATE)
            .journeyStatusId(UPDATED_JOURNEY_STATUS_ID)
            .companyId(UPDATED_COMPANY_ID)
            .departureRailwayStationId(UPDATED_DEPARTURE_RAILWAY_STATION_ID)
            .arivalRailwayStationId(UPDATED_ARIVAL_RAILWAY_STATION_ID);

        restJourneyEastMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedJourneyEast.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedJourneyEast))
            )
            .andExpect(status().isOk());

        // Validate the JourneyEast in the database
        List<JourneyEast> journeyEastList = journeyEastRepository.findAll();
        assertThat(journeyEastList).hasSize(databaseSizeBeforeUpdate);
        JourneyEast testJourneyEast = journeyEastList.get(journeyEastList.size() - 1);
        assertThat(testJourneyEast.getDistance()).isEqualTo(UPDATED_DISTANCE);
        assertThat(testJourneyEast.getJourneyDuration()).isEqualTo(DEFAULT_JOURNEY_DURATION);
        assertThat(testJourneyEast.getActualDepartureTime()).isEqualTo(DEFAULT_ACTUAL_DEPARTURE_TIME);
        assertThat(testJourneyEast.getPlannedDepartureTime()).isEqualTo(UPDATED_PLANNED_DEPARTURE_TIME);
        assertThat(testJourneyEast.getActualArrivalTime()).isEqualTo(DEFAULT_ACTUAL_ARRIVAL_TIME);
        assertThat(testJourneyEast.getPlannedArrivalTime()).isEqualTo(UPDATED_PLANNED_ARRIVAL_TIME);
        assertThat(testJourneyEast.getTicketPrice()).isEqualTo(DEFAULT_TICKET_PRICE);
        assertThat(testJourneyEast.getNumberOfStops()).isEqualTo(UPDATED_NUMBER_OF_STOPS);
        assertThat(testJourneyEast.getTimeOfStops()).isEqualTo(DEFAULT_TIME_OF_STOPS);
        assertThat(testJourneyEast.getMinutesLate()).isEqualTo(UPDATED_MINUTES_LATE);
        assertThat(testJourneyEast.getJourneyStatusId()).isEqualTo(UPDATED_JOURNEY_STATUS_ID);
        assertThat(testJourneyEast.getTrainId()).isEqualTo(DEFAULT_TRAIN_ID);
        assertThat(testJourneyEast.getCompanyId()).isEqualTo(UPDATED_COMPANY_ID);
        assertThat(testJourneyEast.getDepartureRailwayStationId()).isEqualTo(UPDATED_DEPARTURE_RAILWAY_STATION_ID);
        assertThat(testJourneyEast.getArivalRailwayStationId()).isEqualTo(UPDATED_ARIVAL_RAILWAY_STATION_ID);
    }

    @Test
    @Transactional
    void fullUpdateJourneyEastWithPatch() throws Exception {
        // Initialize the database
        journeyEastRepository.saveAndFlush(journeyEast);

        int databaseSizeBeforeUpdate = journeyEastRepository.findAll().size();

        // Update the journeyEast using partial update
        JourneyEast partialUpdatedJourneyEast = new JourneyEast();
        partialUpdatedJourneyEast.setId(journeyEast.getId());

        partialUpdatedJourneyEast
            .distance(UPDATED_DISTANCE)
            .journeyDuration(UPDATED_JOURNEY_DURATION)
            .actualDepartureTime(UPDATED_ACTUAL_DEPARTURE_TIME)
            .plannedDepartureTime(UPDATED_PLANNED_DEPARTURE_TIME)
            .actualArrivalTime(UPDATED_ACTUAL_ARRIVAL_TIME)
            .plannedArrivalTime(UPDATED_PLANNED_ARRIVAL_TIME)
            .ticketPrice(UPDATED_TICKET_PRICE)
            .numberOfStops(UPDATED_NUMBER_OF_STOPS)
            .timeOfStops(UPDATED_TIME_OF_STOPS)
            .minutesLate(UPDATED_MINUTES_LATE)
            .journeyStatusId(UPDATED_JOURNEY_STATUS_ID)
            .trainId(UPDATED_TRAIN_ID)
            .companyId(UPDATED_COMPANY_ID)
            .departureRailwayStationId(UPDATED_DEPARTURE_RAILWAY_STATION_ID)
            .arivalRailwayStationId(UPDATED_ARIVAL_RAILWAY_STATION_ID);

        restJourneyEastMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedJourneyEast.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedJourneyEast))
            )
            .andExpect(status().isOk());

        // Validate the JourneyEast in the database
        List<JourneyEast> journeyEastList = journeyEastRepository.findAll();
        assertThat(journeyEastList).hasSize(databaseSizeBeforeUpdate);
        JourneyEast testJourneyEast = journeyEastList.get(journeyEastList.size() - 1);
        assertThat(testJourneyEast.getDistance()).isEqualTo(UPDATED_DISTANCE);
        assertThat(testJourneyEast.getJourneyDuration()).isEqualTo(UPDATED_JOURNEY_DURATION);
        assertThat(testJourneyEast.getActualDepartureTime()).isEqualTo(UPDATED_ACTUAL_DEPARTURE_TIME);
        assertThat(testJourneyEast.getPlannedDepartureTime()).isEqualTo(UPDATED_PLANNED_DEPARTURE_TIME);
        assertThat(testJourneyEast.getActualArrivalTime()).isEqualTo(UPDATED_ACTUAL_ARRIVAL_TIME);
        assertThat(testJourneyEast.getPlannedArrivalTime()).isEqualTo(UPDATED_PLANNED_ARRIVAL_TIME);
        assertThat(testJourneyEast.getTicketPrice()).isEqualTo(UPDATED_TICKET_PRICE);
        assertThat(testJourneyEast.getNumberOfStops()).isEqualTo(UPDATED_NUMBER_OF_STOPS);
        assertThat(testJourneyEast.getTimeOfStops()).isEqualTo(UPDATED_TIME_OF_STOPS);
        assertThat(testJourneyEast.getMinutesLate()).isEqualTo(UPDATED_MINUTES_LATE);
        assertThat(testJourneyEast.getJourneyStatusId()).isEqualTo(UPDATED_JOURNEY_STATUS_ID);
        assertThat(testJourneyEast.getTrainId()).isEqualTo(UPDATED_TRAIN_ID);
        assertThat(testJourneyEast.getCompanyId()).isEqualTo(UPDATED_COMPANY_ID);
        assertThat(testJourneyEast.getDepartureRailwayStationId()).isEqualTo(UPDATED_DEPARTURE_RAILWAY_STATION_ID);
        assertThat(testJourneyEast.getArivalRailwayStationId()).isEqualTo(UPDATED_ARIVAL_RAILWAY_STATION_ID);
    }

    @Test
    @Transactional
    void patchNonExistingJourneyEast() throws Exception {
        int databaseSizeBeforeUpdate = journeyEastRepository.findAll().size();
        journeyEast.setId(count.incrementAndGet());

        // Create the JourneyEast
        JourneyEastDTO journeyEastDTO = journeyEastMapper.toDto(journeyEast);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restJourneyEastMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, journeyEastDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(journeyEastDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the JourneyEast in the database
        List<JourneyEast> journeyEastList = journeyEastRepository.findAll();
        assertThat(journeyEastList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchJourneyEast() throws Exception {
        int databaseSizeBeforeUpdate = journeyEastRepository.findAll().size();
        journeyEast.setId(count.incrementAndGet());

        // Create the JourneyEast
        JourneyEastDTO journeyEastDTO = journeyEastMapper.toDto(journeyEast);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restJourneyEastMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(journeyEastDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the JourneyEast in the database
        List<JourneyEast> journeyEastList = journeyEastRepository.findAll();
        assertThat(journeyEastList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamJourneyEast() throws Exception {
        int databaseSizeBeforeUpdate = journeyEastRepository.findAll().size();
        journeyEast.setId(count.incrementAndGet());

        // Create the JourneyEast
        JourneyEastDTO journeyEastDTO = journeyEastMapper.toDto(journeyEast);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restJourneyEastMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(journeyEastDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the JourneyEast in the database
        List<JourneyEast> journeyEastList = journeyEastRepository.findAll();
        assertThat(journeyEastList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteJourneyEast() throws Exception {
        // Initialize the database
        journeyEastRepository.saveAndFlush(journeyEast);

        int databaseSizeBeforeDelete = journeyEastRepository.findAll().size();

        // Delete the journeyEast
        restJourneyEastMockMvc
            .perform(delete(ENTITY_API_URL_ID, journeyEast.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<JourneyEast> journeyEastList = journeyEastRepository.findAll();
        assertThat(journeyEastList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
