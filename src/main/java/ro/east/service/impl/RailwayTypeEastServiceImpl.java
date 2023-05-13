package ro.east.service.impl;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.east.domain.RailwayTypeEast;
import ro.east.repository.RailwayTypeEastRepository;
import ro.east.service.RailwayTypeEastService;
import ro.east.service.dto.RailwayTypeEastDTO;
import ro.east.service.mapper.RailwayTypeEastMapper;

/**
 * Service Implementation for managing {@link RailwayTypeEast}.
 */
@Service
@Transactional
public class RailwayTypeEastServiceImpl implements RailwayTypeEastService {

    private final Logger log = LoggerFactory.getLogger(RailwayTypeEastServiceImpl.class);

    private final RailwayTypeEastRepository railwayTypeEastRepository;

    private final RailwayTypeEastMapper railwayTypeEastMapper;

    public RailwayTypeEastServiceImpl(RailwayTypeEastRepository railwayTypeEastRepository, RailwayTypeEastMapper railwayTypeEastMapper) {
        this.railwayTypeEastRepository = railwayTypeEastRepository;
        this.railwayTypeEastMapper = railwayTypeEastMapper;
    }

    @Override
    public RailwayTypeEastDTO save(RailwayTypeEastDTO railwayTypeEastDTO) {
        log.debug("Request to save RailwayTypeEast : {}", railwayTypeEastDTO);
        RailwayTypeEast railwayTypeEast = railwayTypeEastMapper.toEntity(railwayTypeEastDTO);
        railwayTypeEast = railwayTypeEastRepository.save(railwayTypeEast);
        return railwayTypeEastMapper.toDto(railwayTypeEast);
    }

    @Override
    public RailwayTypeEastDTO update(RailwayTypeEastDTO railwayTypeEastDTO) {
        log.debug("Request to update RailwayTypeEast : {}", railwayTypeEastDTO);
        RailwayTypeEast railwayTypeEast = railwayTypeEastMapper.toEntity(railwayTypeEastDTO);
        railwayTypeEast = railwayTypeEastRepository.save(railwayTypeEast);
        return railwayTypeEastMapper.toDto(railwayTypeEast);
    }

    @Override
    public Optional<RailwayTypeEastDTO> partialUpdate(RailwayTypeEastDTO railwayTypeEastDTO) {
        log.debug("Request to partially update RailwayTypeEast : {}", railwayTypeEastDTO);

        return railwayTypeEastRepository
            .findById(railwayTypeEastDTO.getId())
            .map(existingRailwayTypeEast -> {
                railwayTypeEastMapper.partialUpdate(existingRailwayTypeEast, railwayTypeEastDTO);

                return existingRailwayTypeEast;
            })
            .map(railwayTypeEastRepository::save)
            .map(railwayTypeEastMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<RailwayTypeEastDTO> findAll(Pageable pageable) {
        log.debug("Request to get all RailwayTypeEasts");
        return railwayTypeEastRepository.findAll(pageable).map(railwayTypeEastMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<RailwayTypeEastDTO> findOne(Long id) {
        log.debug("Request to get RailwayTypeEast : {}", id);
        return railwayTypeEastRepository.findById(id).map(railwayTypeEastMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete RailwayTypeEast : {}", id);
        railwayTypeEastRepository.deleteById(id);
    }
}
