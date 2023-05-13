package ro.east.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link ro.east.domain.AppUserEast} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AppUserEastDTO implements Serializable {

    private Long id;

    private String email;

    private Double balance;

    private String lastName;

    private String firstName;

    private Long userTypeId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public Long getUserTypeId() {
        return userTypeId;
    }

    public void setUserTypeId(Long userTypeId) {
        this.userTypeId = userTypeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AppUserEastDTO)) {
            return false;
        }

        AppUserEastDTO appUserEastDTO = (AppUserEastDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, appUserEastDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AppUserEastDTO{" +
            "id=" + getId() +
            ", email='" + getEmail() + "'" +
            ", balance=" + getBalance() +
            ", lastName='" + getLastName() + "'" +
            ", firstName='" + getFirstName() + "'" +
            ", userTypeId=" + getUserTypeId() +
            "}";
    }
}
