package ro.east.domain;

import java.io.Serializable;
import javax.persistence.*;

/**
 * A CityEast.
 */
@Entity
@Table(name = "city_east")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CityEast implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "district_id")
    private Long districtId;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public CityEast id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public CityEast name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getDistrictId() {
        return this.districtId;
    }

    public CityEast districtId(Long districtId) {
        this.setDistrictId(districtId);
        return this;
    }

    public void setDistrictId(Long districtId) {
        this.districtId = districtId;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CityEast)) {
            return false;
        }
        return id != null && id.equals(((CityEast) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CityEast{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", districtId=" + getDistrictId() +
            "}";
    }
}
