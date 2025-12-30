package com.crio.rent_read.service;

import com.crio.rent_read.entity.Rental;

import java.util.List;

public interface RentalService {

    Rental rentBook(Long userId, Long bookId);

    void returnBook(Long rentalId);

    List<Rental> getActiveRentalsByUser(Long userId);
}
