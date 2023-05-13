package ro.east.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import ro.east.web.rest.TestUtil;

class DistrictEastTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DistrictEast.class);
        DistrictEast districtEast1 = new DistrictEast();
        districtEast1.setId(1L);
        DistrictEast districtEast2 = new DistrictEast();
        districtEast2.setId(districtEast1.getId());
        assertThat(districtEast1).isEqualTo(districtEast2);
        districtEast2.setId(2L);
        assertThat(districtEast1).isNotEqualTo(districtEast2);
        districtEast1.setId(null);
        assertThat(districtEast1).isNotEqualTo(districtEast2);
    }
}
