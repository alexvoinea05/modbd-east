package ro.east.service.dto;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A DTO for the {@link ro.east.domain.JourneyEast} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class JourneyEastDTO implements Serializable {

    private Long id;

    private Double distance;

    private Double journeyDuration;

    private ZonedDateTime actualDepartureTime;

    private ZonedDateTime plannedDepartureTime;

    private ZonedDateTime actualArrivalTime;

    private ZonedDateTime plannedArrivalTime;

    private Double ticketPrice;

    private Integer numberOfStops;

    private Double timeOfStops;

    private Double minutesLate;

    private Long journeyStatusId;

    private Long trainId;

    private Long companyId;

    private Long departureRailwayStationId;

    private Long arivalRailwayStationId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public Double getJourneyDuration() {
        return journeyDuration;
    }

    public void setJourneyDuration(Double journeyDuration) {
        this.journeyDuration = journeyDuration;
    }

    public ZonedDateTime getActualDepartureTime() {
        return actualDepartureTime;
    }

    public void setActualDepartureTime(ZonedDateTime actualDepartureTime) {
        this.actualDepartureTime = actualDepartureTime;
    }

    public ZonedDateTime getPlannedDepartureTime() {
        return plannedDepartureTime;
    }

    public void setPlannedDepartureTime(ZonedDateTime plannedDepartureTime) {
        this.plannedDepartureTime = plannedDepartureTime;
    }

    public ZonedDateTime getActualArrivalTime() {
        return actualArrivalTime;
    }

    public void setActualArrivalTime(ZonedDateTime actualArrivalTime) {
        this.actualArrivalTime = actualArrivalTime;
    }

    public ZonedDateTime getPlannedArrivalTime() {
        return plannedArrivalTime;
    }

    public void setPlannedArrivalTime(ZonedDateTime plannedArrivalTime) {
        this.plannedArrivalTime = plannedArrivalTime;
    }

    public Double getTicketPrice() {
        return ticketPrice;
    }

    public void setTicketPrice(Double ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    public Integer getNumberOfStops() {
        return numberOfStops;
    }

    public void setNumberOfStops(Integer numberOfStops) {
        this.numberOfStops = numberOfStops;
    }

    public Double getTimeOfStops() {
        return timeOfStops;
    }

    public void setTimeOfStops(Double timeOfStops) {
        this.timeOfStops = timeOfStops;
    }

    public Double getMinutesLate() {
        return minutesLate;
    }

    public void setMinutesLate(Double minutesLate) {
        this.minutesLate = minutesLate;
    }

    public Long getJourneyStatusId() {
        return journeyStatusId;
    }

    public void setJourneyStatusId(Long journeyStatusId) {
        this.journeyStatusId = journeyStatusId;
    }

    public Long getTrainId() {
        return trainId;
    }

    public void setTrainId(Long trainId) {
        this.trainId = trainId;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public Long getDepartureRailwayStationId() {
        return departureRailwayStationId;
    }

    public void setDepartureRailwayStationId(Long departureRailwayStationId) {
        this.departureRailwayStationId = departureRailwayStationId;
    }

    public Long getArivalRailwayStationId() {
        return arivalRailwayStationId;
    }

    public void setArivalRailwayStationId(Long arivalRailwayStationId) {
        this.arivalRailwayStationId = arivalRailwayStationId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof JourneyEastDTO)) {
            return false;
        }

        JourneyEastDTO journeyEastDTO = (JourneyEastDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, journeyEastDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "JourneyEastDTO{" +
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
