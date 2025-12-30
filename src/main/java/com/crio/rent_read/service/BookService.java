package com.crio.rent_read.service;

import com.crio.rent_read.dto.BookRequest;
import com.crio.rent_read.entity.Book;

import java.util.List;

public interface BookService {
    Book createBook(BookRequest request);
    List<Book> getAllBooks();
    Book updateBook(Long id, BookRequest request);
    void deleteBook(Long id);
}
