package ro.east.service.impl;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.east.domain.FuelTypeEast;
import ro.east.repository.FuelTypeEastRepository;
import ro.east.service.FuelTypeEastService;
import ro.east.service.dto.FuelTypeEastDTO;
import ro.east.service.mapper.FuelTypeEastMapper;

/**
 * Service Implementation for managing {@link FuelTypeEast}.
 */
@Service
@Transactional
public class FuelTypeEastServiceImpl implements FuelTypeEastService {

    private final Logger log = LoggerFactory.getLogger(FuelTypeEastServiceImpl.class);

    private final FuelTypeEastRepository fuelTypeEastRepository;

    private final FuelTypeEastMapper fuelTypeEastMapper;

    public FuelTypeEastServiceImpl(FuelTypeEastRepository fuelTypeEastRepository, FuelTypeEastMapper fuelTypeEastMapper) {
        this.fuelTypeEastRepository = fuelTypeEastRepository;
        this.fuelTypeEastMapper = fuelTypeEastMapper;
    }

    @Override
    public FuelTypeEastDTO save(FuelTypeEastDTO fuelTypeEastDTO) {
        log.debug("Request to save FuelTypeEast : {}", fuelTypeEastDTO);
        FuelTypeEast fuelTypeEast = fuelTypeEastMapper.toEntity(fuelTypeEastDTO);
        fuelTypeEast = fuelTypeEastRepository.save(fuelTypeEast);
        return fuelTypeEastMapper.toDto(fuelTypeEast);
    }

    @Override
    public FuelTypeEastDTO update(FuelTypeEastDTO fuelTypeEastDTO) {
        log.debug("Request to update FuelTypeEast : {}", fuelTypeEastDTO);
        FuelTypeEast fuelTypeEast = fuelTypeEastMapper.toEntity(fuelTypeEastDTO);
        fuelTypeEast = fuelTypeEastRepository.save(fuelTypeEast);
        return fuelTypeEastMapper.toDto(fuelTypeEast);
    }

    @Override
    public Optional<FuelTypeEastDTO> partialUpdate(FuelTypeEastDTO fuelTypeEastDTO) {
        log.debug("Request to partially update FuelTypeEast : {}", fuelTypeEastDTO);

        return fuelTypeEastRepository
            .findById(fuelTypeEastDTO.getId())
            .map(existingFuelTypeEast -> {
                fuelTypeEastMapper.partialUpdate(existingFuelTypeEast, fuelTypeEastDTO);

                return existingFuelTypeEast;
            })
            .map(fuelTypeEastRepository::save)
            .map(fuelTypeEastMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<FuelTypeEastDTO> findAll(Pageable pageable) {
        log.debug("Request to get all FuelTypeEasts");
        return fuelTypeEastRepository.findAll(pageable).map(fuelTypeEastMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<FuelTypeEastDTO> findOne(Long id) {
        log.debug("Request to get FuelTypeEast : {}", id);
        return fuelTypeEastRepository.findById(id).map(fuelTypeEastMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete FuelTypeEast : {}", id);
        fuelTypeEastRepository.deleteById(id);
    }
}
