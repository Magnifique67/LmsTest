package com.LmsTest.Lab5.controller;

import com.LmsTest.Lab5.entity.Book;
import com.LmsTest.Lab5.repository.BookRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class BookControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private Book testBook;

    @BeforeEach
    public void setUp() {
        bookRepository.deleteAll();
        testBook = new Book();
        testBook.setTitle("Test Book");
        testBook.setAuthor("Test Author");
        testBook.setIsbn("123456789");
        testBook.setAvailability(true);
        bookRepository.save(testBook);
    }

    @Test
    public void testGetAllBooks() throws Exception {
        mockMvc.perform(get("/books"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value(testBook.getTitle()));
    }

    @Test
    public void testGetBookById() throws Exception {
        mockMvc.perform(get("/books/" + testBook.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(testBook.getTitle()));
    }

    @Test
    public void testCreateBook() throws Exception {
        Book newBook = new Book();
        newBook.setTitle("New Book");
        newBook.setAuthor("New Author");
        newBook.setIsbn("987654321");
        newBook.setAvailability(true);

        mockMvc.perform(post("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newBook)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("New Book"));
    }

    @Test
    public void testUpdateBook() throws Exception {
        testBook.setTitle("Updated Title");

        mockMvc.perform(put("/books/" + testBook.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testBook)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Updated Title"));
    }

    @Test
    public void testDeleteBook() throws Exception {
        mockMvc.perform(delete("/books/" + testBook.getId()))
                .andExpect(status().isNoContent());
    }
}
