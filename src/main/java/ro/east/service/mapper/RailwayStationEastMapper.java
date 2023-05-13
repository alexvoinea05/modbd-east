package ro.east.service.mapper;

import org.mapstruct.*;
import ro.east.domain.RailwayStationEast;
import ro.east.service.dto.RailwayStationEastDTO;

/**
 * Mapper for the entity {@link RailwayStationEast} and its DTO {@link RailwayStationEastDTO}.
 */
@Mapper(componentModel = "spring")
public interface RailwayStationEastMapper extends EntityMapper<RailwayStationEastDTO, RailwayStationEast> {}
