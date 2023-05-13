package ro.east.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UserTypeEastMapperTest {

    private UserTypeEastMapper userTypeEastMapper;

    @BeforeEach
    public void setUp() {
        userTypeEastMapper = new UserTypeEastMapperImpl();
    }
}
