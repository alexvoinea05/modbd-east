package ro.east.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import ro.east.web.rest.TestUtil;

class CityEastTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CityEast.class);
        CityEast cityEast1 = new CityEast();
        cityEast1.setId(1L);
        CityEast cityEast2 = new CityEast();
        cityEast2.setId(cityEast1.getId());
        assertThat(cityEast1).isEqualTo(cityEast2);
        cityEast2.setId(2L);
        assertThat(cityEast1).isNotEqualTo(cityEast2);
        cityEast1.setId(null);
        assertThat(cityEast1).isNotEqualTo(cityEast2);
    }
}
