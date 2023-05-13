package ro.east.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link ro.east.domain.RailwayStationEast} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class RailwayStationEastDTO implements Serializable {

    private Long id;

    private String railwayStationName;

    private Long railwayTypeId;

    private Long addressId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRailwayStationName() {
        return railwayStationName;
    }

    public void setRailwayStationName(String railwayStationName) {
        this.railwayStationName = railwayStationName;
    }

    public Long getRailwayTypeId() {
        return railwayTypeId;
    }

    public void setRailwayTypeId(Long railwayTypeId) {
        this.railwayTypeId = railwayTypeId;
    }

    public Long getAddressId() {
        return addressId;
    }

    public void setAddressId(Long addressId) {
        this.addressId = addressId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RailwayStationEastDTO)) {
            return false;
        }

        RailwayStationEastDTO railwayStationEastDTO = (RailwayStationEastDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, railwayStationEastDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RailwayStationEastDTO{" +
            "id=" + getId() +
            ", railwayStationName='" + getRailwayStationName() + "'" +
            ", railwayTypeId=" + getRailwayTypeId() +
            ", addressId=" + getAddressId() +
            "}";
    }
}
