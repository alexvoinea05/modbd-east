package ro.east.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import ro.east.web.rest.TestUtil;

class TrainTypeEastTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TrainTypeEast.class);
        TrainTypeEast trainTypeEast1 = new TrainTypeEast();
        trainTypeEast1.setId(1L);
        TrainTypeEast trainTypeEast2 = new TrainTypeEast();
        trainTypeEast2.setId(trainTypeEast1.getId());
        assertThat(trainTypeEast1).isEqualTo(trainTypeEast2);
        trainTypeEast2.setId(2L);
        assertThat(trainTypeEast1).isNotEqualTo(trainTypeEast2);
        trainTypeEast1.setId(null);
        assertThat(trainTypeEast1).isNotEqualTo(trainTypeEast2);
    }
}
