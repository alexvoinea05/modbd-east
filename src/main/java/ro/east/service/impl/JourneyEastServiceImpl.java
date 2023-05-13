package ro.east.service.impl;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.east.domain.JourneyEast;
import ro.east.repository.JourneyEastRepository;
import ro.east.service.JourneyEastService;
import ro.east.service.dto.JourneyEastDTO;
import ro.east.service.mapper.JourneyEastMapper;

/**
 * Service Implementation for managing {@link JourneyEast}.
 */
@Service
@Transactional
public class JourneyEastServiceImpl implements JourneyEastService {

    private final Logger log = LoggerFactory.getLogger(JourneyEastServiceImpl.class);

    private final JourneyEastRepository journeyEastRepository;

    private final JourneyEastMapper journeyEastMapper;

    public JourneyEastServiceImpl(JourneyEastRepository journeyEastRepository, JourneyEastMapper journeyEastMapper) {
        this.journeyEastRepository = journeyEastRepository;
        this.journeyEastMapper = journeyEastMapper;
    }

    @Override
    public JourneyEastDTO save(JourneyEastDTO journeyEastDTO) {
        log.debug("Request to save JourneyEast : {}", journeyEastDTO);
        JourneyEast journeyEast = journeyEastMapper.toEntity(journeyEastDTO);
        journeyEast = journeyEastRepository.save(journeyEast);
        return journeyEastMapper.toDto(journeyEast);
    }

    @Override
    public JourneyEastDTO update(JourneyEastDTO journeyEastDTO) {
        log.debug("Request to update JourneyEast : {}", journeyEastDTO);
        JourneyEast journeyEast = journeyEastMapper.toEntity(journeyEastDTO);
        journeyEast = journeyEastRepository.save(journeyEast);
        return journeyEastMapper.toDto(journeyEast);
    }

    @Override
    public Optional<JourneyEastDTO> partialUpdate(JourneyEastDTO journeyEastDTO) {
        log.debug("Request to partially update JourneyEast : {}", journeyEastDTO);

        return journeyEastRepository
            .findById(journeyEastDTO.getId())
            .map(existingJourneyEast -> {
                journeyEastMapper.partialUpdate(existingJourneyEast, journeyEastDTO);

                return existingJourneyEast;
            })
            .map(journeyEastRepository::save)
            .map(journeyEastMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<JourneyEastDTO> findAll(Pageable pageable) {
        log.debug("Request to get all JourneyEasts");
        return journeyEastRepository.findAll(pageable).map(journeyEastMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<JourneyEastDTO> findOne(Long id) {
        log.debug("Request to get JourneyEast : {}", id);
        return journeyEastRepository.findById(id).map(journeyEastMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete JourneyEast : {}", id);
        journeyEastRepository.deleteById(id);
    }
}
