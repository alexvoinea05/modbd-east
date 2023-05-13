package ro.east.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link ro.east.domain.RailwayTypeEast} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class RailwayTypeEastDTO implements Serializable {

    private Long id;

    private String code;

    private String description;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RailwayTypeEastDTO)) {
            return false;
        }

        RailwayTypeEastDTO railwayTypeEastDTO = (RailwayTypeEastDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, railwayTypeEastDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RailwayTypeEastDTO{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
