package ro.east.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import ro.east.web.rest.TestUtil;

class TicketEastTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TicketEast.class);
        TicketEast ticketEast1 = new TicketEast();
        ticketEast1.setId(1L);
        TicketEast ticketEast2 = new TicketEast();
        ticketEast2.setId(ticketEast1.getId());
        assertThat(ticketEast1).isEqualTo(ticketEast2);
        ticketEast2.setId(2L);
        assertThat(ticketEast1).isNotEqualTo(ticketEast2);
        ticketEast1.setId(null);
        assertThat(ticketEast1).isNotEqualTo(ticketEast2);
    }
}
