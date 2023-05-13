package ro.east.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RailwayStationEastMapperTest {

    private RailwayStationEastMapper railwayStationEastMapper;

    @BeforeEach
    public void setUp() {
        railwayStationEastMapper = new RailwayStationEastMapperImpl();
    }
}
