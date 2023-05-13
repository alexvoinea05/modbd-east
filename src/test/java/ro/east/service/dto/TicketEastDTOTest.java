package ro.east.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import ro.east.web.rest.TestUtil;

class TicketEastDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TicketEastDTO.class);
        TicketEastDTO ticketEastDTO1 = new TicketEastDTO();
        ticketEastDTO1.setId(1L);
        TicketEastDTO ticketEastDTO2 = new TicketEastDTO();
        assertThat(ticketEastDTO1).isNotEqualTo(ticketEastDTO2);
        ticketEastDTO2.setId(ticketEastDTO1.getId());
        assertThat(ticketEastDTO1).isEqualTo(ticketEastDTO2);
        ticketEastDTO2.setId(2L);
        assertThat(ticketEastDTO1).isNotEqualTo(ticketEastDTO2);
        ticketEastDTO1.setId(null);
        assertThat(ticketEastDTO1).isNotEqualTo(ticketEastDTO2);
    }
}
