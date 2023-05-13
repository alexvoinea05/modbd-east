package ro.east.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import ro.east.web.rest.TestUtil;

class AppUserEastDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AppUserEastDTO.class);
        AppUserEastDTO appUserEastDTO1 = new AppUserEastDTO();
        appUserEastDTO1.setId(1L);
        AppUserEastDTO appUserEastDTO2 = new AppUserEastDTO();
        assertThat(appUserEastDTO1).isNotEqualTo(appUserEastDTO2);
        appUserEastDTO2.setId(appUserEastDTO1.getId());
        assertThat(appUserEastDTO1).isEqualTo(appUserEastDTO2);
        appUserEastDTO2.setId(2L);
        assertThat(appUserEastDTO1).isNotEqualTo(appUserEastDTO2);
        appUserEastDTO1.setId(null);
        assertThat(appUserEastDTO1).isNotEqualTo(appUserEastDTO2);
    }
}
