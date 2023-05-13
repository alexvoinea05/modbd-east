package ro.east.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TicketEastMapperTest {

    private TicketEastMapper ticketEastMapper;

    @BeforeEach
    public void setUp() {
        ticketEastMapper = new TicketEastMapperImpl();
    }
}
