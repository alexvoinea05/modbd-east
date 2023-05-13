package ro.east.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;

/**
 * A CompanyLicenseEast.
 */
@Entity
@Table(name = "company_license_east")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CompanyLicenseEast implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JsonIgnoreProperties(value = { "companyLicenseEasts" }, allowSetters = true)
    private CompanyEast idCompany;

    @ManyToOne
    @JsonIgnoreProperties(value = { "companyLicenseEasts" }, allowSetters = true)
    private LicenseEast idLicense;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public CompanyLicenseEast id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CompanyEast getIdCompany() {
        return this.idCompany;
    }

    public void setIdCompany(CompanyEast companyEast) {
        this.idCompany = companyEast;
    }

    public CompanyLicenseEast idCompany(CompanyEast companyEast) {
        this.setIdCompany(companyEast);
        return this;
    }

    public LicenseEast getIdLicense() {
        return this.idLicense;
    }

    public void setIdLicense(LicenseEast licenseEast) {
        this.idLicense = licenseEast;
    }

    public CompanyLicenseEast idLicense(LicenseEast licenseEast) {
        this.setIdLicense(licenseEast);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CompanyLicenseEast)) {
            return false;
        }
        return id != null && id.equals(((CompanyLicenseEast) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CompanyLicenseEast{" +
            "id=" + getId() +
            "}";
    }
}
