package ro.east.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import ro.east.web.rest.TestUtil;

class AppUserEastTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AppUserEast.class);
        AppUserEast appUserEast1 = new AppUserEast();
        appUserEast1.setId(1L);
        AppUserEast appUserEast2 = new AppUserEast();
        appUserEast2.setId(appUserEast1.getId());
        assertThat(appUserEast1).isEqualTo(appUserEast2);
        appUserEast2.setId(2L);
        assertThat(appUserEast1).isNotEqualTo(appUserEast2);
        appUserEast1.setId(null);
        assertThat(appUserEast1).isNotEqualTo(appUserEast2);
    }
}
