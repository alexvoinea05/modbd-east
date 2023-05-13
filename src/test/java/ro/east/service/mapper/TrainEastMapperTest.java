package ro.east.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TrainEastMapperTest {

    private TrainEastMapper trainEastMapper;

    @BeforeEach
    public void setUp() {
        trainEastMapper = new TrainEastMapperImpl();
    }
}
