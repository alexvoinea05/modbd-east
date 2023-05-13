package ro.east.service.dto;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A DTO for the {@link ro.east.domain.TicketEast} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TicketEastDTO implements Serializable {

    private Long id;

    private Double finalPrice;

    private Integer quantity;

    private ZonedDateTime time;

    private Long appUserId;

    private Long journeyId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(Double finalPrice) {
        this.finalPrice = finalPrice;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public ZonedDateTime getTime() {
        return time;
    }

    public void setTime(ZonedDateTime time) {
        this.time = time;
    }

    public Long getAppUserId() {
        return appUserId;
    }

    public void setAppUserId(Long appUserId) {
        this.appUserId = appUserId;
    }

    public Long getJourneyId() {
        return journeyId;
    }

    public void setJourneyId(Long journeyId) {
        this.journeyId = journeyId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TicketEastDTO)) {
            return false;
        }

        TicketEastDTO ticketEastDTO = (TicketEastDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, ticketEastDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TicketEastDTO{" +
            "id=" + getId() +
            ", finalPrice=" + getFinalPrice() +
            ", quantity=" + getQuantity() +
            ", time='" + getTime() + "'" +
            ", appUserId=" + getAppUserId() +
            ", journeyId=" + getJourneyId() +
            "}";
    }
}
