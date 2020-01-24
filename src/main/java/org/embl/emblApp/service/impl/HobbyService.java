package org.embl.emblApp.service.impl;

import org.embl.emblApp.repository.HobbyRepository;
import org.embl.emblApp.service.IHobbyService;
import org.embl.emblApp.service.dto.HobbyDTO;
import org.embl.emblApp.service.mapper.HobbyMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link org.embl.emblApp.domain.Hobby}.
 */
@Service
@Transactional
public class HobbyService implements IHobbyService {

    private final Logger log = LoggerFactory.getLogger(HobbyService.class);

    private final HobbyRepository hobbyRepository;

    private final HobbyMapper hobbyMapper;

    public HobbyService(HobbyRepository hobbyRepository, HobbyMapper hobbyMapper) {
        this.hobbyRepository = hobbyRepository;
        this.hobbyMapper = hobbyMapper;
    }

    @Override
    public HobbyDTO save(HobbyDTO hobbyDTO) {
        return null;
    }

    @Override
    public Page<HobbyDTO> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public Optional<HobbyDTO> findOne(Long id) {
        return Optional.empty();
    }

    @Override
    public void delete(Long id) {

    }
}
