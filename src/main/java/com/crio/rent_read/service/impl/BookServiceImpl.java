package com.crio.rent_read.service.impl;

import java.util.List;
import com.crio.rent_read.dto.BookRequest;
import com.crio.rent_read.entity.AvailabilityStatus;
import com.crio.rent_read.entity.Book;
import com.crio.rent_read.exception.ResourceNotFoundException;
import com.crio.rent_read.repositories.BookRepository;
import com.crio.rent_read.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository bookRepository;

    @Override
    public Book createBook(BookRequest request) {
        Book book = new Book();
        book.setTitle(request.getTitle());
        book.setAuthor(request.getAuthor());
        book.setGenre(request.getGenre());
        book.setAvailabilityStatus(request.getAvailabilityStatus() != null ? request.getAvailabilityStatus() : AvailabilityStatus.AVAILABLE);
        return bookRepository.save(book);
    }

    @Override
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    public Book updateBook(Long id, BookRequest request) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Book not found"));
        book.setTitle(request.getTitle());
        book.setAuthor(request.getAuthor());
        book.setGenre(request.getGenre());
        book.setAvailabilityStatus(request.getAvailabilityStatus() != null ? request.getAvailabilityStatus() : AvailabilityStatus.AVAILABLE);
        return bookRepository.save(book);
    }

    @Override
    public void deleteBook(Long id) {
        Book book = bookRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Book not found"));
        bookRepository.delete(book);
    }
}
