package ro.east.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import ro.east.web.rest.TestUtil;

class UserTypeEastTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserTypeEast.class);
        UserTypeEast userTypeEast1 = new UserTypeEast();
        userTypeEast1.setId(1L);
        UserTypeEast userTypeEast2 = new UserTypeEast();
        userTypeEast2.setId(userTypeEast1.getId());
        assertThat(userTypeEast1).isEqualTo(userTypeEast2);
        userTypeEast2.setId(2L);
        assertThat(userTypeEast1).isNotEqualTo(userTypeEast2);
        userTypeEast1.setId(null);
        assertThat(userTypeEast1).isNotEqualTo(userTypeEast2);
    }
}
