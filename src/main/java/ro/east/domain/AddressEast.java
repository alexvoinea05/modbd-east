package ro.east.domain;

import java.io.Serializable;
import javax.persistence.*;

/**
 * A AddressEast.
 */
@Entity
@Table(name = "address_east")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AddressEast implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "street_number")
    private String streetNumber;

    @Column(name = "street")
    private String street;

    @Column(name = "zipcode")
    private String zipcode;

    @Column(name = "city_id")
    private Long cityId;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public AddressEast id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStreetNumber() {
        return this.streetNumber;
    }

    public AddressEast streetNumber(String streetNumber) {
        this.setStreetNumber(streetNumber);
        return this;
    }

    public void setStreetNumber(String streetNumber) {
        this.streetNumber = streetNumber;
    }

    public String getStreet() {
        return this.street;
    }

    public AddressEast street(String street) {
        this.setStreet(street);
        return this;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getZipcode() {
        return this.zipcode;
    }

    public AddressEast zipcode(String zipcode) {
        this.setZipcode(zipcode);
        return this;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public Long getCityId() {
        return this.cityId;
    }

    public AddressEast cityId(Long cityId) {
        this.setCityId(cityId);
        return this;
    }

    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AddressEast)) {
            return false;
        }
        return id != null && id.equals(((AddressEast) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AddressEast{" +
            "id=" + getId() +
            ", streetNumber='" + getStreetNumber() + "'" +
            ", street='" + getStreet() + "'" +
            ", zipcode='" + getZipcode() + "'" +
            ", cityId=" + getCityId() +
            "}";
    }
}
