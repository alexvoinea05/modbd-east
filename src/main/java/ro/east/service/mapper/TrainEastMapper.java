package ro.east.service.mapper;

import org.mapstruct.*;
import ro.east.domain.TrainEast;
import ro.east.service.dto.TrainEastDTO;

/**
 * Mapper for the entity {@link TrainEast} and its DTO {@link TrainEastDTO}.
 */
@Mapper(componentModel = "spring")
public interface TrainEastMapper extends EntityMapper<TrainEastDTO, TrainEast> {}
