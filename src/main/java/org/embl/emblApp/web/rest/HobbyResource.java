package org.embl.emblApp.web.rest;

import org.embl.emblApp.service.IHobbyService;
import org.embl.emblApp.service.dto.HobbyDTO;
import org.embl.emblApp.utility.HeaderUtil;
import org.embl.emblApp.utility.PaginationUtil;
import org.embl.emblApp.utility.ResponseUtil;
import org.embl.emblApp.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link org.embl.emblApp.domain.Hobby}.
 */
@RestController
@RequestMapping("/api")
public class HobbyResource {

    private final Logger log = LoggerFactory.getLogger(HobbyResource.class);

    private static final String ENTITY_NAME = "hobby";

    @Value("${app.name}")
    private String applicationName;

    private final IHobbyService hobbyService;

    public HobbyResource(IHobbyService hobbyService) {
        this.hobbyService = hobbyService;
    }

    /**
     * {@code POST  /hobbies} : Create a new hobby.
     *
     * @param hobbyDTO the hobbyDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new hobbyDTO, or with status {@code 400 (Bad Request)} if the hobby has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/hobbies")
    public ResponseEntity<HobbyDTO> createHobby(@RequestBody HobbyDTO hobbyDTO) throws URISyntaxException {
        log.debug("REST request to save Hobby : {}", hobbyDTO);
        if (hobbyDTO.getId() != null) {
            throw new BadRequestAlertException("A new hobby cannot already have an ID", ENTITY_NAME, "idexists");
        }
        HobbyDTO result = hobbyService.save(hobbyDTO);
        return ResponseEntity.created(new URI("/api/hobbies/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /hobbies} : Updates an existing hobby.
     *
     * @param hobbyDTO the hobbyDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated hobbyDTO,
     * or with status {@code 400 (Bad Request)} if the hobbyDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the hobbyDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/hobbies")
    public ResponseEntity<HobbyDTO> updateHobby(@RequestBody HobbyDTO hobbyDTO) throws URISyntaxException {
        log.debug("REST request to update Hobby : {}", hobbyDTO);
        if (hobbyDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        HobbyDTO result = hobbyService.save(hobbyDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, hobbyDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /hobbies} : get all the hobbies.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of hobbies in body.
     */
    @GetMapping("/hobbies")
    public ResponseEntity<List<HobbyDTO>> getAllHobbies(Pageable pageable) {
        log.debug("REST request to get a page of Hobbies");
        Page<HobbyDTO> page = hobbyService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /hobbies/:id} : get the "id" hobby.
     *
     * @param id the id of the hobbyDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the hobbyDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/hobbies/{id}")
    public ResponseEntity<HobbyDTO> getHobby(@PathVariable Long id) {
        log.debug("REST request to get Hobby : {}", id);
        Optional<HobbyDTO> hobbyDTO = hobbyService.findOne(id);
        return ResponseUtil.wrapOrNotFound(hobbyDTO);
    }

    /**
     * {@code DELETE  /hobbies/:id} : delete the "id" hobby.
     *
     * @param id the id of the hobbyDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/hobbies/{id}")
    public ResponseEntity<Void> deleteHobby(@PathVariable Long id) {
        log.debug("REST request to delete Hobby : {}", id);
        hobbyService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
