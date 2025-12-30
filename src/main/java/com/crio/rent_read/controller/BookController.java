package com.crio.rent_read.controller;

import com.crio.rent_read.dto.BookRequest;
import com.crio.rent_read.dto.BookResponse;
import com.crio.rent_read.entity.Book;
import com.crio.rent_read.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookService bookService;

    // ADMIN only
    @PostMapping
    public ResponseEntity<BookResponse> createBook(@RequestBody BookRequest request) {
        Book book = bookService.createBook(request);
        BookResponse response = new BookResponse(book.getId(), book.getTitle(), book.getAuthor(), book.getGenre(), book.getAvailabilityStatus());
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // All authenticated users
    @GetMapping
    public ResponseEntity<List<BookResponse>> getAllBooks() {
        List<BookResponse> books = bookService.getAllBooks().stream()
                .map(book -> new BookResponse(book.getId(), book.getTitle(), book.getAuthor(), book.getGenre(), book.getAvailabilityStatus()))
                .collect(Collectors.toList());
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    // ADMIN only
    @PutMapping("/{id}")
    public ResponseEntity<BookResponse> updateBook(@PathVariable Long id, @RequestBody BookRequest request) {
        Book book = bookService.updateBook(id, request);
        BookResponse response = new BookResponse(book.getId(), book.getTitle(), book.getAuthor(), book.getGenre(), book.getAvailabilityStatus());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // ADMIN only
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
