package com.ligainternship.carwash.model.entitiy;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

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

    @ManyToMany(mappedBy = "services")
    private List<Booking> bookings;
}
