package ro.east.service.mapper;

import org.mapstruct.*;
import ro.east.domain.DistrictEast;
import ro.east.service.dto.DistrictEastDTO;

/**
 * Mapper for the entity {@link DistrictEast} and its DTO {@link DistrictEastDTO}.
 */
@Mapper(componentModel = "spring")
public interface DistrictEastMapper extends EntityMapper<DistrictEastDTO, DistrictEast> {}
