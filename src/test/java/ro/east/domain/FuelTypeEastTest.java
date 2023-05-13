package ro.east.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import ro.east.web.rest.TestUtil;

class FuelTypeEastTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FuelTypeEast.class);
        FuelTypeEast fuelTypeEast1 = new FuelTypeEast();
        fuelTypeEast1.setId(1L);
        FuelTypeEast fuelTypeEast2 = new FuelTypeEast();
        fuelTypeEast2.setId(fuelTypeEast1.getId());
        assertThat(fuelTypeEast1).isEqualTo(fuelTypeEast2);
        fuelTypeEast2.setId(2L);
        assertThat(fuelTypeEast1).isNotEqualTo(fuelTypeEast2);
        fuelTypeEast1.setId(null);
        assertThat(fuelTypeEast1).isNotEqualTo(fuelTypeEast2);
    }
}
