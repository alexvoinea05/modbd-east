package ro.east.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

/**
 * A CompanyEast.
 */
@Entity
@Table(name = "company_east")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CompanyEast implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "identification_number")
    private String identificationNumber;

    @OneToMany(mappedBy = "idCompany")
    @JsonIgnoreProperties(value = { "idCompany", "idLicense" }, allowSetters = true)
    private Set<CompanyLicenseEast> companyLicenseEasts = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public CompanyEast id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public CompanyEast name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdentificationNumber() {
        return this.identificationNumber;
    }

    public CompanyEast identificationNumber(String identificationNumber) {
        this.setIdentificationNumber(identificationNumber);
        return this;
    }

    public void setIdentificationNumber(String identificationNumber) {
        this.identificationNumber = identificationNumber;
    }

    public Set<CompanyLicenseEast> getCompanyLicenseEasts() {
        return this.companyLicenseEasts;
    }

    public void setCompanyLicenseEasts(Set<CompanyLicenseEast> companyLicenseEasts) {
        if (this.companyLicenseEasts != null) {
            this.companyLicenseEasts.forEach(i -> i.setIdCompany(null));
        }
        if (companyLicenseEasts != null) {
            companyLicenseEasts.forEach(i -> i.setIdCompany(this));
        }
        this.companyLicenseEasts = companyLicenseEasts;
    }

    public CompanyEast companyLicenseEasts(Set<CompanyLicenseEast> companyLicenseEasts) {
        this.setCompanyLicenseEasts(companyLicenseEasts);
        return this;
    }

    public CompanyEast addCompanyLicenseEast(CompanyLicenseEast companyLicenseEast) {
        this.companyLicenseEasts.add(companyLicenseEast);
        companyLicenseEast.setIdCompany(this);
        return this;
    }

    public CompanyEast removeCompanyLicenseEast(CompanyLicenseEast companyLicenseEast) {
        this.companyLicenseEasts.remove(companyLicenseEast);
        companyLicenseEast.setIdCompany(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CompanyEast)) {
            return false;
        }
        return id != null && id.equals(((CompanyEast) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CompanyEast{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", identificationNumber='" + getIdentificationNumber() + "'" +
            "}";
    }
}
