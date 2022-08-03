package com.ligainternship.carwash.model.entitiy;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "services")
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class Service {

    @Id
    @Column(name ="id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "lead_time_minutes")
    private Long leadTime;

    @Column(name = "price")
    private Double price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "booking_id")
    private Booking booking;
}
