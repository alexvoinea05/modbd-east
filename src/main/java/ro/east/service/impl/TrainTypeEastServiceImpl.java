package ro.east.service.impl;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.east.domain.TrainTypeEast;
import ro.east.repository.TrainTypeEastRepository;
import ro.east.service.TrainTypeEastService;
import ro.east.service.dto.TrainTypeEastDTO;
import ro.east.service.mapper.TrainTypeEastMapper;

/**
 * Service Implementation for managing {@link TrainTypeEast}.
 */
@Service
@Transactional
public class TrainTypeEastServiceImpl implements TrainTypeEastService {

    private final Logger log = LoggerFactory.getLogger(TrainTypeEastServiceImpl.class);

    private final TrainTypeEastRepository trainTypeEastRepository;

    private final TrainTypeEastMapper trainTypeEastMapper;

    public TrainTypeEastServiceImpl(TrainTypeEastRepository trainTypeEastRepository, TrainTypeEastMapper trainTypeEastMapper) {
        this.trainTypeEastRepository = trainTypeEastRepository;
        this.trainTypeEastMapper = trainTypeEastMapper;
    }

    @Override
    public TrainTypeEastDTO save(TrainTypeEastDTO trainTypeEastDTO) {
        log.debug("Request to save TrainTypeEast : {}", trainTypeEastDTO);
        TrainTypeEast trainTypeEast = trainTypeEastMapper.toEntity(trainTypeEastDTO);
        trainTypeEast = trainTypeEastRepository.save(trainTypeEast);
        return trainTypeEastMapper.toDto(trainTypeEast);
    }

    @Override
    public TrainTypeEastDTO update(TrainTypeEastDTO trainTypeEastDTO) {
        log.debug("Request to update TrainTypeEast : {}", trainTypeEastDTO);
        TrainTypeEast trainTypeEast = trainTypeEastMapper.toEntity(trainTypeEastDTO);
        trainTypeEast = trainTypeEastRepository.save(trainTypeEast);
        return trainTypeEastMapper.toDto(trainTypeEast);
    }

    @Override
    public Optional<TrainTypeEastDTO> partialUpdate(TrainTypeEastDTO trainTypeEastDTO) {
        log.debug("Request to partially update TrainTypeEast : {}", trainTypeEastDTO);

        return trainTypeEastRepository
            .findById(trainTypeEastDTO.getId())
            .map(existingTrainTypeEast -> {
                trainTypeEastMapper.partialUpdate(existingTrainTypeEast, trainTypeEastDTO);

                return existingTrainTypeEast;
            })
            .map(trainTypeEastRepository::save)
            .map(trainTypeEastMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TrainTypeEastDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TrainTypeEasts");
        return trainTypeEastRepository.findAll(pageable).map(trainTypeEastMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TrainTypeEastDTO> findOne(Long id) {
        log.debug("Request to get TrainTypeEast : {}", id);
        return trainTypeEastRepository.findById(id).map(trainTypeEastMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete TrainTypeEast : {}", id);
        trainTypeEastRepository.deleteById(id);
    }
}
