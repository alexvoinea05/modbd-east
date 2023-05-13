package ro.east.domain;

import java.io.Serializable;
import javax.persistence.*;

/**
 * A UserTypeEast.
 */
@Entity
@Table(name = "user_type_east")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class UserTypeEast implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "code")
    private String code;

    @Column(name = "discount")
    private Double discount;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public UserTypeEast id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return this.code;
    }

    public UserTypeEast code(String code) {
        this.setCode(code);
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Double getDiscount() {
        return this.discount;
    }

    public UserTypeEast discount(Double discount) {
        this.setDiscount(discount);
        return this;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserTypeEast)) {
            return false;
        }
        return id != null && id.equals(((UserTypeEast) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UserTypeEast{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", discount=" + getDiscount() +
            "}";
    }
}
