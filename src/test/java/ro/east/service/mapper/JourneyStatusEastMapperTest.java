package ro.east.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class JourneyStatusEastMapperTest {

    private JourneyStatusEastMapper journeyStatusEastMapper;

    @BeforeEach
    public void setUp() {
        journeyStatusEastMapper = new JourneyStatusEastMapperImpl();
    }
}
