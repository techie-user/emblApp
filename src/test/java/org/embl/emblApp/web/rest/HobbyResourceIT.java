package org.embl.emblApp.web.rest;

import org.embl.emblApp.PersonApp;
import org.embl.emblApp.domain.Hobby;
import org.embl.emblApp.repository.HobbyRepository;
import org.embl.emblApp.service.dto.HobbyDTO;
import org.embl.emblApp.service.IHobbyService;
import org.embl.emblApp.service.mapper.HobbyMapper;
import org.embl.emblApp.web.rest.errors.ExceptionTranslator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.embl.emblApp.web.rest.TestUtil.createFormattingConversionService;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link HobbyResource} REST controller.
 */
@SpringBootTest(classes = PersonApp.class)
public class HobbyResourceIT {

    private static final String DEFAULT_TITLE = "Test Person CRUD App";
    private static final String UPDATED_TITLE = "Update Person CRUD App";

    @Autowired
    private HobbyRepository hobbyRepository;

    @Autowired
    private HobbyMapper hobbyMapper;

    @Autowired
    private IHobbyService hobbyService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restHobbyMockMvc;

    private Hobby hobby;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final HobbyResource hobbyResource = new HobbyResource(hobbyService);
        this.restHobbyMockMvc = MockMvcBuilders.standaloneSetup(hobbyResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Hobby createEntity(EntityManager em) {
        Hobby hobby = new Hobby()
            .title(DEFAULT_TITLE);
        return hobby;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Hobby createUpdatedEntity(EntityManager em) {
        Hobby hobby = new Hobby()
            .title(UPDATED_TITLE);
        return hobby;
    }

    @BeforeEach
    public void initTest() {
        hobby = createEntity(em);
    }

    @Test
    @Transactional
    public void createHobby() throws Exception {
        int databaseSizeBeforeCreate = hobbyRepository.findAll().size();

        // Create the Hobby
        HobbyDTO hobbyDTO = hobbyMapper.toDto(hobby);
        restHobbyMockMvc.perform(post("/api/hobbies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(hobbyDTO)))
            .andExpect(status().isCreated());

        // Validate the Hobby in the database
        List<Hobby> hobbyList = hobbyRepository.findAll();
        assertThat(hobbyList).hasSize(databaseSizeBeforeCreate + 1);
        Hobby testHobby = hobbyList.get(hobbyList.size() - 1);
        assertThat(testHobby.getTitle()).isEqualTo(DEFAULT_TITLE);
    }

    @Test
    @Transactional
    public void createHobbyWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = hobbyRepository.findAll().size();

        // Create the Hobby with an existing ID
        hobby.setId(1L);
        HobbyDTO hobbyDTO = hobbyMapper.toDto(hobby);

        // An entity with an existing ID cannot be created, so this API call must fail
        restHobbyMockMvc.perform(post("/api/hobbies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(hobbyDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Hobby in the database
        List<Hobby> hobbyList = hobbyRepository.findAll();
        assertThat(hobbyList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllHobbies() throws Exception {
        // Initialize the database
        hobbyRepository.saveAndFlush(hobby);

        // Get all the hobbyList
        restHobbyMockMvc.perform(get("/api/hobbies?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(hobby.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)));
    }
    
    @Test
    @Transactional
    public void getHobby() throws Exception {
        // Initialize the database
        hobbyRepository.saveAndFlush(hobby);

        // Get the hobby
        restHobbyMockMvc.perform(get("/api/hobbies/{id}", hobby.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(hobby.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE));
    }

    @Test
    @Transactional
    public void getNonExistingHobby() throws Exception {
        // Get the hobby
        restHobbyMockMvc.perform(get("/api/hobbies/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateHobby() throws Exception {
        // Initialize the database
        hobbyRepository.saveAndFlush(hobby);

        int databaseSizeBeforeUpdate = hobbyRepository.findAll().size();

        // Update the hobby
        Hobby updatedHobby = hobbyRepository.findById(hobby.getId()).get();
        // Disconnect from session so that the updates on updatedHobby are not directly saved in db
        em.detach(updatedHobby);
        updatedHobby
            .title(UPDATED_TITLE);
        HobbyDTO hobbyDTO = hobbyMapper.toDto(updatedHobby);

        restHobbyMockMvc.perform(put("/api/hobbies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(hobbyDTO)))
            .andExpect(status().isOk());

        // Validate the Hobby in the database
        List<Hobby> hobbyList = hobbyRepository.findAll();
        assertThat(hobbyList).hasSize(databaseSizeBeforeUpdate);
        Hobby testHobby = hobbyList.get(hobbyList.size() - 1);
        assertThat(testHobby.getTitle()).isEqualTo(UPDATED_TITLE);
    }

    @Test
    @Transactional
    public void updateNonExistingHobby() throws Exception {
        int databaseSizeBeforeUpdate = hobbyRepository.findAll().size();

        // Create the Hobby
        HobbyDTO hobbyDTO = hobbyMapper.toDto(hobby);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHobbyMockMvc.perform(put("/api/hobbies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(hobbyDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Hobby in the database
        List<Hobby> hobbyList = hobbyRepository.findAll();
        assertThat(hobbyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteHobby() throws Exception {
        // Initialize the database
        hobbyRepository.saveAndFlush(hobby);

        int databaseSizeBeforeDelete = hobbyRepository.findAll().size();

        // Delete the hobby
        restHobbyMockMvc.perform(delete("/api/hobbies/{id}", hobby.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Hobby> hobbyList = hobbyRepository.findAll();
        assertThat(hobbyList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
