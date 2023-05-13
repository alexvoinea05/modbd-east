package ro.east.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import ro.east.web.rest.TestUtil;

class RailwayStationEastTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RailwayStationEast.class);
        RailwayStationEast railwayStationEast1 = new RailwayStationEast();
        railwayStationEast1.setId(1L);
        RailwayStationEast railwayStationEast2 = new RailwayStationEast();
        railwayStationEast2.setId(railwayStationEast1.getId());
        assertThat(railwayStationEast1).isEqualTo(railwayStationEast2);
        railwayStationEast2.setId(2L);
        assertThat(railwayStationEast1).isNotEqualTo(railwayStationEast2);
        railwayStationEast1.setId(null);
        assertThat(railwayStationEast1).isNotEqualTo(railwayStationEast2);
    }
}
