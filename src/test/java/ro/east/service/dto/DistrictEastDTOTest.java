package ro.east.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import ro.east.web.rest.TestUtil;

class DistrictEastDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DistrictEastDTO.class);
        DistrictEastDTO districtEastDTO1 = new DistrictEastDTO();
        districtEastDTO1.setId(1L);
        DistrictEastDTO districtEastDTO2 = new DistrictEastDTO();
        assertThat(districtEastDTO1).isNotEqualTo(districtEastDTO2);
        districtEastDTO2.setId(districtEastDTO1.getId());
        assertThat(districtEastDTO1).isEqualTo(districtEastDTO2);
        districtEastDTO2.setId(2L);
        assertThat(districtEastDTO1).isNotEqualTo(districtEastDTO2);
        districtEastDTO1.setId(null);
        assertThat(districtEastDTO1).isNotEqualTo(districtEastDTO2);
    }
}
