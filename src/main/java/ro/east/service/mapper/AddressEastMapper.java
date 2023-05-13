package ro.east.service.mapper;

import org.mapstruct.*;
import ro.east.domain.AddressEast;
import ro.east.service.dto.AddressEastDTO;

/**
 * Mapper for the entity {@link AddressEast} and its DTO {@link AddressEastDTO}.
 */
@Mapper(componentModel = "spring")
public interface AddressEastMapper extends EntityMapper<AddressEastDTO, AddressEast> {}
