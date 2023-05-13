package ro.east.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import ro.east.web.rest.TestUtil;

class CityEastDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CityEastDTO.class);
        CityEastDTO cityEastDTO1 = new CityEastDTO();
        cityEastDTO1.setId(1L);
        CityEastDTO cityEastDTO2 = new CityEastDTO();
        assertThat(cityEastDTO1).isNotEqualTo(cityEastDTO2);
        cityEastDTO2.setId(cityEastDTO1.getId());
        assertThat(cityEastDTO1).isEqualTo(cityEastDTO2);
        cityEastDTO2.setId(2L);
        assertThat(cityEastDTO1).isNotEqualTo(cityEastDTO2);
        cityEastDTO1.setId(null);
        assertThat(cityEastDTO1).isNotEqualTo(cityEastDTO2);
    }
}
