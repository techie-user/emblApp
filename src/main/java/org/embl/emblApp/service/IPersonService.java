package org.embl.emblApp.service;

import org.embl.emblApp.service.dto.PersonDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface IPersonService {

    PersonDTO save(PersonDTO personDTO);
    Page<PersonDTO> findAll(Pageable pageable);
    Optional<PersonDTO> findOne(Long id);
    void delete(Long id);
}
