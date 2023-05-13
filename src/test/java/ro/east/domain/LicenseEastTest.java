package ro.east.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import ro.east.web.rest.TestUtil;

class LicenseEastTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LicenseEast.class);
        LicenseEast licenseEast1 = new LicenseEast();
        licenseEast1.setId(1L);
        LicenseEast licenseEast2 = new LicenseEast();
        licenseEast2.setId(licenseEast1.getId());
        assertThat(licenseEast1).isEqualTo(licenseEast2);
        licenseEast2.setId(2L);
        assertThat(licenseEast1).isNotEqualTo(licenseEast2);
        licenseEast1.setId(null);
        assertThat(licenseEast1).isNotEqualTo(licenseEast2);
    }
}
