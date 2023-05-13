package ro.east.domain;

import java.io.Serializable;
import javax.persistence.*;

/**
 * A DistrictEast.
 */
@Entity
@Table(name = "district_east")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DistrictEast implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "region")
    private String region;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public DistrictEast id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public DistrictEast name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRegion() {
        return this.region;
    }

    public DistrictEast region(String region) {
        this.setRegion(region);
        return this;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DistrictEast)) {
            return false;
        }
        return id != null && id.equals(((DistrictEast) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DistrictEast{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", region='" + getRegion() + "'" +
            "}";
    }
}
