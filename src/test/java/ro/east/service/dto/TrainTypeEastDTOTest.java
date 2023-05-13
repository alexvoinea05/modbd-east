package ro.east.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import ro.east.web.rest.TestUtil;

class TrainTypeEastDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TrainTypeEastDTO.class);
        TrainTypeEastDTO trainTypeEastDTO1 = new TrainTypeEastDTO();
        trainTypeEastDTO1.setId(1L);
        TrainTypeEastDTO trainTypeEastDTO2 = new TrainTypeEastDTO();
        assertThat(trainTypeEastDTO1).isNotEqualTo(trainTypeEastDTO2);
        trainTypeEastDTO2.setId(trainTypeEastDTO1.getId());
        assertThat(trainTypeEastDTO1).isEqualTo(trainTypeEastDTO2);
        trainTypeEastDTO2.setId(2L);
        assertThat(trainTypeEastDTO1).isNotEqualTo(trainTypeEastDTO2);
        trainTypeEastDTO1.setId(null);
        assertThat(trainTypeEastDTO1).isNotEqualTo(trainTypeEastDTO2);
    }
}
