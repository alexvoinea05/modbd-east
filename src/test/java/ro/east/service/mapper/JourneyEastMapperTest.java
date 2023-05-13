package ro.east.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class JourneyEastMapperTest {

    private JourneyEastMapper journeyEastMapper;

    @BeforeEach
    public void setUp() {
        journeyEastMapper = new JourneyEastMapperImpl();
    }
}
