package ro.east.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AddressEastMapperTest {

    private AddressEastMapper addressEastMapper;

    @BeforeEach
    public void setUp() {
        addressEastMapper = new AddressEastMapperImpl();
    }
}
