package ro.east.service.impl;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.east.domain.UserTypeEast;
import ro.east.repository.UserTypeEastRepository;
import ro.east.service.UserTypeEastService;
import ro.east.service.dto.UserTypeEastDTO;
import ro.east.service.mapper.UserTypeEastMapper;

/**
 * Service Implementation for managing {@link UserTypeEast}.
 */
@Service
@Transactional
public class UserTypeEastServiceImpl implements UserTypeEastService {

    private final Logger log = LoggerFactory.getLogger(UserTypeEastServiceImpl.class);

    private final UserTypeEastRepository userTypeEastRepository;

    private final UserTypeEastMapper userTypeEastMapper;

    public UserTypeEastServiceImpl(UserTypeEastRepository userTypeEastRepository, UserTypeEastMapper userTypeEastMapper) {
        this.userTypeEastRepository = userTypeEastRepository;
        this.userTypeEastMapper = userTypeEastMapper;
    }

    @Override
    public UserTypeEastDTO save(UserTypeEastDTO userTypeEastDTO) {
        log.debug("Request to save UserTypeEast : {}", userTypeEastDTO);
        UserTypeEast userTypeEast = userTypeEastMapper.toEntity(userTypeEastDTO);
        userTypeEast = userTypeEastRepository.save(userTypeEast);
        return userTypeEastMapper.toDto(userTypeEast);
    }

    @Override
    public UserTypeEastDTO update(UserTypeEastDTO userTypeEastDTO) {
        log.debug("Request to update UserTypeEast : {}", userTypeEastDTO);
        UserTypeEast userTypeEast = userTypeEastMapper.toEntity(userTypeEastDTO);
        userTypeEast = userTypeEastRepository.save(userTypeEast);
        return userTypeEastMapper.toDto(userTypeEast);
    }

    @Override
    public Optional<UserTypeEastDTO> partialUpdate(UserTypeEastDTO userTypeEastDTO) {
        log.debug("Request to partially update UserTypeEast : {}", userTypeEastDTO);

        return userTypeEastRepository
            .findById(userTypeEastDTO.getId())
            .map(existingUserTypeEast -> {
                userTypeEastMapper.partialUpdate(existingUserTypeEast, userTypeEastDTO);

                return existingUserTypeEast;
            })
            .map(userTypeEastRepository::save)
            .map(userTypeEastMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UserTypeEastDTO> findAll(Pageable pageable) {
        log.debug("Request to get all UserTypeEasts");
        return userTypeEastRepository.findAll(pageable).map(userTypeEastMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<UserTypeEastDTO> findOne(Long id) {
        log.debug("Request to get UserTypeEast : {}", id);
        return userTypeEastRepository.findById(id).map(userTypeEastMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete UserTypeEast : {}", id);
        userTypeEastRepository.deleteById(id);
    }
}
