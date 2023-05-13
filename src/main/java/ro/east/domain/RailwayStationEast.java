package ro.east.domain;

import java.io.Serializable;
import javax.persistence.*;

/**
 * A RailwayStationEast.
 */
@Entity
@Table(name = "railway_station_east")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class RailwayStationEast implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "railway_station_name")
    private String railwayStationName;

    @Column(name = "railway_type_id")
    private Long railwayTypeId;

    @Column(name = "address_id")
    private Long addressId;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public RailwayStationEast id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRailwayStationName() {
        return this.railwayStationName;
    }

    public RailwayStationEast railwayStationName(String railwayStationName) {
        this.setRailwayStationName(railwayStationName);
        return this;
    }

    public void setRailwayStationName(String railwayStationName) {
        this.railwayStationName = railwayStationName;
    }

    public Long getRailwayTypeId() {
        return this.railwayTypeId;
    }

    public RailwayStationEast railwayTypeId(Long railwayTypeId) {
        this.setRailwayTypeId(railwayTypeId);
        return this;
    }

    public void setRailwayTypeId(Long railwayTypeId) {
        this.railwayTypeId = railwayTypeId;
    }

    public Long getAddressId() {
        return this.addressId;
    }

    public RailwayStationEast addressId(Long addressId) {
        this.setAddressId(addressId);
        return this;
    }

    public void setAddressId(Long addressId) {
        this.addressId = addressId;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RailwayStationEast)) {
            return false;
        }
        return id != null && id.equals(((RailwayStationEast) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RailwayStationEast{" +
            "id=" + getId() +
            ", railwayStationName='" + getRailwayStationName() + "'" +
            ", railwayTypeId=" + getRailwayTypeId() +
            ", addressId=" + getAddressId() +
            "}";
    }
}
