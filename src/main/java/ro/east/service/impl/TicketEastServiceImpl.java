package ro.east.service.impl;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.east.domain.TicketEast;
import ro.east.repository.TicketEastRepository;
import ro.east.service.TicketEastService;
import ro.east.service.dto.TicketEastDTO;
import ro.east.service.mapper.TicketEastMapper;

/**
 * Service Implementation for managing {@link TicketEast}.
 */
@Service
@Transactional
public class TicketEastServiceImpl implements TicketEastService {

    private final Logger log = LoggerFactory.getLogger(TicketEastServiceImpl.class);

    private final TicketEastRepository ticketEastRepository;

    private final TicketEastMapper ticketEastMapper;

    public TicketEastServiceImpl(TicketEastRepository ticketEastRepository, TicketEastMapper ticketEastMapper) {
        this.ticketEastRepository = ticketEastRepository;
        this.ticketEastMapper = ticketEastMapper;
    }

    @Override
    public TicketEastDTO save(TicketEastDTO ticketEastDTO) {
        log.debug("Request to save TicketEast : {}", ticketEastDTO);
        TicketEast ticketEast = ticketEastMapper.toEntity(ticketEastDTO);
        ticketEast = ticketEastRepository.save(ticketEast);
        return ticketEastMapper.toDto(ticketEast);
    }

    @Override
    public TicketEastDTO update(TicketEastDTO ticketEastDTO) {
        log.debug("Request to update TicketEast : {}", ticketEastDTO);
        TicketEast ticketEast = ticketEastMapper.toEntity(ticketEastDTO);
        ticketEast = ticketEastRepository.save(ticketEast);
        return ticketEastMapper.toDto(ticketEast);
    }

    @Override
    public Optional<TicketEastDTO> partialUpdate(TicketEastDTO ticketEastDTO) {
        log.debug("Request to partially update TicketEast : {}", ticketEastDTO);

        return ticketEastRepository
            .findById(ticketEastDTO.getId())
            .map(existingTicketEast -> {
                ticketEastMapper.partialUpdate(existingTicketEast, ticketEastDTO);

                return existingTicketEast;
            })
            .map(ticketEastRepository::save)
            .map(ticketEastMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TicketEastDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TicketEasts");
        return ticketEastRepository.findAll(pageable).map(ticketEastMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TicketEastDTO> findOne(Long id) {
        log.debug("Request to get TicketEast : {}", id);
        return ticketEastRepository.findById(id).map(ticketEastMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete TicketEast : {}", id);
        ticketEastRepository.deleteById(id);
    }
}
