package ro.east.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import ro.east.web.rest.TestUtil;

class RailwayStationEastDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RailwayStationEastDTO.class);
        RailwayStationEastDTO railwayStationEastDTO1 = new RailwayStationEastDTO();
        railwayStationEastDTO1.setId(1L);
        RailwayStationEastDTO railwayStationEastDTO2 = new RailwayStationEastDTO();
        assertThat(railwayStationEastDTO1).isNotEqualTo(railwayStationEastDTO2);
        railwayStationEastDTO2.setId(railwayStationEastDTO1.getId());
        assertThat(railwayStationEastDTO1).isEqualTo(railwayStationEastDTO2);
        railwayStationEastDTO2.setId(2L);
        assertThat(railwayStationEastDTO1).isNotEqualTo(railwayStationEastDTO2);
        railwayStationEastDTO1.setId(null);
        assertThat(railwayStationEastDTO1).isNotEqualTo(railwayStationEastDTO2);
    }
}
