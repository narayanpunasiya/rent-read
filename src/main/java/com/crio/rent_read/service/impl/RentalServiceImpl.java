package com.crio.rent_read.service.impl;

import com.crio.rent_read.entity.AvailabilityStatus;
import com.crio.rent_read.entity.Book;
import com.crio.rent_read.entity.Rental;
import com.crio.rent_read.entity.User;
import com.crio.rent_read.exception.BadRequestException;
import com.crio.rent_read.exception.ResourceNotFoundException;
import com.crio.rent_read.repositories.BookRepository;
import com.crio.rent_read.repositories.RentalRepository;
import com.crio.rent_read.repositories.UserRepository;
import com.crio.rent_read.service.RentalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class RentalServiceImpl implements RentalService {

    private static final int MAX_ACTIVE_RENTALS = 2;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private RentalRepository rentalRepository;

    @Override
    public Rental rentBook(Long userId, Long bookId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found"));

        if (book.getAvailabilityStatus() != AvailabilityStatus.AVAILABLE) {
            throw new BadRequestException("Book is not available for rent");
        }

        List<Rental> activeRentals = rentalRepository.findByUserAndReturnDateIsNull(user);
        if (activeRentals.size() >= MAX_ACTIVE_RENTALS) {
            throw new BadRequestException("User has already reached maximum book rental limit!");
        }

        Rental rental = new Rental();
        rental.setUser(user);
        rental.setBook(book);
        rental.setRentedAt(LocalDate.now());
        rental.setReturnDate(null);

        book.setAvailabilityStatus(AvailabilityStatus.NOT_AVAILABLE);
        bookRepository.save(book);

        return rentalRepository.save(rental);
    }

    @Override
    public void returnBook(Long rentalId) {

        Rental rental = rentalRepository.findById(rentalId)
                .orElseThrow(() -> new ResourceNotFoundException("Rental not found"));

        if (rental.getReturnDate() != null) {
            throw new BadRequestException("Book has already been returned");
        }

        rental.setReturnDate(LocalDate.now());
        rentalRepository.save(rental);

        Book book = rental.getBook();
        book.setAvailabilityStatus(AvailabilityStatus.AVAILABLE);
        bookRepository.save(book);
    }

    @Override
    public List<Rental> getActiveRentalsByUser(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        return rentalRepository.findByUserAndReturnDateIsNull(user);
    }
}
