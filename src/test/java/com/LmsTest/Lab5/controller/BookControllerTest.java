package com.LmsTest.Lab5.controller;

import com.LmsTest.Lab5.entity.Book;
import com.LmsTest.Lab5.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.Collections;
import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class BookControllerTest {

    @Mock
    private BookService bookService;

    @InjectMocks
    private BookController bookController;

    private Book book;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        book = new Book(1L, "Title", "Author", "ISBN123456", new Date(), true);
    }

    @Test
    void testGetAllBooks() {
        when(bookService.findAllBooks()).thenReturn(Collections.singletonList(book));

        ResponseEntity<?> response = bookController.getAllBooks();
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(Collections.singletonList(book), response.getBody());
    }

    @Test
    void testGetBookById() {
        when(bookService.findBookById(anyLong())).thenReturn(Optional.of(book));

        ResponseEntity<?> response = bookController.getBookById(1L);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(book, response.getBody());
    }

    @Test
    void testCreateBook() {
        when(bookService.saveBook(any(Book.class))).thenReturn(book);

        ResponseEntity<?> response = bookController.createBook(book);
        assertEquals(201, response.getStatusCodeValue());
        assertEquals(book, response.getBody());
    }

    @Test
    void updateBook_Success() {
        Book existingBook = new Book();
        existingBook.setId(1L);
        existingBook.setTitle("Original Title");

        Book updatedBook = new Book();
        updatedBook.setId(1L);
        updatedBook.setTitle("Updated Title");

        when(bookService.updateBook(1L, updatedBook)).thenReturn(Optional.of(updatedBook));

        ResponseEntity<Book> response = bookController.updateBook(1L, updatedBook);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Updated Title", response.getBody().getTitle());
    }

    @Test
    void updateBook_NotFound() {
        Book updatedBook = new Book();
        updatedBook.setId(1L);
        updatedBook.setTitle("Updated Title");

        when(bookService.updateBook(1L, updatedBook)).thenReturn(Optional.empty());

        ResponseEntity<Book> response = bookController.updateBook(1L, updatedBook);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }


    @Test
    void testDeleteBook_Success() {
        // Mock the service method to do nothing, as it should return void
        doNothing().when(bookService).deleteBook(anyLong());

        ResponseEntity<Void> response = bookController.deleteBook(1L);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }


    @Test
    void testDeleteBook_Exception() {
        // Mock the service to throw a RuntimeException
        doThrow(new RuntimeException("Internal Error")).when(bookService).deleteBook(anyLong());

        ResponseEntity<Void> response = bookController.deleteBook(1L);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }


    @Test
    void testGetBookById_NotFound() {
        when(bookService.findBookById(anyLong())).thenReturn(Optional.empty());

        ResponseEntity<Book> response = bookController.getBookById(1L);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }



}
