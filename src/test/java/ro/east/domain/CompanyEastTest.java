package ro.east.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import ro.east.web.rest.TestUtil;

class CompanyEastTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CompanyEast.class);
        CompanyEast companyEast1 = new CompanyEast();
        companyEast1.setId(1L);
        CompanyEast companyEast2 = new CompanyEast();
        companyEast2.setId(companyEast1.getId());
        assertThat(companyEast1).isEqualTo(companyEast2);
        companyEast2.setId(2L);
        assertThat(companyEast1).isNotEqualTo(companyEast2);
        companyEast1.setId(null);
        assertThat(companyEast1).isNotEqualTo(companyEast2);
    }
}
