package org.embl.emblApp.service;

import org.embl.emblApp.service.dto.HobbyDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface IHobbyService {

    HobbyDTO save(HobbyDTO hobbyDTO);

    Page<HobbyDTO> findAll(Pageable pageable);

    Optional<HobbyDTO> findOne(Long id);

    void delete(Long id);
}
