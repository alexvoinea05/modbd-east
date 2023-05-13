package ro.east.service.mapper;

import org.mapstruct.*;
import ro.east.domain.TrainTypeEast;
import ro.east.service.dto.TrainTypeEastDTO;

/**
 * Mapper for the entity {@link TrainTypeEast} and its DTO {@link TrainTypeEastDTO}.
 */
@Mapper(componentModel = "spring")
public interface TrainTypeEastMapper extends EntityMapper<TrainTypeEastDTO, TrainTypeEast> {}
