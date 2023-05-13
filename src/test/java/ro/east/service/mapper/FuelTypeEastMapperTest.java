package ro.east.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FuelTypeEastMapperTest {

    private FuelTypeEastMapper fuelTypeEastMapper;

    @BeforeEach
    public void setUp() {
        fuelTypeEastMapper = new FuelTypeEastMapperImpl();
    }
}
