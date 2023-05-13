package ro.east.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import ro.east.web.rest.TestUtil;

class CompanyLicenseEastDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CompanyLicenseEastDTO.class);
        CompanyLicenseEastDTO companyLicenseEastDTO1 = new CompanyLicenseEastDTO();
        companyLicenseEastDTO1.setId(1L);
        CompanyLicenseEastDTO companyLicenseEastDTO2 = new CompanyLicenseEastDTO();
        assertThat(companyLicenseEastDTO1).isNotEqualTo(companyLicenseEastDTO2);
        companyLicenseEastDTO2.setId(companyLicenseEastDTO1.getId());
        assertThat(companyLicenseEastDTO1).isEqualTo(companyLicenseEastDTO2);
        companyLicenseEastDTO2.setId(2L);
        assertThat(companyLicenseEastDTO1).isNotEqualTo(companyLicenseEastDTO2);
        companyLicenseEastDTO1.setId(null);
        assertThat(companyLicenseEastDTO1).isNotEqualTo(companyLicenseEastDTO2);
    }
}
