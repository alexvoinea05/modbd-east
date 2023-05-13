package ro.east.service.impl;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.east.domain.CompanyEast;
import ro.east.repository.CompanyEastRepository;
import ro.east.service.CompanyEastService;
import ro.east.service.dto.CompanyEastDTO;
import ro.east.service.mapper.CompanyEastMapper;

/**
 * Service Implementation for managing {@link CompanyEast}.
 */
@Service
@Transactional
public class CompanyEastServiceImpl implements CompanyEastService {

    private final Logger log = LoggerFactory.getLogger(CompanyEastServiceImpl.class);

    private final CompanyEastRepository companyEastRepository;

    private final CompanyEastMapper companyEastMapper;

    public CompanyEastServiceImpl(CompanyEastRepository companyEastRepository, CompanyEastMapper companyEastMapper) {
        this.companyEastRepository = companyEastRepository;
        this.companyEastMapper = companyEastMapper;
    }

    @Override
    public CompanyEastDTO save(CompanyEastDTO companyEastDTO) {
        log.debug("Request to save CompanyEast : {}", companyEastDTO);
        CompanyEast companyEast = companyEastMapper.toEntity(companyEastDTO);
        companyEast = companyEastRepository.save(companyEast);
        return companyEastMapper.toDto(companyEast);
    }

    @Override
    public CompanyEastDTO update(CompanyEastDTO companyEastDTO) {
        log.debug("Request to update CompanyEast : {}", companyEastDTO);
        CompanyEast companyEast = companyEastMapper.toEntity(companyEastDTO);
        companyEast = companyEastRepository.save(companyEast);
        return companyEastMapper.toDto(companyEast);
    }

    @Override
    public Optional<CompanyEastDTO> partialUpdate(CompanyEastDTO companyEastDTO) {
        log.debug("Request to partially update CompanyEast : {}", companyEastDTO);

        return companyEastRepository
            .findById(companyEastDTO.getId())
            .map(existingCompanyEast -> {
                companyEastMapper.partialUpdate(existingCompanyEast, companyEastDTO);

                return existingCompanyEast;
            })
            .map(companyEastRepository::save)
            .map(companyEastMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CompanyEastDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CompanyEasts");
        return companyEastRepository.findAll(pageable).map(companyEastMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CompanyEastDTO> findOne(Long id) {
        log.debug("Request to get CompanyEast : {}", id);
        return companyEastRepository.findById(id).map(companyEastMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete CompanyEast : {}", id);
        companyEastRepository.deleteById(id);
    }
}
