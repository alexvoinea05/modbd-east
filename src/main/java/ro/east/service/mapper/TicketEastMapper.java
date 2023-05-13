package ro.east.service.mapper;

import org.mapstruct.*;
import ro.east.domain.TicketEast;
import ro.east.service.dto.TicketEastDTO;

/**
 * Mapper for the entity {@link TicketEast} and its DTO {@link TicketEastDTO}.
 */
@Mapper(componentModel = "spring")
public interface TicketEastMapper extends EntityMapper<TicketEastDTO, TicketEast> {}
