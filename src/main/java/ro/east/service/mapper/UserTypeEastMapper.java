package ro.east.service.mapper;

import org.mapstruct.*;
import ro.east.domain.UserTypeEast;
import ro.east.service.dto.UserTypeEastDTO;

/**
 * Mapper for the entity {@link UserTypeEast} and its DTO {@link UserTypeEastDTO}.
 */
@Mapper(componentModel = "spring")
public interface UserTypeEastMapper extends EntityMapper<UserTypeEastDTO, UserTypeEast> {}
