package com.LmsTest.Lab5;

import com.LmsTest.Lab5.service.BookService; // Adjust the package according to your actual service package
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class Lab5ApplicationTests {

	@Autowired
	private BookService bookService;

	@Test
	void contextLoads() {
		// This test will pass if the application context loads successfully
	}

	@Test
	void bookServiceShouldBeLoaded() {
		// Verify that the BookService bean is loaded into the application context
		assertThat(bookService).isNotNull();
	}

	// Add more specific tests if needed
}
