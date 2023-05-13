package ro.east.service.mapper;

import org.mapstruct.*;
import ro.east.domain.CompanyEast;
import ro.east.service.dto.CompanyEastDTO;

/**
 * Mapper for the entity {@link CompanyEast} and its DTO {@link CompanyEastDTO}.
 */
@Mapper(componentModel = "spring")
public interface CompanyEastMapper extends EntityMapper<CompanyEastDTO, CompanyEast> {}
