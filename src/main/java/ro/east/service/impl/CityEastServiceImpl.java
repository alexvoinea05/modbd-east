package ro.east.service.impl;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.east.domain.CityEast;
import ro.east.repository.CityEastRepository;
import ro.east.service.CityEastService;
import ro.east.service.dto.CityEastDTO;
import ro.east.service.mapper.CityEastMapper;

/**
 * Service Implementation for managing {@link CityEast}.
 */
@Service
@Transactional
public class CityEastServiceImpl implements CityEastService {

    private final Logger log = LoggerFactory.getLogger(CityEastServiceImpl.class);

    private final CityEastRepository cityEastRepository;

    private final CityEastMapper cityEastMapper;

    public CityEastServiceImpl(CityEastRepository cityEastRepository, CityEastMapper cityEastMapper) {
        this.cityEastRepository = cityEastRepository;
        this.cityEastMapper = cityEastMapper;
    }

    @Override
    public CityEastDTO save(CityEastDTO cityEastDTO) {
        log.debug("Request to save CityEast : {}", cityEastDTO);
        CityEast cityEast = cityEastMapper.toEntity(cityEastDTO);
        cityEast = cityEastRepository.save(cityEast);
        return cityEastMapper.toDto(cityEast);
    }

    @Override
    public CityEastDTO update(CityEastDTO cityEastDTO) {
        log.debug("Request to update CityEast : {}", cityEastDTO);
        CityEast cityEast = cityEastMapper.toEntity(cityEastDTO);
        cityEast = cityEastRepository.save(cityEast);
        return cityEastMapper.toDto(cityEast);
    }

    @Override
    public Optional<CityEastDTO> partialUpdate(CityEastDTO cityEastDTO) {
        log.debug("Request to partially update CityEast : {}", cityEastDTO);

        return cityEastRepository
            .findById(cityEastDTO.getId())
            .map(existingCityEast -> {
                cityEastMapper.partialUpdate(existingCityEast, cityEastDTO);

                return existingCityEast;
            })
            .map(cityEastRepository::save)
            .map(cityEastMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CityEastDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CityEasts");
        return cityEastRepository.findAll(pageable).map(cityEastMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CityEastDTO> findOne(Long id) {
        log.debug("Request to get CityEast : {}", id);
        return cityEastRepository.findById(id).map(cityEastMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete CityEast : {}", id);
        cityEastRepository.deleteById(id);
    }
}
