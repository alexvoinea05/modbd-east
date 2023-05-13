package ro.east.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import ro.east.web.rest.TestUtil;

class LicenseEastDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(LicenseEastDTO.class);
        LicenseEastDTO licenseEastDTO1 = new LicenseEastDTO();
        licenseEastDTO1.setId(1L);
        LicenseEastDTO licenseEastDTO2 = new LicenseEastDTO();
        assertThat(licenseEastDTO1).isNotEqualTo(licenseEastDTO2);
        licenseEastDTO2.setId(licenseEastDTO1.getId());
        assertThat(licenseEastDTO1).isEqualTo(licenseEastDTO2);
        licenseEastDTO2.setId(2L);
        assertThat(licenseEastDTO1).isNotEqualTo(licenseEastDTO2);
        licenseEastDTO1.setId(null);
        assertThat(licenseEastDTO1).isNotEqualTo(licenseEastDTO2);
    }
}
