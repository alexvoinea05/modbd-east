package ro.east.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import ro.east.web.rest.TestUtil;

class JourneyStatusEastTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(JourneyStatusEast.class);
        JourneyStatusEast journeyStatusEast1 = new JourneyStatusEast();
        journeyStatusEast1.setId(1L);
        JourneyStatusEast journeyStatusEast2 = new JourneyStatusEast();
        journeyStatusEast2.setId(journeyStatusEast1.getId());
        assertThat(journeyStatusEast1).isEqualTo(journeyStatusEast2);
        journeyStatusEast2.setId(2L);
        assertThat(journeyStatusEast1).isNotEqualTo(journeyStatusEast2);
        journeyStatusEast1.setId(null);
        assertThat(journeyStatusEast1).isNotEqualTo(journeyStatusEast2);
    }
}
