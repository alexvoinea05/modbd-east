package ro.east.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link ro.east.domain.CompanyLicenseEast} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CompanyLicenseEastDTO implements Serializable {

    private Long id;

    private CompanyEastDTO idCompany;

    private LicenseEastDTO idLicense;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CompanyEastDTO getIdCompany() {
        return idCompany;
    }

    public void setIdCompany(CompanyEastDTO idCompany) {
        this.idCompany = idCompany;
    }

    public LicenseEastDTO getIdLicense() {
        return idLicense;
    }

    public void setIdLicense(LicenseEastDTO idLicense) {
        this.idLicense = idLicense;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CompanyLicenseEastDTO)) {
            return false;
        }

        CompanyLicenseEastDTO companyLicenseEastDTO = (CompanyLicenseEastDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, companyLicenseEastDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CompanyLicenseEastDTO{" +
            "id=" + getId() +
            ", idCompany=" + getIdCompany() +
            ", idLicense=" + getIdLicense() +
            "}";
    }
}
