package ro.east.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import ro.east.web.rest.TestUtil;

class UserTypeEastDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserTypeEastDTO.class);
        UserTypeEastDTO userTypeEastDTO1 = new UserTypeEastDTO();
        userTypeEastDTO1.setId(1L);
        UserTypeEastDTO userTypeEastDTO2 = new UserTypeEastDTO();
        assertThat(userTypeEastDTO1).isNotEqualTo(userTypeEastDTO2);
        userTypeEastDTO2.setId(userTypeEastDTO1.getId());
        assertThat(userTypeEastDTO1).isEqualTo(userTypeEastDTO2);
        userTypeEastDTO2.setId(2L);
        assertThat(userTypeEastDTO1).isNotEqualTo(userTypeEastDTO2);
        userTypeEastDTO1.setId(null);
        assertThat(userTypeEastDTO1).isNotEqualTo(userTypeEastDTO2);
    }
}
