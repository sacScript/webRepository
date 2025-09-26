package com.example.demo.domain.entities;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

import com.example.demo.domain.enums.Cabin;



@Entity
@Table(name = "booking_items")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookingItem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Cabin cabin;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = false)
    private Integer segmentOrder;

    @ManyToOne(optional = false)
    @JoinColumn(name = "booking_id", nullable = false)
    private Booking booking;

    @ManyToOne(optional = false)
    @JoinColumn(name = "flight_id", nullable = false)
    private Flight flight;
}
