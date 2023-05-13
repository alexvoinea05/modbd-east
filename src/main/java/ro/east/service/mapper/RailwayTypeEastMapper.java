package ro.east.service.mapper;

import org.mapstruct.*;
import ro.east.domain.RailwayTypeEast;
import ro.east.service.dto.RailwayTypeEastDTO;

/**
 * Mapper for the entity {@link RailwayTypeEast} and its DTO {@link RailwayTypeEastDTO}.
 */
@Mapper(componentModel = "spring")
public interface RailwayTypeEastMapper extends EntityMapper<RailwayTypeEastDTO, RailwayTypeEast> {}
