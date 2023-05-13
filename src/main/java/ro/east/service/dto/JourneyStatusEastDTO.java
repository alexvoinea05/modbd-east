package ro.east.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link ro.east.domain.JourneyStatusEast} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class JourneyStatusEastDTO implements Serializable {

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
        if (!(o instanceof JourneyStatusEastDTO)) {
            return false;
        }

        JourneyStatusEastDTO journeyStatusEastDTO = (JourneyStatusEastDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, journeyStatusEastDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "JourneyStatusEastDTO{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
