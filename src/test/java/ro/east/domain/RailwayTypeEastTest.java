package ro.east.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import ro.east.web.rest.TestUtil;

class RailwayTypeEastTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RailwayTypeEast.class);
        RailwayTypeEast railwayTypeEast1 = new RailwayTypeEast();
        railwayTypeEast1.setId(1L);
        RailwayTypeEast railwayTypeEast2 = new RailwayTypeEast();
        railwayTypeEast2.setId(railwayTypeEast1.getId());
        assertThat(railwayTypeEast1).isEqualTo(railwayTypeEast2);
        railwayTypeEast2.setId(2L);
        assertThat(railwayTypeEast1).isNotEqualTo(railwayTypeEast2);
        railwayTypeEast1.setId(null);
        assertThat(railwayTypeEast1).isNotEqualTo(railwayTypeEast2);
    }
}
