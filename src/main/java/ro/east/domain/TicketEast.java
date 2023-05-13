package ro.east.domain;

import java.io.Serializable;
import java.time.ZonedDateTime;
import javax.persistence.*;

/**
 * A TicketEast.
 */
@Entity
@Table(name = "ticket_east")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TicketEast implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "final_price")
    private Double finalPrice;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "jhi_time")
    private ZonedDateTime time;

    @Column(name = "app_user_id")
    private Long appUserId;

    @Column(name = "journey_id")
    private Long journeyId;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public TicketEast id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getFinalPrice() {
        return this.finalPrice;
    }

    public TicketEast finalPrice(Double finalPrice) {
        this.setFinalPrice(finalPrice);
        return this;
    }

    public void setFinalPrice(Double finalPrice) {
        this.finalPrice = finalPrice;
    }

    public Integer getQuantity() {
        return this.quantity;
    }

    public TicketEast quantity(Integer quantity) {
        this.setQuantity(quantity);
        return this;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public ZonedDateTime getTime() {
        return this.time;
    }

    public TicketEast time(ZonedDateTime time) {
        this.setTime(time);
        return this;
    }

    public void setTime(ZonedDateTime time) {
        this.time = time;
    }

    public Long getAppUserId() {
        return this.appUserId;
    }

    public TicketEast appUserId(Long appUserId) {
        this.setAppUserId(appUserId);
        return this;
    }

    public void setAppUserId(Long appUserId) {
        this.appUserId = appUserId;
    }

    public Long getJourneyId() {
        return this.journeyId;
    }

    public TicketEast journeyId(Long journeyId) {
        this.setJourneyId(journeyId);
        return this;
    }

    public void setJourneyId(Long journeyId) {
        this.journeyId = journeyId;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TicketEast)) {
            return false;
        }
        return id != null && id.equals(((TicketEast) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TicketEast{" +
            "id=" + getId() +
            ", finalPrice=" + getFinalPrice() +
            ", quantity=" + getQuantity() +
            ", time='" + getTime() + "'" +
            ", appUserId=" + getAppUserId() +
            ", journeyId=" + getJourneyId() +
            "}";
    }
}
