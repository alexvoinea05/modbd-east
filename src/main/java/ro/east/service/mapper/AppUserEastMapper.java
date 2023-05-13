package ro.east.service.mapper;

import org.mapstruct.*;
import ro.east.domain.AppUserEast;
import ro.east.service.dto.AppUserEastDTO;

/**
 * Mapper for the entity {@link AppUserEast} and its DTO {@link AppUserEastDTO}.
 */
@Mapper(componentModel = "spring")
public interface AppUserEastMapper extends EntityMapper<AppUserEastDTO, AppUserEast> {}
