package ro.east.service.mapper;

import org.mapstruct.*;
import ro.east.domain.CompanyEast;
import ro.east.domain.CompanyLicenseEast;
import ro.east.domain.LicenseEast;
import ro.east.service.dto.CompanyEastDTO;
import ro.east.service.dto.CompanyLicenseEastDTO;
import ro.east.service.dto.LicenseEastDTO;

/**
 * Mapper for the entity {@link CompanyLicenseEast} and its DTO {@link CompanyLicenseEastDTO}.
 */
@Mapper(componentModel = "spring")
public interface CompanyLicenseEastMapper extends EntityMapper<CompanyLicenseEastDTO, CompanyLicenseEast> {
    @Mapping(target = "idCompany", source = "idCompany", qualifiedByName = "companyEastId")
    @Mapping(target = "idLicense", source = "idLicense", qualifiedByName = "licenseEastId")
    CompanyLicenseEastDTO toDto(CompanyLicenseEast s);

    @Named("companyEastId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CompanyEastDTO toDtoCompanyEastId(CompanyEast companyEast);

    @Named("licenseEastId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    LicenseEastDTO toDtoLicenseEastId(LicenseEast licenseEast);
}
