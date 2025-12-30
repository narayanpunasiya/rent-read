package com.crio.rent_read.dto;

import com.crio.rent_read.entity.AvailabilityStatus;
import lombok.Data;

@Data
public class BookRequest {
    private String title;
    private String author;
    private String genre;
    private AvailabilityStatus availabilityStatus; // Optional, default AVAILABLE
}
