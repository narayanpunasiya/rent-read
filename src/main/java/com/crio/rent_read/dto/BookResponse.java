package com.crio.rent_read.dto;

import com.crio.rent_read.entity.AvailabilityStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BookResponse {
    private Long id;
    private String title;
    private String author;
    private String genre;
    private AvailabilityStatus availabilityStatus;
}
