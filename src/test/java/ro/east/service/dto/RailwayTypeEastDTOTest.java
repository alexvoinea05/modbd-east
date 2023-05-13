package ro.east.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import ro.east.web.rest.TestUtil;

class RailwayTypeEastDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RailwayTypeEastDTO.class);
        RailwayTypeEastDTO railwayTypeEastDTO1 = new RailwayTypeEastDTO();
        railwayTypeEastDTO1.setId(1L);
        RailwayTypeEastDTO railwayTypeEastDTO2 = new RailwayTypeEastDTO();
        assertThat(railwayTypeEastDTO1).isNotEqualTo(railwayTypeEastDTO2);
        railwayTypeEastDTO2.setId(railwayTypeEastDTO1.getId());
        assertThat(railwayTypeEastDTO1).isEqualTo(railwayTypeEastDTO2);
        railwayTypeEastDTO2.setId(2L);
        assertThat(railwayTypeEastDTO1).isNotEqualTo(railwayTypeEastDTO2);
        railwayTypeEastDTO1.setId(null);
        assertThat(railwayTypeEastDTO1).isNotEqualTo(railwayTypeEastDTO2);
    }
}
