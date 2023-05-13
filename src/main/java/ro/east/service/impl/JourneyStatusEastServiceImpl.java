package ro.east.service.impl;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.east.domain.JourneyStatusEast;
import ro.east.repository.JourneyStatusEastRepository;
import ro.east.service.JourneyStatusEastService;
import ro.east.service.dto.JourneyStatusEastDTO;
import ro.east.service.mapper.JourneyStatusEastMapper;

/**
 * Service Implementation for managing {@link JourneyStatusEast}.
 */
@Service
@Transactional
public class JourneyStatusEastServiceImpl implements JourneyStatusEastService {

    private final Logger log = LoggerFactory.getLogger(JourneyStatusEastServiceImpl.class);

    private final JourneyStatusEastRepository journeyStatusEastRepository;

    private final JourneyStatusEastMapper journeyStatusEastMapper;

    public JourneyStatusEastServiceImpl(
        JourneyStatusEastRepository journeyStatusEastRepository,
        JourneyStatusEastMapper journeyStatusEastMapper
    ) {
        this.journeyStatusEastRepository = journeyStatusEastRepository;
        this.journeyStatusEastMapper = journeyStatusEastMapper;
    }

    @Override
    public JourneyStatusEastDTO save(JourneyStatusEastDTO journeyStatusEastDTO) {
        log.debug("Request to save JourneyStatusEast : {}", journeyStatusEastDTO);
        JourneyStatusEast journeyStatusEast = journeyStatusEastMapper.toEntity(journeyStatusEastDTO);
        journeyStatusEast = journeyStatusEastRepository.save(journeyStatusEast);
        return journeyStatusEastMapper.toDto(journeyStatusEast);
    }

    @Override
    public JourneyStatusEastDTO update(JourneyStatusEastDTO journeyStatusEastDTO) {
        log.debug("Request to update JourneyStatusEast : {}", journeyStatusEastDTO);
        JourneyStatusEast journeyStatusEast = journeyStatusEastMapper.toEntity(journeyStatusEastDTO);
        journeyStatusEast = journeyStatusEastRepository.save(journeyStatusEast);
        return journeyStatusEastMapper.toDto(journeyStatusEast);
    }

    @Override
    public Optional<JourneyStatusEastDTO> partialUpdate(JourneyStatusEastDTO journeyStatusEastDTO) {
        log.debug("Request to partially update JourneyStatusEast : {}", journeyStatusEastDTO);

        return journeyStatusEastRepository
            .findById(journeyStatusEastDTO.getId())
            .map(existingJourneyStatusEast -> {
                journeyStatusEastMapper.partialUpdate(existingJourneyStatusEast, journeyStatusEastDTO);

                return existingJourneyStatusEast;
            })
            .map(journeyStatusEastRepository::save)
            .map(journeyStatusEastMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<JourneyStatusEastDTO> findAll(Pageable pageable) {
        log.debug("Request to get all JourneyStatusEasts");
        return journeyStatusEastRepository.findAll(pageable).map(journeyStatusEastMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<JourneyStatusEastDTO> findOne(Long id) {
        log.debug("Request to get JourneyStatusEast : {}", id);
        return journeyStatusEastRepository.findById(id).map(journeyStatusEastMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete JourneyStatusEast : {}", id);
        journeyStatusEastRepository.deleteById(id);
    }
}
