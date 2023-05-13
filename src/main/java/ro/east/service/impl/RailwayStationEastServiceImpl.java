package ro.east.service.impl;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.east.domain.RailwayStationEast;
import ro.east.repository.RailwayStationEastRepository;
import ro.east.service.RailwayStationEastService;
import ro.east.service.dto.RailwayStationEastDTO;
import ro.east.service.mapper.RailwayStationEastMapper;

/**
 * Service Implementation for managing {@link RailwayStationEast}.
 */
@Service
@Transactional
public class RailwayStationEastServiceImpl implements RailwayStationEastService {

    private final Logger log = LoggerFactory.getLogger(RailwayStationEastServiceImpl.class);

    private final RailwayStationEastRepository railwayStationEastRepository;

    private final RailwayStationEastMapper railwayStationEastMapper;

    public RailwayStationEastServiceImpl(
        RailwayStationEastRepository railwayStationEastRepository,
        RailwayStationEastMapper railwayStationEastMapper
    ) {
        this.railwayStationEastRepository = railwayStationEastRepository;
        this.railwayStationEastMapper = railwayStationEastMapper;
    }

    @Override
    public RailwayStationEastDTO save(RailwayStationEastDTO railwayStationEastDTO) {
        log.debug("Request to save RailwayStationEast : {}", railwayStationEastDTO);
        RailwayStationEast railwayStationEast = railwayStationEastMapper.toEntity(railwayStationEastDTO);
        railwayStationEast = railwayStationEastRepository.save(railwayStationEast);
        return railwayStationEastMapper.toDto(railwayStationEast);
    }

    @Override
    public RailwayStationEastDTO update(RailwayStationEastDTO railwayStationEastDTO) {
        log.debug("Request to update RailwayStationEast : {}", railwayStationEastDTO);
        RailwayStationEast railwayStationEast = railwayStationEastMapper.toEntity(railwayStationEastDTO);
        railwayStationEast = railwayStationEastRepository.save(railwayStationEast);
        return railwayStationEastMapper.toDto(railwayStationEast);
    }

    @Override
    public Optional<RailwayStationEastDTO> partialUpdate(RailwayStationEastDTO railwayStationEastDTO) {
        log.debug("Request to partially update RailwayStationEast : {}", railwayStationEastDTO);

        return railwayStationEastRepository
            .findById(railwayStationEastDTO.getId())
            .map(existingRailwayStationEast -> {
                railwayStationEastMapper.partialUpdate(existingRailwayStationEast, railwayStationEastDTO);

                return existingRailwayStationEast;
            })
            .map(railwayStationEastRepository::save)
            .map(railwayStationEastMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<RailwayStationEastDTO> findAll(Pageable pageable) {
        log.debug("Request to get all RailwayStationEasts");
        return railwayStationEastRepository.findAll(pageable).map(railwayStationEastMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<RailwayStationEastDTO> findOne(Long id) {
        log.debug("Request to get RailwayStationEast : {}", id);
        return railwayStationEastRepository.findById(id).map(railwayStationEastMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete RailwayStationEast : {}", id);
        railwayStationEastRepository.deleteById(id);
    }
}
