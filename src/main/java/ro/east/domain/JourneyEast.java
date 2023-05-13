package ro.east.domain;

import java.io.Serializable;
import java.time.ZonedDateTime;
import javax.persistence.*;

/**
 * A JourneyEast.
 */
@Entity
@Table(name = "journey_east")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class JourneyEast implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "distance")
    private Double distance;

    @Column(name = "journey_duration")
    private Double journeyDuration;

    @Column(name = "actual_departure_time")
    private ZonedDateTime actualDepartureTime;

    @Column(name = "planned_departure_time")
    private ZonedDateTime plannedDepartureTime;

    @Column(name = "actual_arrival_time")
    private ZonedDateTime actualArrivalTime;

    @Column(name = "planned_arrival_time")
    private ZonedDateTime plannedArrivalTime;

    @Column(name = "ticket_price")
    private Double ticketPrice;

    @Column(name = "number_of_stops")
    private Integer numberOfStops;

    @Column(name = "time_of_stops")
    private Double timeOfStops;

    @Column(name = "minutes_late")
    private Double minutesLate;

    @Column(name = "journey_status_id")
    private Long journeyStatusId;

    @Column(name = "train_id")
    private Long trainId;

    @Column(name = "company_id")
    private Long companyId;

    @Column(name = "departure_railway_station_id")
    private Long departureRailwayStationId;

    @Column(name = "arival_railway_station_id")
    private Long arivalRailwayStationId;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public JourneyEast id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getDistance() {
        return this.distance;
    }

    public JourneyEast distance(Double distance) {
        this.setDistance(distance);
        return this;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public Double getJourneyDuration() {
        return this.journeyDuration;
    }

    public JourneyEast journeyDuration(Double journeyDuration) {
        this.setJourneyDuration(journeyDuration);
        return this;
    }

    public void setJourneyDuration(Double journeyDuration) {
        this.journeyDuration = journeyDuration;
    }

    public ZonedDateTime getActualDepartureTime() {
        return this.actualDepartureTime;
    }

    public JourneyEast actualDepartureTime(ZonedDateTime actualDepartureTime) {
        this.setActualDepartureTime(actualDepartureTime);
        return this;
    }

    public void setActualDepartureTime(ZonedDateTime actualDepartureTime) {
        this.actualDepartureTime = actualDepartureTime;
    }

    public ZonedDateTime getPlannedDepartureTime() {
        return this.plannedDepartureTime;
    }

    public JourneyEast plannedDepartureTime(ZonedDateTime plannedDepartureTime) {
        this.setPlannedDepartureTime(plannedDepartureTime);
        return this;
    }

    public void setPlannedDepartureTime(ZonedDateTime plannedDepartureTime) {
        this.plannedDepartureTime = plannedDepartureTime;
    }

    public ZonedDateTime getActualArrivalTime() {
        return this.actualArrivalTime;
    }

    public JourneyEast actualArrivalTime(ZonedDateTime actualArrivalTime) {
        this.setActualArrivalTime(actualArrivalTime);
        return this;
    }

    public void setActualArrivalTime(ZonedDateTime actualArrivalTime) {
        this.actualArrivalTime = actualArrivalTime;
    }

    public ZonedDateTime getPlannedArrivalTime() {
        return this.plannedArrivalTime;
    }

    public JourneyEast plannedArrivalTime(ZonedDateTime plannedArrivalTime) {
        this.setPlannedArrivalTime(plannedArrivalTime);
        return this;
    }

    public void setPlannedArrivalTime(ZonedDateTime plannedArrivalTime) {
        this.plannedArrivalTime = plannedArrivalTime;
    }

    public Double getTicketPrice() {
        return this.ticketPrice;
    }

    public JourneyEast ticketPrice(Double ticketPrice) {
        this.setTicketPrice(ticketPrice);
        return this;
    }

    public void setTicketPrice(Double ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    public Integer getNumberOfStops() {
        return this.numberOfStops;
    }

    public JourneyEast numberOfStops(Integer numberOfStops) {
        this.setNumberOfStops(numberOfStops);
        return this;
    }

    public void setNumberOfStops(Integer numberOfStops) {
        this.numberOfStops = numberOfStops;
    }

    public Double getTimeOfStops() {
        return this.timeOfStops;
    }

    public JourneyEast timeOfStops(Double timeOfStops) {
        this.setTimeOfStops(timeOfStops);
        return this;
    }

    public void setTimeOfStops(Double timeOfStops) {
        this.timeOfStops = timeOfStops;
    }

    public Double getMinutesLate() {
        return this.minutesLate;
    }

    public JourneyEast minutesLate(Double minutesLate) {
        this.setMinutesLate(minutesLate);
        return this;
    }

    public void setMinutesLate(Double minutesLate) {
        this.minutesLate = minutesLate;
    }

    public Long getJourneyStatusId() {
        return this.journeyStatusId;
    }

    public JourneyEast journeyStatusId(Long journeyStatusId) {
        this.setJourneyStatusId(journeyStatusId);
        return this;
    }

    public void setJourneyStatusId(Long journeyStatusId) {
        this.journeyStatusId = journeyStatusId;
    }

    public Long getTrainId() {
        return this.trainId;
    }

    public JourneyEast trainId(Long trainId) {
        this.setTrainId(trainId);
        return this;
    }

    public void setTrainId(Long trainId) {
        this.trainId = trainId;
    }

    public Long getCompanyId() {
        return this.companyId;
    }

    public JourneyEast companyId(Long companyId) {
        this.setCompanyId(companyId);
        return this;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public Long getDepartureRailwayStationId() {
        return this.departureRailwayStationId;
    }

    public JourneyEast departureRailwayStationId(Long departureRailwayStationId) {
        this.setDepartureRailwayStationId(departureRailwayStationId);
        return this;
    }

    public void setDepartureRailwayStationId(Long departureRailwayStationId) {
        this.departureRailwayStationId = departureRailwayStationId;
    }

    public Long getArivalRailwayStationId() {
        return this.arivalRailwayStationId;
    }

    public JourneyEast arivalRailwayStationId(Long arivalRailwayStationId) {
        this.setArivalRailwayStationId(arivalRailwayStationId);
        return this;
    }

    public void setArivalRailwayStationId(Long arivalRailwayStationId) {
        this.arivalRailwayStationId = arivalRailwayStationId;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof JourneyEast)) {
            return false;
        }
        return id != null && id.equals(((JourneyEast) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "JourneyEast{" +
            "id=" + getId() +
            ", distance=" + getDistance() +
            ", journeyDuration=" + getJourneyDuration() +
            ", actualDepartureTime='" + getActualDepartureTime() + "'" +
            ", plannedDepartureTime='" + getPlannedDepartureTime() + "'" +
            ", actualArrivalTime='" + getActualArrivalTime() + "'" +
            ", plannedArrivalTime='" + getPlannedArrivalTime() + "'" +
            ", ticketPrice=" + getTicketPrice() +
            ", numberOfStops=" + getNumberOfStops() +
            ", timeOfStops=" + getTimeOfStops() +
            ", minutesLate=" + getMinutesLate() +
            ", journeyStatusId=" + getJourneyStatusId() +
            ", trainId=" + getTrainId() +
            ", companyId=" + getCompanyId() +
            ", departureRailwayStationId=" + getDepartureRailwayStationId() +
            ", arivalRailwayStationId=" + getArivalRailwayStationId() +
            "}";
    }
}
