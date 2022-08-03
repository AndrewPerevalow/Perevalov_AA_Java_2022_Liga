package com.ligainternship.carwash.model.entitiy;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "bookings")
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class Booking {

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "start_time")
    private LocalDateTime startTime;

    @Column(name = "status")
    private String status;

    @Column(name = "discount_percent")
    private Double discount;

    @OneToMany(mappedBy = "booking")
    @Fetch(FetchMode.SUBSELECT)
    private List<Service> services;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "box_id")
    private Box box;
}
