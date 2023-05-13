package ro.east.domain;

import java.io.Serializable;
import javax.persistence.*;

/**
 * A AppUserEast.
 */
@Entity
@Table(name = "app_user_east")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AppUserEast implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "email")
    private String email;

    @Column(name = "balance")
    private Double balance;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "user_type_id")
    private Long userTypeId;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public AppUserEast id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return this.email;
    }

    public AppUserEast email(String email) {
        this.setEmail(email);
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Double getBalance() {
        return this.balance;
    }

    public AppUserEast balance(Double balance) {
        this.setBalance(balance);
        return this;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public String getLastName() {
        return this.lastName;
    }

    public AppUserEast lastName(String lastName) {
        this.setLastName(lastName);
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public AppUserEast firstName(String firstName) {
        this.setFirstName(firstName);
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public Long getUserTypeId() {
        return this.userTypeId;
    }

    public AppUserEast userTypeId(Long userTypeId) {
        this.setUserTypeId(userTypeId);
        return this;
    }

    public void setUserTypeId(Long userTypeId) {
        this.userTypeId = userTypeId;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AppUserEast)) {
            return false;
        }
        return id != null && id.equals(((AppUserEast) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AppUserEast{" +
            "id=" + getId() +
            ", email='" + getEmail() + "'" +
            ", balance=" + getBalance() +
            ", lastName='" + getLastName() + "'" +
            ", firstName='" + getFirstName() + "'" +
            ", userTypeId=" + getUserTypeId() +
            "}";
    }
}
