package com.crio.rent_read.controller;

import com.crio.rent_read.entity.Rental;
import com.crio.rent_read.service.RentalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rentals")
public class RentalController {

    @Autowired
    private RentalService rentalService;

    // Rent a book
    @PostMapping("/users/{userId}/books/{bookId}")
    public ResponseEntity<Rental> rentBook(@PathVariable Long userId, @PathVariable Long bookId) {
        return ResponseEntity.status(201).body(rentalService.rentBook(userId, bookId));
    }

    // Return a book
    @PutMapping("/{rentalId}")
    public ResponseEntity<Void> returnBook(@PathVariable Long rentalId) {
        rentalService.returnBook(rentalId);
        return ResponseEntity.noContent().build();
    }

    // Get active rentals for a user
    @GetMapping("/active-rentals/users/{userId}")
    public ResponseEntity<List<Rental>> getActiveRentals(@PathVariable Long userId) {
        return ResponseEntity.ok(rentalService.getActiveRentalsByUser(userId));
    }
}
