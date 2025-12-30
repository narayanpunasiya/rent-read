package com.crio.rent_read.repositories;

import java.util.List;
import com.crio.rent_read.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RentalRepository extends JpaRepository<Rental, Long> {
    List<Rental> findByUserAndReturnDateIsNull(User user);
}

