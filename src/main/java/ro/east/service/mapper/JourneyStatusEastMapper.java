package ro.east.service.mapper;

import org.mapstruct.*;
import ro.east.domain.JourneyStatusEast;
import ro.east.service.dto.JourneyStatusEastDTO;

/**
 * Mapper for the entity {@link JourneyStatusEast} and its DTO {@link JourneyStatusEastDTO}.
 */
@Mapper(componentModel = "spring")
public interface JourneyStatusEastMapper extends EntityMapper<JourneyStatusEastDTO, JourneyStatusEast> {}
