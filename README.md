# Library Management System Test (LMSTest) - Project Documentation

## Overview

The Library Management System Test (LMSTest) This documentation provides an overview of the project, including features, testing strategies, and instructions for setup and usage.

## Project Features

### 1. Unit Testing

- **Tool/Framework:** JUnit
- **Description:** Unit tests verify the functionality of individual components and methods. Key areas tested include:
    - **BookController:** CRUD operations for managing books.
    - **BookService:** Business logic for book management.

**Example Unit Test:**
```@Test
void testGetAllBooks() {
    when(bookService.findAllBooks()).thenReturn(Collections.singletonList(book));
    ResponseEntity<?> response = bookController.getAllBooks();
    assertEquals(200, response.getStatusCodeValue());
    assertEquals(Collections.singletonList(book), response.getBody());
}
```

### 2. Integration Testing
-**Tool/Framework:** JUnit
**Description:** Integration tests validate the interaction between modules. Scenarios tested include:
-**Adding and updating books
-**Performing transactions
**Example**

```@Test
void testCreateBook() {
    when(bookService.saveBook(any(Book.class))).thenReturn(book);
    ResponseEntity<?> response = bookController.createBook(book);
    assertEquals(201, response.getStatusCodeValue());
    assertEquals(book, response.getBody());
}
```

### 3.  Mocking and Stubbing
-**Tool/Framework:** Mockito
-**Description:** Mockito is used for mocking dependencies and isolating components during testing.

**Example**
```@Mock
private BookService bookService;

@InjectMocks
private BookController bookController;

@BeforeEach
void setUp() {
    MockitoAnnotations.openMocks(this);
}
```


### 4. Parameterized Testing
-**Tool/Framework:** JUnit Parameterized Tests
-**Description:** Tests are written with various input combinations to ensure robustness.

**Example**
```@ParameterizedTest
@ValueSource(longs = {1L, 2L, 3L})
void testGetBookById(Long id) {
when(bookService.findBookById(id)).thenReturn(Optional.of(book));
ResponseEntity<?> response = bookController.getBookById(id);
assertEquals(200, response.getStatusCodeValue());
assertEquals(book, response.getBody());
}
```

### 5. Exception Handling
   -**Tool/Framework:** JUnit
   -**Description:** Tests ensure that exceptions are handled gracefully.
   **Example Exception Handling Test:**

```@Test
void testGetBookByIdNotFound() {
    when(bookService.findBookById(anyLong())).thenReturn(Optional.empty());
    ResponseEntity<?> response = bookController.getBookById(999L);
    assertEquals(404, response.getStatusCodeValue());
}
```
### 6. Code Coverage Analysis
   -**Tool/Framework:** JaCoCo
   -**Description:** JaCoCo is used to measure code coverage and ensure high coverage.
   **Generating Code Coverage Report:**
```
mvn jacoco:report
```
### Getting Started
#### Prerequisites
- Java Development Kit
- Maven/Gradle: For build and dependency management
- JUnit: For testing
- Mockito: For mocking
- JaCoCo: For code coverage

**run**
```
mvn clean install
```
**then**
```
mvn test
```
**Generating Code Coverage Report**
```
mvn jacoco:report
```

### Loom video

### https://www.loom.com/share/420011ebb0f34957aed61aaf627aa1bb
