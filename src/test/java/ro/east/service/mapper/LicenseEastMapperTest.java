package ro.east.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class LicenseEastMapperTest {

    private LicenseEastMapper licenseEastMapper;

    @BeforeEach
    public void setUp() {
        licenseEastMapper = new LicenseEastMapperImpl();
    }
}
