package ro.east.service.mapper;

import org.mapstruct.*;
import ro.east.domain.JourneyEast;
import ro.east.service.dto.JourneyEastDTO;

/**
 * Mapper for the entity {@link JourneyEast} and its DTO {@link JourneyEastDTO}.
 */
@Mapper(componentModel = "spring")
public interface JourneyEastMapper extends EntityMapper<JourneyEastDTO, JourneyEast> {}
