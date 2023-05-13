package ro.east.service.mapper;

import org.mapstruct.*;
import ro.east.domain.CityEast;
import ro.east.service.dto.CityEastDTO;

/**
 * Mapper for the entity {@link CityEast} and its DTO {@link CityEastDTO}.
 */
@Mapper(componentModel = "spring")
public interface CityEastMapper extends EntityMapper<CityEastDTO, CityEast> {}
