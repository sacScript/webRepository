package com.example.demo.domain.entities;

import jakarta.persistence.*;
import lombok.*;
import java.time.OffsetDateTime;
import java.util.*;

@Entity
@Table(name = "bookings")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private OffsetDateTime createdAt;

    @ManyToOne(optional = false)
    @JoinColumn(name = "passenger_id", nullable = false)
    private Passenger passenger;

    @OneToMany(mappedBy = "booking")
    @Builder.Default
    private List<BookingItem> bookingItems = new ArrayList<>();

    public void addBookingItem(BookingItem item) {
        bookingItems.add(item);
        item.setBooking(this);
    }

}
