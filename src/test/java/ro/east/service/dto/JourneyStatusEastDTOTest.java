package ro.east.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import ro.east.web.rest.TestUtil;

class JourneyStatusEastDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(JourneyStatusEastDTO.class);
        JourneyStatusEastDTO journeyStatusEastDTO1 = new JourneyStatusEastDTO();
        journeyStatusEastDTO1.setId(1L);
        JourneyStatusEastDTO journeyStatusEastDTO2 = new JourneyStatusEastDTO();
        assertThat(journeyStatusEastDTO1).isNotEqualTo(journeyStatusEastDTO2);
        journeyStatusEastDTO2.setId(journeyStatusEastDTO1.getId());
        assertThat(journeyStatusEastDTO1).isEqualTo(journeyStatusEastDTO2);
        journeyStatusEastDTO2.setId(2L);
        assertThat(journeyStatusEastDTO1).isNotEqualTo(journeyStatusEastDTO2);
        journeyStatusEastDTO1.setId(null);
        assertThat(journeyStatusEastDTO1).isNotEqualTo(journeyStatusEastDTO2);
    }
}
