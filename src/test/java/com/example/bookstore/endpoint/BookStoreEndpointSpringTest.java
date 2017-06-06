package com.example.bookstore.endpoint;

import com.example.bookstore.model.Book;
import com.example.bookstore.model.BookDto;
import com.example.bookstore.model.BookListingDto;
import com.example.bookstore.repository.BookRepository;
import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ActiveProfiles("test")
public class BookStoreEndpointSpringTest {
    @Autowired
    BookRepository bookRepository;

    TestRestTemplate restTemplate;

    @Before
    public void setUp() {
        bookRepository.deleteAll();
        restTemplate = new TestRestTemplate();
    }

    @Test
    public void shouldGetBookListing() {
        // given
        bookRepository.save(new Book("test title 1", "test author 1"));
        bookRepository.save(new Book("test title 2", "test author 2"));

        // when
        ResponseEntity<BookListingDto> bookListingEntity =
                restTemplate.getForEntity("http://localhost:8190/api/books",
                        BookListingDto.class);
        // then
        assertEquals(HttpStatus.OK,
                bookListingEntity.getStatusCode());
        assertEquals(new BookListingDto(
                        Lists.newArrayList(
                                new BookDto("test title 1", "test author 1"),
                                new BookDto("test title 2", "test author 2")
                        ), 2
                ),
                bookListingEntity.getBody());
    }

    @Test
    public void shouldAddBook() {
        // given
        bookRepository.save(new Book("title 1", "author 1"));

        // when
        ResponseEntity<BookDto> responseEntity = restTemplate.postForEntity("http://localhost:8190/api/books",
                new BookDto("title 2", "author 2"),
                BookDto.class);

        // then
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(new BookDto("title 2", "author 2"),
                responseEntity.getBody());
        assertEquals(2, bookRepository.findAll().size());
    }
}
