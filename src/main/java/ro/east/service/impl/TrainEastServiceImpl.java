package ro.east.service.impl;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.east.domain.TrainEast;
import ro.east.repository.TrainEastRepository;
import ro.east.service.TrainEastService;
import ro.east.service.dto.TrainEastDTO;
import ro.east.service.mapper.TrainEastMapper;

/**
 * Service Implementation for managing {@link TrainEast}.
 */
@Service
@Transactional
public class TrainEastServiceImpl implements TrainEastService {

    private final Logger log = LoggerFactory.getLogger(TrainEastServiceImpl.class);

    private final TrainEastRepository trainEastRepository;

    private final TrainEastMapper trainEastMapper;

    public TrainEastServiceImpl(TrainEastRepository trainEastRepository, TrainEastMapper trainEastMapper) {
        this.trainEastRepository = trainEastRepository;
        this.trainEastMapper = trainEastMapper;
    }

    @Override
    public TrainEastDTO save(TrainEastDTO trainEastDTO) {
        log.debug("Request to save TrainEast : {}", trainEastDTO);
        TrainEast trainEast = trainEastMapper.toEntity(trainEastDTO);
        trainEast = trainEastRepository.save(trainEast);
        return trainEastMapper.toDto(trainEast);
    }

    @Override
    public TrainEastDTO update(TrainEastDTO trainEastDTO) {
        log.debug("Request to update TrainEast : {}", trainEastDTO);
        TrainEast trainEast = trainEastMapper.toEntity(trainEastDTO);
        trainEast = trainEastRepository.save(trainEast);
        return trainEastMapper.toDto(trainEast);
    }

    @Override
    public Optional<TrainEastDTO> partialUpdate(TrainEastDTO trainEastDTO) {
        log.debug("Request to partially update TrainEast : {}", trainEastDTO);

        return trainEastRepository
            .findById(trainEastDTO.getId())
            .map(existingTrainEast -> {
                trainEastMapper.partialUpdate(existingTrainEast, trainEastDTO);

                return existingTrainEast;
            })
            .map(trainEastRepository::save)
            .map(trainEastMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TrainEastDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TrainEasts");
        return trainEastRepository.findAll(pageable).map(trainEastMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TrainEastDTO> findOne(Long id) {
        log.debug("Request to get TrainEast : {}", id);
        return trainEastRepository.findById(id).map(trainEastMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete TrainEast : {}", id);
        trainEastRepository.deleteById(id);
    }
}
