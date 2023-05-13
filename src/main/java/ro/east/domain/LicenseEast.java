package ro.east.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

/**
 * A LicenseEast.
 */
@Entity
@Table(name = "license_east")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class LicenseEast implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "license_number")
    private Long licenseNumber;

    @Column(name = "license_description")
    private String licenseDescription;

    @OneToMany(mappedBy = "idLicense")
    @JsonIgnoreProperties(value = { "idCompany", "idLicense" }, allowSetters = true)
    private Set<CompanyLicenseEast> companyLicenseEasts = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public LicenseEast id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getLicenseNumber() {
        return this.licenseNumber;
    }

    public LicenseEast licenseNumber(Long licenseNumber) {
        this.setLicenseNumber(licenseNumber);
        return this;
    }

    public void setLicenseNumber(Long licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

    public String getLicenseDescription() {
        return this.licenseDescription;
    }

    public LicenseEast licenseDescription(String licenseDescription) {
        this.setLicenseDescription(licenseDescription);
        return this;
    }

    public void setLicenseDescription(String licenseDescription) {
        this.licenseDescription = licenseDescription;
    }

    public Set<CompanyLicenseEast> getCompanyLicenseEasts() {
        return this.companyLicenseEasts;
    }

    public void setCompanyLicenseEasts(Set<CompanyLicenseEast> companyLicenseEasts) {
        if (this.companyLicenseEasts != null) {
            this.companyLicenseEasts.forEach(i -> i.setIdLicense(null));
        }
        if (companyLicenseEasts != null) {
            companyLicenseEasts.forEach(i -> i.setIdLicense(this));
        }
        this.companyLicenseEasts = companyLicenseEasts;
    }

    public LicenseEast companyLicenseEasts(Set<CompanyLicenseEast> companyLicenseEasts) {
        this.setCompanyLicenseEasts(companyLicenseEasts);
        return this;
    }

    public LicenseEast addCompanyLicenseEast(CompanyLicenseEast companyLicenseEast) {
        this.companyLicenseEasts.add(companyLicenseEast);
        companyLicenseEast.setIdLicense(this);
        return this;
    }

    public LicenseEast removeCompanyLicenseEast(CompanyLicenseEast companyLicenseEast) {
        this.companyLicenseEasts.remove(companyLicenseEast);
        companyLicenseEast.setIdLicense(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LicenseEast)) {
            return false;
        }
        return id != null && id.equals(((LicenseEast) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LicenseEast{" +
            "id=" + getId() +
            ", licenseNumber=" + getLicenseNumber() +
            ", licenseDescription='" + getLicenseDescription() + "'" +
            "}";
    }
}
