package ro.east.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import ro.east.web.rest.TestUtil;

class TrainEastTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TrainEast.class);
        TrainEast trainEast1 = new TrainEast();
        trainEast1.setId(1L);
        TrainEast trainEast2 = new TrainEast();
        trainEast2.setId(trainEast1.getId());
        assertThat(trainEast1).isEqualTo(trainEast2);
        trainEast2.setId(2L);
        assertThat(trainEast1).isNotEqualTo(trainEast2);
        trainEast1.setId(null);
        assertThat(trainEast1).isNotEqualTo(trainEast2);
    }
}
