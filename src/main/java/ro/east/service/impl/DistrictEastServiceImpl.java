package ro.east.service.impl;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.east.domain.DistrictEast;
import ro.east.repository.DistrictEastRepository;
import ro.east.service.DistrictEastService;
import ro.east.service.dto.DistrictEastDTO;
import ro.east.service.mapper.DistrictEastMapper;

/**
 * Service Implementation for managing {@link DistrictEast}.
 */
@Service
@Transactional
public class DistrictEastServiceImpl implements DistrictEastService {

    private final Logger log = LoggerFactory.getLogger(DistrictEastServiceImpl.class);

    private final DistrictEastRepository districtEastRepository;

    private final DistrictEastMapper districtEastMapper;

    public DistrictEastServiceImpl(DistrictEastRepository districtEastRepository, DistrictEastMapper districtEastMapper) {
        this.districtEastRepository = districtEastRepository;
        this.districtEastMapper = districtEastMapper;
    }

    @Override
    public DistrictEastDTO save(DistrictEastDTO districtEastDTO) {
        log.debug("Request to save DistrictEast : {}", districtEastDTO);
        DistrictEast districtEast = districtEastMapper.toEntity(districtEastDTO);
        districtEast = districtEastRepository.save(districtEast);
        return districtEastMapper.toDto(districtEast);
    }

    @Override
    public DistrictEastDTO update(DistrictEastDTO districtEastDTO) {
        log.debug("Request to update DistrictEast : {}", districtEastDTO);
        DistrictEast districtEast = districtEastMapper.toEntity(districtEastDTO);
        districtEast = districtEastRepository.save(districtEast);
        return districtEastMapper.toDto(districtEast);
    }

    @Override
    public Optional<DistrictEastDTO> partialUpdate(DistrictEastDTO districtEastDTO) {
        log.debug("Request to partially update DistrictEast : {}", districtEastDTO);

        return districtEastRepository
            .findById(districtEastDTO.getId())
            .map(existingDistrictEast -> {
                districtEastMapper.partialUpdate(existingDistrictEast, districtEastDTO);

                return existingDistrictEast;
            })
            .map(districtEastRepository::save)
            .map(districtEastMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DistrictEastDTO> findAll(Pageable pageable) {
        log.debug("Request to get all DistrictEasts");
        return districtEastRepository.findAll(pageable).map(districtEastMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<DistrictEastDTO> findOne(Long id) {
        log.debug("Request to get DistrictEast : {}", id);
        return districtEastRepository.findById(id).map(districtEastMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete DistrictEast : {}", id);
        districtEastRepository.deleteById(id);
    }
}
