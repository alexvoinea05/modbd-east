package ro.east.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import ro.east.web.rest.TestUtil;

class AddressEastDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AddressEastDTO.class);
        AddressEastDTO addressEastDTO1 = new AddressEastDTO();
        addressEastDTO1.setId(1L);
        AddressEastDTO addressEastDTO2 = new AddressEastDTO();
        assertThat(addressEastDTO1).isNotEqualTo(addressEastDTO2);
        addressEastDTO2.setId(addressEastDTO1.getId());
        assertThat(addressEastDTO1).isEqualTo(addressEastDTO2);
        addressEastDTO2.setId(2L);
        assertThat(addressEastDTO1).isNotEqualTo(addressEastDTO2);
        addressEastDTO1.setId(null);
        assertThat(addressEastDTO1).isNotEqualTo(addressEastDTO2);
    }
}
