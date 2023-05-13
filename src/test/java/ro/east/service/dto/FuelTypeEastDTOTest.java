package ro.east.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import ro.east.web.rest.TestUtil;

class FuelTypeEastDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(FuelTypeEastDTO.class);
        FuelTypeEastDTO fuelTypeEastDTO1 = new FuelTypeEastDTO();
        fuelTypeEastDTO1.setId(1L);
        FuelTypeEastDTO fuelTypeEastDTO2 = new FuelTypeEastDTO();
        assertThat(fuelTypeEastDTO1).isNotEqualTo(fuelTypeEastDTO2);
        fuelTypeEastDTO2.setId(fuelTypeEastDTO1.getId());
        assertThat(fuelTypeEastDTO1).isEqualTo(fuelTypeEastDTO2);
        fuelTypeEastDTO2.setId(2L);
        assertThat(fuelTypeEastDTO1).isNotEqualTo(fuelTypeEastDTO2);
        fuelTypeEastDTO1.setId(null);
        assertThat(fuelTypeEastDTO1).isNotEqualTo(fuelTypeEastDTO2);
    }
}
