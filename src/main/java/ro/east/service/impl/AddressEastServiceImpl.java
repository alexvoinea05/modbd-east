package ro.east.service.impl;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.east.domain.AddressEast;
import ro.east.repository.AddressEastRepository;
import ro.east.service.AddressEastService;
import ro.east.service.dto.AddressEastDTO;
import ro.east.service.mapper.AddressEastMapper;

/**
 * Service Implementation for managing {@link AddressEast}.
 */
@Service
@Transactional
public class AddressEastServiceImpl implements AddressEastService {

    private final Logger log = LoggerFactory.getLogger(AddressEastServiceImpl.class);

    private final AddressEastRepository addressEastRepository;

    private final AddressEastMapper addressEastMapper;

    public AddressEastServiceImpl(AddressEastRepository addressEastRepository, AddressEastMapper addressEastMapper) {
        this.addressEastRepository = addressEastRepository;
        this.addressEastMapper = addressEastMapper;
    }

    @Override
    public AddressEastDTO save(AddressEastDTO addressEastDTO) {
        log.debug("Request to save AddressEast : {}", addressEastDTO);
        AddressEast addressEast = addressEastMapper.toEntity(addressEastDTO);
        addressEast = addressEastRepository.save(addressEast);
        return addressEastMapper.toDto(addressEast);
    }

    @Override
    public AddressEastDTO update(AddressEastDTO addressEastDTO) {
        log.debug("Request to update AddressEast : {}", addressEastDTO);
        AddressEast addressEast = addressEastMapper.toEntity(addressEastDTO);
        addressEast = addressEastRepository.save(addressEast);
        return addressEastMapper.toDto(addressEast);
    }

    @Override
    public Optional<AddressEastDTO> partialUpdate(AddressEastDTO addressEastDTO) {
        log.debug("Request to partially update AddressEast : {}", addressEastDTO);

        return addressEastRepository
            .findById(addressEastDTO.getId())
            .map(existingAddressEast -> {
                addressEastMapper.partialUpdate(existingAddressEast, addressEastDTO);

                return existingAddressEast;
            })
            .map(addressEastRepository::save)
            .map(addressEastMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AddressEastDTO> findAll(Pageable pageable) {
        log.debug("Request to get all AddressEasts");
        return addressEastRepository.findAll(pageable).map(addressEastMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AddressEastDTO> findOne(Long id) {
        log.debug("Request to get AddressEast : {}", id);
        return addressEastRepository.findById(id).map(addressEastMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete AddressEast : {}", id);
        addressEastRepository.deleteById(id);
    }
}
