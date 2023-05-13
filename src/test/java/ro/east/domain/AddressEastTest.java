package ro.east.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import ro.east.web.rest.TestUtil;

class AddressEastTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AddressEast.class);
        AddressEast addressEast1 = new AddressEast();
        addressEast1.setId(1L);
        AddressEast addressEast2 = new AddressEast();
        addressEast2.setId(addressEast1.getId());
        assertThat(addressEast1).isEqualTo(addressEast2);
        addressEast2.setId(2L);
        assertThat(addressEast1).isNotEqualTo(addressEast2);
        addressEast1.setId(null);
        assertThat(addressEast1).isNotEqualTo(addressEast2);
    }
}
