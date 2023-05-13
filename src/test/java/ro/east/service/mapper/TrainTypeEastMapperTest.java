package ro.east.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TrainTypeEastMapperTest {

    private TrainTypeEastMapper trainTypeEastMapper;

    @BeforeEach
    public void setUp() {
        trainTypeEastMapper = new TrainTypeEastMapperImpl();
    }
}
