package ro.east.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link ro.east.domain.AddressEast} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AddressEastDTO implements Serializable {

    private Long id;

    private String streetNumber;

    private String street;

    private String zipcode;

    private Long cityId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(String streetNumber) {
        this.streetNumber = streetNumber;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public Long getCityId() {
        return cityId;
    }

    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AddressEastDTO)) {
            return false;
        }

        AddressEastDTO addressEastDTO = (AddressEastDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, addressEastDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AddressEastDTO{" +
            "id=" + getId() +
            ", streetNumber='" + getStreetNumber() + "'" +
            ", street='" + getStreet() + "'" +
            ", zipcode='" + getZipcode() + "'" +
            ", cityId=" + getCityId() +
            "}";
    }
}
