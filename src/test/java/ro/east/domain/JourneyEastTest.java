package ro.east.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import ro.east.web.rest.TestUtil;

class JourneyEastTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(JourneyEast.class);
        JourneyEast journeyEast1 = new JourneyEast();
        journeyEast1.setId(1L);
        JourneyEast journeyEast2 = new JourneyEast();
        journeyEast2.setId(journeyEast1.getId());
        assertThat(journeyEast1).isEqualTo(journeyEast2);
        journeyEast2.setId(2L);
        assertThat(journeyEast1).isNotEqualTo(journeyEast2);
        journeyEast1.setId(null);
        assertThat(journeyEast1).isNotEqualTo(journeyEast2);
    }
}
