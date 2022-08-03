package com.ligainternship.carwash.model.entitiy;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.time.LocalTime;
import java.util.List;

@Entity
@Table(name = "boxes")
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class Box {

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "ratio")
    private Double ratio;

    @Column(name = "work_from_time")
    private LocalTime workFromTime;

    @Column(name = "work_to_time")
    private LocalTime workToTime;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @OneToMany(mappedBy = "box")
    @Fetch(FetchMode.SUBSELECT)
    private List<Booking> bookings;
}
