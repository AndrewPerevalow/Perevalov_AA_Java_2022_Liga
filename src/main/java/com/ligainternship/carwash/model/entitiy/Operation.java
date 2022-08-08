package com.ligainternship.carwash.model.entitiy;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "operations")
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class Operation {

    @Id
    @Column(name ="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "lead_time_minutes")
    private Integer leadTime;

    @Column(name = "price")
    private Double price;

    @ManyToMany(mappedBy = "operations")
    private List<Booking> bookings;
}
