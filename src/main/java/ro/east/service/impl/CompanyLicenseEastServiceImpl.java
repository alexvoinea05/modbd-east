package ro.east.service.impl;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.east.domain.CompanyLicenseEast;
import ro.east.repository.CompanyLicenseEastRepository;
import ro.east.service.CompanyLicenseEastService;
import ro.east.service.dto.CompanyLicenseEastDTO;
import ro.east.service.mapper.CompanyLicenseEastMapper;

/**
 * Service Implementation for managing {@link CompanyLicenseEast}.
 */
@Service
@Transactional
public class CompanyLicenseEastServiceImpl implements CompanyLicenseEastService {

    private final Logger log = LoggerFactory.getLogger(CompanyLicenseEastServiceImpl.class);

    private final CompanyLicenseEastRepository companyLicenseEastRepository;

    private final CompanyLicenseEastMapper companyLicenseEastMapper;

    public CompanyLicenseEastServiceImpl(
        CompanyLicenseEastRepository companyLicenseEastRepository,
        CompanyLicenseEastMapper companyLicenseEastMapper
    ) {
        this.companyLicenseEastRepository = companyLicenseEastRepository;
        this.companyLicenseEastMapper = companyLicenseEastMapper;
    }

    @Override
    public CompanyLicenseEastDTO save(CompanyLicenseEastDTO companyLicenseEastDTO) {
        log.debug("Request to save CompanyLicenseEast : {}", companyLicenseEastDTO);
        CompanyLicenseEast companyLicenseEast = companyLicenseEastMapper.toEntity(companyLicenseEastDTO);
        companyLicenseEast = companyLicenseEastRepository.save(companyLicenseEast);
        return companyLicenseEastMapper.toDto(companyLicenseEast);
    }

    @Override
    public CompanyLicenseEastDTO update(CompanyLicenseEastDTO companyLicenseEastDTO) {
        log.debug("Request to update CompanyLicenseEast : {}", companyLicenseEastDTO);
        CompanyLicenseEast companyLicenseEast = companyLicenseEastMapper.toEntity(companyLicenseEastDTO);
        companyLicenseEast = companyLicenseEastRepository.save(companyLicenseEast);
        return companyLicenseEastMapper.toDto(companyLicenseEast);
    }

    @Override
    public Optional<CompanyLicenseEastDTO> partialUpdate(CompanyLicenseEastDTO companyLicenseEastDTO) {
        log.debug("Request to partially update CompanyLicenseEast : {}", companyLicenseEastDTO);

        return companyLicenseEastRepository
            .findById(companyLicenseEastDTO.getId())
            .map(existingCompanyLicenseEast -> {
                companyLicenseEastMapper.partialUpdate(existingCompanyLicenseEast, companyLicenseEastDTO);

                return existingCompanyLicenseEast;
            })
            .map(companyLicenseEastRepository::save)
            .map(companyLicenseEastMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CompanyLicenseEastDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CompanyLicenseEasts");
        return companyLicenseEastRepository.findAll(pageable).map(companyLicenseEastMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CompanyLicenseEastDTO> findOne(Long id) {
        log.debug("Request to get CompanyLicenseEast : {}", id);
        return companyLicenseEastRepository.findById(id).map(companyLicenseEastMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete CompanyLicenseEast : {}", id);
        companyLicenseEastRepository.deleteById(id);
    }
}
