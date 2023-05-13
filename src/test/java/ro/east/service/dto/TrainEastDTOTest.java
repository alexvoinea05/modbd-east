package ro.east.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import ro.east.web.rest.TestUtil;

class TrainEastDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TrainEastDTO.class);
        TrainEastDTO trainEastDTO1 = new TrainEastDTO();
        trainEastDTO1.setId(1L);
        TrainEastDTO trainEastDTO2 = new TrainEastDTO();
        assertThat(trainEastDTO1).isNotEqualTo(trainEastDTO2);
        trainEastDTO2.setId(trainEastDTO1.getId());
        assertThat(trainEastDTO1).isEqualTo(trainEastDTO2);
        trainEastDTO2.setId(2L);
        assertThat(trainEastDTO1).isNotEqualTo(trainEastDTO2);
        trainEastDTO1.setId(null);
        assertThat(trainEastDTO1).isNotEqualTo(trainEastDTO2);
    }
}
