package ro.east.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link ro.east.domain.LicenseEast} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class LicenseEastDTO implements Serializable {

    private Long id;

    private Long licenseNumber;

    private String licenseDescription;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getLicenseNumber() {
        return licenseNumber;
    }

    public void setLicenseNumber(Long licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

    public String getLicenseDescription() {
        return licenseDescription;
    }

    public void setLicenseDescription(String licenseDescription) {
        this.licenseDescription = licenseDescription;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LicenseEastDTO)) {
            return false;
        }

        LicenseEastDTO licenseEastDTO = (LicenseEastDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, licenseEastDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LicenseEastDTO{" +
            "id=" + getId() +
            ", licenseNumber=" + getLicenseNumber() +
            ", licenseDescription='" + getLicenseDescription() + "'" +
            "}";
    }
}
