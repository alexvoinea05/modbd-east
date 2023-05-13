package ro.east.service.mapper;

import org.mapstruct.*;
import ro.east.domain.LicenseEast;
import ro.east.service.dto.LicenseEastDTO;

/**
 * Mapper for the entity {@link LicenseEast} and its DTO {@link LicenseEastDTO}.
 */
@Mapper(componentModel = "spring")
public interface LicenseEastMapper extends EntityMapper<LicenseEastDTO, LicenseEast> {}
