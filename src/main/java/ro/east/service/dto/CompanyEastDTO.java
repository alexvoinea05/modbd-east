package ro.east.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link ro.east.domain.CompanyEast} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CompanyEastDTO implements Serializable {

    private Long id;

    private String name;

    private String identificationNumber;

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

    public String getIdentificationNumber() {
        return identificationNumber;
    }

    public void setIdentificationNumber(String identificationNumber) {
        this.identificationNumber = identificationNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CompanyEastDTO)) {
            return false;
        }

        CompanyEastDTO companyEastDTO = (CompanyEastDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, companyEastDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CompanyEastDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", identificationNumber='" + getIdentificationNumber() + "'" +
            "}";
    }
}
