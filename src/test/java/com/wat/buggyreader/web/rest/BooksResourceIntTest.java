package com.wat.buggyreader.web.rest;

import com.wat.buggyreader.BukreaderApp;

import com.wat.buggyreader.domain.Books;
import com.wat.buggyreader.repository.BooksRepository;

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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the BooksResource REST controller.
 *
 * @see BooksResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BukreaderApp.class)
public class BooksResourceIntTest {

    private static final String DEFAULT_ISBN = "AAAAA";
    private static final String UPDATED_ISBN = "BBBBB";

    private static final String DEFAULT_TITLE = "AAAAA";
    private static final String UPDATED_TITLE = "BBBBB";

    private static final String DEFAULT_AUTHOR = "AAAAA";
    private static final String UPDATED_AUTHOR = "BBBBB";

    private static final Integer DEFAULT_PUBLICATION_YEAR = 1;
    private static final Integer UPDATED_PUBLICATION_YEAR = 2;

    private static final String DEFAULT_PUBLISHER = "AAAAA";
    private static final String UPDATED_PUBLISHER = "BBBBB";

    private static final String DEFAULT_IMAGE_S = "AAAAA";
    private static final String UPDATED_IMAGE_S = "BBBBB";

    private static final String DEFAULT_IMAGE_M = "AAAAA";
    private static final String UPDATED_IMAGE_M = "BBBBB";

    private static final String DEFAULT_IMAGE_L = "AAAAA";
    private static final String UPDATED_IMAGE_L = "BBBBB";

    @Inject
    private BooksRepository booksRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restBooksMockMvc;

    private Books books;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        BooksResource booksResource = new BooksResource();
        ReflectionTestUtils.setField(booksResource, "booksRepository", booksRepository);
        this.restBooksMockMvc = MockMvcBuilders.standaloneSetup(booksResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Books createEntity() {
        Books books = new Books()
                .isbn(DEFAULT_ISBN)
                .title(DEFAULT_TITLE)
                .author(DEFAULT_AUTHOR)
                .publication_year(DEFAULT_PUBLICATION_YEAR)
                .publisher(DEFAULT_PUBLISHER)
                .image_s(DEFAULT_IMAGE_S)
                .image_m(DEFAULT_IMAGE_M)
                .image_l(DEFAULT_IMAGE_L);
        return books;
    }

    @Before
    public void initTest() {
        booksRepository.deleteAll();
        books = createEntity();
    }

    @Test
    public void createBooks() throws Exception {
        int databaseSizeBeforeCreate = booksRepository.findAll().size();

        // Create the Books

        restBooksMockMvc.perform(post("/api/books")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(books)))
                .andExpect(status().isCreated());

        // Validate the Books in the database
        List<Books> books = booksRepository.findAll();
        assertThat(books).hasSize(databaseSizeBeforeCreate + 1);
        Books testBooks = books.get(books.size() - 1);
        assertThat(testBooks.getIsbn()).isEqualTo(DEFAULT_ISBN);
        assertThat(testBooks.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testBooks.getAuthor()).isEqualTo(DEFAULT_AUTHOR);
        assertThat(testBooks.getPublication_year()).isEqualTo(DEFAULT_PUBLICATION_YEAR);
        assertThat(testBooks.getPublisher()).isEqualTo(DEFAULT_PUBLISHER);
        assertThat(testBooks.getImage_s()).isEqualTo(DEFAULT_IMAGE_S);
        assertThat(testBooks.getImage_m()).isEqualTo(DEFAULT_IMAGE_M);
        assertThat(testBooks.getImage_l()).isEqualTo(DEFAULT_IMAGE_L);
    }

    @Test
    public void getAllBooks() throws Exception {
        // Initialize the database
        booksRepository.save(books);

        // Get all the books
        restBooksMockMvc.perform(get("/api/books?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(books.getId())))
                .andExpect(jsonPath("$.[*].isbn").value(hasItem(DEFAULT_ISBN.toString())))
                .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
                .andExpect(jsonPath("$.[*].author").value(hasItem(DEFAULT_AUTHOR.toString())))
                .andExpect(jsonPath("$.[*].publication_year").value(hasItem(DEFAULT_PUBLICATION_YEAR)))
                .andExpect(jsonPath("$.[*].publisher").value(hasItem(DEFAULT_PUBLISHER.toString())))
                .andExpect(jsonPath("$.[*].image_s").value(hasItem(DEFAULT_IMAGE_S.toString())))
                .andExpect(jsonPath("$.[*].image_m").value(hasItem(DEFAULT_IMAGE_M.toString())))
                .andExpect(jsonPath("$.[*].image_l").value(hasItem(DEFAULT_IMAGE_L.toString())));
    }

    @Test
    public void getBooks() throws Exception {
        // Initialize the database
        booksRepository.save(books);

        // Get the books
        restBooksMockMvc.perform(get("/api/books/{id}", books.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(books.getId()))
            .andExpect(jsonPath("$.isbn").value(DEFAULT_ISBN.toString()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.author").value(DEFAULT_AUTHOR.toString()))
            .andExpect(jsonPath("$.publication_year").value(DEFAULT_PUBLICATION_YEAR))
            .andExpect(jsonPath("$.publisher").value(DEFAULT_PUBLISHER.toString()))
            .andExpect(jsonPath("$.image_s").value(DEFAULT_IMAGE_S.toString()))
            .andExpect(jsonPath("$.image_m").value(DEFAULT_IMAGE_M.toString()))
            .andExpect(jsonPath("$.image_l").value(DEFAULT_IMAGE_L.toString()));
    }

    @Test
    public void getNonExistingBooks() throws Exception {
        // Get the books
        restBooksMockMvc.perform(get("/api/books/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    public void updateBooks() throws Exception {
        // Initialize the database
        booksRepository.save(books);
        int databaseSizeBeforeUpdate = booksRepository.findAll().size();

        // Update the books
        Books updatedBooks = booksRepository.findOne(books.getId());
        updatedBooks
                .isbn(UPDATED_ISBN)
                .title(UPDATED_TITLE)
                .author(UPDATED_AUTHOR)
                .publication_year(UPDATED_PUBLICATION_YEAR)
                .publisher(UPDATED_PUBLISHER)
                .image_s(UPDATED_IMAGE_S)
                .image_m(UPDATED_IMAGE_M)
                .image_l(UPDATED_IMAGE_L);

        restBooksMockMvc.perform(put("/api/books")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedBooks)))
                .andExpect(status().isOk());

        // Validate the Books in the database
        List<Books> books = booksRepository.findAll();
        assertThat(books).hasSize(databaseSizeBeforeUpdate);
        Books testBooks = books.get(books.size() - 1);
        assertThat(testBooks.getIsbn()).isEqualTo(UPDATED_ISBN);
        assertThat(testBooks.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testBooks.getAuthor()).isEqualTo(UPDATED_AUTHOR);
        assertThat(testBooks.getPublication_year()).isEqualTo(UPDATED_PUBLICATION_YEAR);
        assertThat(testBooks.getPublisher()).isEqualTo(UPDATED_PUBLISHER);
        assertThat(testBooks.getImage_s()).isEqualTo(UPDATED_IMAGE_S);
        assertThat(testBooks.getImage_m()).isEqualTo(UPDATED_IMAGE_M);
        assertThat(testBooks.getImage_l()).isEqualTo(UPDATED_IMAGE_L);
    }

    @Test
    public void deleteBooks() throws Exception {
        // Initialize the database
        booksRepository.save(books);
        int databaseSizeBeforeDelete = booksRepository.findAll().size();

        // Get the books
        restBooksMockMvc.perform(delete("/api/books/{id}", books.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Books> books = booksRepository.findAll();
        assertThat(books).hasSize(databaseSizeBeforeDelete - 1);
    }
}
