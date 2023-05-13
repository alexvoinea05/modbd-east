package ro.east.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CompanyEastMapperTest {

    private CompanyEastMapper companyEastMapper;

    @BeforeEach
    public void setUp() {
        companyEastMapper = new CompanyEastMapperImpl();
    }
}
