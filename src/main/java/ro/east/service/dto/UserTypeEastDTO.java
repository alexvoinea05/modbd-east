package ro.east.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link ro.east.domain.UserTypeEast} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class UserTypeEastDTO implements Serializable {

    private Long id;

    private String code;

    private Double discount;

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

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserTypeEastDTO)) {
            return false;
        }

        UserTypeEastDTO userTypeEastDTO = (UserTypeEastDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, userTypeEastDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UserTypeEastDTO{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", discount=" + getDiscount() +
            "}";
    }
}
