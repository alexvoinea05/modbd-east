package ro.east.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AppUserEastMapperTest {

    private AppUserEastMapper appUserEastMapper;

    @BeforeEach
    public void setUp() {
        appUserEastMapper = new AppUserEastMapperImpl();
    }
}
