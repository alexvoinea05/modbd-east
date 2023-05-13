package ro.east.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import ro.east.web.rest.TestUtil;

class CompanyLicenseEastTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CompanyLicenseEast.class);
        CompanyLicenseEast companyLicenseEast1 = new CompanyLicenseEast();
        companyLicenseEast1.setId(1L);
        CompanyLicenseEast companyLicenseEast2 = new CompanyLicenseEast();
        companyLicenseEast2.setId(companyLicenseEast1.getId());
        assertThat(companyLicenseEast1).isEqualTo(companyLicenseEast2);
        companyLicenseEast2.setId(2L);
        assertThat(companyLicenseEast1).isNotEqualTo(companyLicenseEast2);
        companyLicenseEast1.setId(null);
        assertThat(companyLicenseEast1).isNotEqualTo(companyLicenseEast2);
    }
}
