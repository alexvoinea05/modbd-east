package ro.east.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link ro.east.domain.CityEast} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CityEastDTO implements Serializable {

    private Long id;

    private String name;

    private Long districtId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getDistrictId() {
        return districtId;
    }

    public void setDistrictId(Long districtId) {
        this.districtId = districtId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CityEastDTO)) {
            return false;
        }

        CityEastDTO cityEastDTO = (CityEastDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, cityEastDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CityEastDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", districtId=" + getDistrictId() +
            "}";
    }
}
