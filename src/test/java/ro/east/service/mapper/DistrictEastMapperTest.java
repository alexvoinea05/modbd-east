package ro.east.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DistrictEastMapperTest {

    private DistrictEastMapper districtEastMapper;

    @BeforeEach
    public void setUp() {
        districtEastMapper = new DistrictEastMapperImpl();
    }
}
