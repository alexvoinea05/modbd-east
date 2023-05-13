package ro.east.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import ro.east.web.rest.TestUtil;

class CompanyEastDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CompanyEastDTO.class);
        CompanyEastDTO companyEastDTO1 = new CompanyEastDTO();
        companyEastDTO1.setId(1L);
        CompanyEastDTO companyEastDTO2 = new CompanyEastDTO();
        assertThat(companyEastDTO1).isNotEqualTo(companyEastDTO2);
        companyEastDTO2.setId(companyEastDTO1.getId());
        assertThat(companyEastDTO1).isEqualTo(companyEastDTO2);
        companyEastDTO2.setId(2L);
        assertThat(companyEastDTO1).isNotEqualTo(companyEastDTO2);
        companyEastDTO1.setId(null);
        assertThat(companyEastDTO1).isNotEqualTo(companyEastDTO2);
    }
}
