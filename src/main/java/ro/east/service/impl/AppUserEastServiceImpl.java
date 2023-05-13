package ro.east.service.impl;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.east.domain.AppUserEast;
import ro.east.repository.AppUserEastRepository;
import ro.east.service.AppUserEastService;
import ro.east.service.dto.AppUserEastDTO;
import ro.east.service.mapper.AppUserEastMapper;

/**
 * Service Implementation for managing {@link AppUserEast}.
 */
@Service
@Transactional
public class AppUserEastServiceImpl implements AppUserEastService {

    private final Logger log = LoggerFactory.getLogger(AppUserEastServiceImpl.class);

    private final AppUserEastRepository appUserEastRepository;

    private final AppUserEastMapper appUserEastMapper;

    public AppUserEastServiceImpl(AppUserEastRepository appUserEastRepository, AppUserEastMapper appUserEastMapper) {
        this.appUserEastRepository = appUserEastRepository;
        this.appUserEastMapper = appUserEastMapper;
    }

    @Override
    public AppUserEastDTO save(AppUserEastDTO appUserEastDTO) {
        log.debug("Request to save AppUserEast : {}", appUserEastDTO);
        AppUserEast appUserEast = appUserEastMapper.toEntity(appUserEastDTO);
        appUserEast = appUserEastRepository.save(appUserEast);
        return appUserEastMapper.toDto(appUserEast);
    }

    @Override
    public AppUserEastDTO update(AppUserEastDTO appUserEastDTO) {
        log.debug("Request to update AppUserEast : {}", appUserEastDTO);
        AppUserEast appUserEast = appUserEastMapper.toEntity(appUserEastDTO);
        appUserEast = appUserEastRepository.save(appUserEast);
        return appUserEastMapper.toDto(appUserEast);
    }

    @Override
    public Optional<AppUserEastDTO> partialUpdate(AppUserEastDTO appUserEastDTO) {
        log.debug("Request to partially update AppUserEast : {}", appUserEastDTO);

        return appUserEastRepository
            .findById(appUserEastDTO.getId())
            .map(existingAppUserEast -> {
                appUserEastMapper.partialUpdate(existingAppUserEast, appUserEastDTO);

                return existingAppUserEast;
            })
            .map(appUserEastRepository::save)
            .map(appUserEastMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AppUserEastDTO> findAll(Pageable pageable) {
        log.debug("Request to get all AppUserEasts");
        return appUserEastRepository.findAll(pageable).map(appUserEastMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AppUserEastDTO> findOne(Long id) {
        log.debug("Request to get AppUserEast : {}", id);
        return appUserEastRepository.findById(id).map(appUserEastMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete AppUserEast : {}", id);
        appUserEastRepository.deleteById(id);
    }
}
