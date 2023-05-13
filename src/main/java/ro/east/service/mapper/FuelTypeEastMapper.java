package ro.east.service.mapper;

import org.mapstruct.*;
import ro.east.domain.FuelTypeEast;
import ro.east.service.dto.FuelTypeEastDTO;

/**
 * Mapper for the entity {@link FuelTypeEast} and its DTO {@link FuelTypeEastDTO}.
 */
@Mapper(componentModel = "spring")
public interface FuelTypeEastMapper extends EntityMapper<FuelTypeEastDTO, FuelTypeEast> {}
