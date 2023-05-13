package ro.east.service.impl;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.east.domain.LicenseEast;
import ro.east.repository.LicenseEastRepository;
import ro.east.service.LicenseEastService;
import ro.east.service.dto.LicenseEastDTO;
import ro.east.service.mapper.LicenseEastMapper;

/**
 * Service Implementation for managing {@link LicenseEast}.
 */
@Service
@Transactional
public class LicenseEastServiceImpl implements LicenseEastService {

    private final Logger log = LoggerFactory.getLogger(LicenseEastServiceImpl.class);

    private final LicenseEastRepository licenseEastRepository;

    private final LicenseEastMapper licenseEastMapper;

    public LicenseEastServiceImpl(LicenseEastRepository licenseEastRepository, LicenseEastMapper licenseEastMapper) {
        this.licenseEastRepository = licenseEastRepository;
        this.licenseEastMapper = licenseEastMapper;
    }

    @Override
    public LicenseEastDTO save(LicenseEastDTO licenseEastDTO) {
        log.debug("Request to save LicenseEast : {}", licenseEastDTO);
        LicenseEast licenseEast = licenseEastMapper.toEntity(licenseEastDTO);
        licenseEast = licenseEastRepository.save(licenseEast);
        return licenseEastMapper.toDto(licenseEast);
    }

    @Override
    public LicenseEastDTO update(LicenseEastDTO licenseEastDTO) {
        log.debug("Request to update LicenseEast : {}", licenseEastDTO);
        LicenseEast licenseEast = licenseEastMapper.toEntity(licenseEastDTO);
        licenseEast = licenseEastRepository.save(licenseEast);
        return licenseEastMapper.toDto(licenseEast);
    }

    @Override
    public Optional<LicenseEastDTO> partialUpdate(LicenseEastDTO licenseEastDTO) {
        log.debug("Request to partially update LicenseEast : {}", licenseEastDTO);

        return licenseEastRepository
            .findById(licenseEastDTO.getId())
            .map(existingLicenseEast -> {
                licenseEastMapper.partialUpdate(existingLicenseEast, licenseEastDTO);

                return existingLicenseEast;
            })
            .map(licenseEastRepository::save)
            .map(licenseEastMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<LicenseEastDTO> findAll(Pageable pageable) {
        log.debug("Request to get all LicenseEasts");
        return licenseEastRepository.findAll(pageable).map(licenseEastMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<LicenseEastDTO> findOne(Long id) {
        log.debug("Request to get LicenseEast : {}", id);
        return licenseEastRepository.findById(id).map(licenseEastMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete LicenseEast : {}", id);
        licenseEastRepository.deleteById(id);
    }
}
