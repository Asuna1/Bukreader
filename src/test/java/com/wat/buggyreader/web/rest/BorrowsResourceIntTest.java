package com.wat.buggyreader.web.rest;

import com.wat.buggyreader.BukreaderApp;

import com.wat.buggyreader.domain.Borrows;
import com.wat.buggyreader.repository.BorrowsRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the BorrowsResource REST controller.
 *
 * @see BorrowsResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BukreaderApp.class)
public class BorrowsResourceIntTest {

    private static final String DEFAULT_BOOK_ID = "AAAAA";
    private static final String UPDATED_BOOK_ID = "BBBBB";

    private static final String DEFAULT_USER_ID = "AAAAA";
    private static final String UPDATED_USER_ID = "BBBBB";

    private static final LocalDate DEFAULT_DATE_FROM = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_FROM = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATE_TO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_TO = LocalDate.now(ZoneId.systemDefault());

    private static final Boolean DEFAULT_IS_ACTIVATED = false;
    private static final Boolean UPDATED_IS_ACTIVATED = true;

    private static final Boolean DEFAULT_IS_WAITING = false;
    private static final Boolean UPDATED_IS_WAITING = true;

    private static final Integer DEFAULT_PRICE = 1;
    private static final Integer UPDATED_PRICE = 2;

    @Inject
    private BorrowsRepository borrowsRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restBorrowsMockMvc;

    private Borrows borrows;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        BorrowsResource borrowsResource = new BorrowsResource();
        ReflectionTestUtils.setField(borrowsResource, "borrowsRepository", borrowsRepository);
        this.restBorrowsMockMvc = MockMvcBuilders.standaloneSetup(borrowsResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Borrows createEntity() {
        Borrows borrows = new Borrows()
                .book_id(DEFAULT_BOOK_ID)
                .user_id(DEFAULT_USER_ID)
                .date_from(DEFAULT_DATE_FROM)
                .date_to(DEFAULT_DATE_TO)
                .is_activated(DEFAULT_IS_ACTIVATED)
                .is_waiting(DEFAULT_IS_WAITING)
                .price(DEFAULT_PRICE);
        return borrows;
    }

    @Before
    public void initTest() {
        borrowsRepository.deleteAll();
        borrows = createEntity();
    }

    @Test
    public void createBorrows() throws Exception {
        int databaseSizeBeforeCreate = borrowsRepository.findAll().size();

        // Create the Borrows

        restBorrowsMockMvc.perform(post("/api/borrows")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(borrows)))
                .andExpect(status().isCreated());

        // Validate the Borrows in the database
        List<Borrows> borrows = borrowsRepository.findAll();
        assertThat(borrows).hasSize(databaseSizeBeforeCreate + 1);
        Borrows testBorrows = borrows.get(borrows.size() - 1);
        assertThat(testBorrows.getBook_id()).isEqualTo(DEFAULT_BOOK_ID);
        assertThat(testBorrows.getUser_id()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testBorrows.getDate_from()).isEqualTo(DEFAULT_DATE_FROM);
        assertThat(testBorrows.getDate_to()).isEqualTo(DEFAULT_DATE_TO);
        assertThat(testBorrows.isIs_activated()).isEqualTo(DEFAULT_IS_ACTIVATED);
        assertThat(testBorrows.isIs_waiting()).isEqualTo(DEFAULT_IS_WAITING);
        assertThat(testBorrows.getPrice()).isEqualTo(DEFAULT_PRICE);
    }

    @Test
    public void getAllBorrows() throws Exception {
        // Initialize the database
        borrowsRepository.save(borrows);

        // Get all the borrows
        restBorrowsMockMvc.perform(get("/api/borrows?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(borrows.getId())))
                .andExpect(jsonPath("$.[*].book_id").value(hasItem(DEFAULT_BOOK_ID.toString())))
                .andExpect(jsonPath("$.[*].user_id").value(hasItem(DEFAULT_USER_ID.toString())))
                .andExpect(jsonPath("$.[*].date_from").value(hasItem(DEFAULT_DATE_FROM.toString())))
                .andExpect(jsonPath("$.[*].date_to").value(hasItem(DEFAULT_DATE_TO.toString())))
                .andExpect(jsonPath("$.[*].is_activated").value(hasItem(DEFAULT_IS_ACTIVATED.booleanValue())))
                .andExpect(jsonPath("$.[*].is_waiting").value(hasItem(DEFAULT_IS_WAITING.booleanValue())))
                .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE)));
    }

    @Test
    public void getBorrows() throws Exception {
        // Initialize the database
        borrowsRepository.save(borrows);

        // Get the borrows
        restBorrowsMockMvc.perform(get("/api/borrows/{id}", borrows.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(borrows.getId()))
            .andExpect(jsonPath("$.book_id").value(DEFAULT_BOOK_ID.toString()))
            .andExpect(jsonPath("$.user_id").value(DEFAULT_USER_ID.toString()))
            .andExpect(jsonPath("$.date_from").value(DEFAULT_DATE_FROM.toString()))
            .andExpect(jsonPath("$.date_to").value(DEFAULT_DATE_TO.toString()))
            .andExpect(jsonPath("$.is_activated").value(DEFAULT_IS_ACTIVATED.booleanValue()))
            .andExpect(jsonPath("$.is_waiting").value(DEFAULT_IS_WAITING.booleanValue()))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE));
    }

    @Test
    public void getNonExistingBorrows() throws Exception {
        // Get the borrows
        restBorrowsMockMvc.perform(get("/api/borrows/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    public void updateBorrows() throws Exception {
        // Initialize the database
        borrowsRepository.save(borrows);
        int databaseSizeBeforeUpdate = borrowsRepository.findAll().size();

        // Update the borrows
        Borrows updatedBorrows = borrowsRepository.findOne(borrows.getId());
        updatedBorrows
                .book_id(UPDATED_BOOK_ID)
                .user_id(UPDATED_USER_ID)
                .date_from(UPDATED_DATE_FROM)
                .date_to(UPDATED_DATE_TO)
                .is_activated(UPDATED_IS_ACTIVATED)
                .is_waiting(UPDATED_IS_WAITING)
                .price(UPDATED_PRICE);

        restBorrowsMockMvc.perform(put("/api/borrows")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedBorrows)))
                .andExpect(status().isOk());

        // Validate the Borrows in the database
        List<Borrows> borrows = borrowsRepository.findAll();
        assertThat(borrows).hasSize(databaseSizeBeforeUpdate);
        Borrows testBorrows = borrows.get(borrows.size() - 1);
        assertThat(testBorrows.getBook_id()).isEqualTo(UPDATED_BOOK_ID);
        assertThat(testBorrows.getUser_id()).isEqualTo(UPDATED_USER_ID);
        assertThat(testBorrows.getDate_from()).isEqualTo(UPDATED_DATE_FROM);
        assertThat(testBorrows.getDate_to()).isEqualTo(UPDATED_DATE_TO);
        assertThat(testBorrows.isIs_activated()).isEqualTo(UPDATED_IS_ACTIVATED);
        assertThat(testBorrows.isIs_waiting()).isEqualTo(UPDATED_IS_WAITING);
        assertThat(testBorrows.getPrice()).isEqualTo(UPDATED_PRICE);
    }

    @Test
    public void deleteBorrows() throws Exception {
        // Initialize the database
        borrowsRepository.save(borrows);
        int databaseSizeBeforeDelete = borrowsRepository.findAll().size();

        // Get the borrows
        restBorrowsMockMvc.perform(delete("/api/borrows/{id}", borrows.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Borrows> borrows = borrowsRepository.findAll();
        assertThat(borrows).hasSize(databaseSizeBeforeDelete - 1);
    }
}
