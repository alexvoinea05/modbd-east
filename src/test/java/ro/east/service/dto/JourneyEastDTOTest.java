package ro.east.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import ro.east.web.rest.TestUtil;

class JourneyEastDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(JourneyEastDTO.class);
        JourneyEastDTO journeyEastDTO1 = new JourneyEastDTO();
        journeyEastDTO1.setId(1L);
        JourneyEastDTO journeyEastDTO2 = new JourneyEastDTO();
        assertThat(journeyEastDTO1).isNotEqualTo(journeyEastDTO2);
        journeyEastDTO2.setId(journeyEastDTO1.getId());
        assertThat(journeyEastDTO1).isEqualTo(journeyEastDTO2);
        journeyEastDTO2.setId(2L);
        assertThat(journeyEastDTO1).isNotEqualTo(journeyEastDTO2);
        journeyEastDTO1.setId(null);
        assertThat(journeyEastDTO1).isNotEqualTo(journeyEastDTO2);
    }
}
