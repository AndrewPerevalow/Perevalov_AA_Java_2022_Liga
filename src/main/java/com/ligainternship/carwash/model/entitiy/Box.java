package com.ligainternship.carwash.model.entitiy;

import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.time.LocalTime;
import java.util.List;

@Entity
@Table(name = "boxes")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class Box {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "ratio")
    private Double ratio;

    @Column(name = "work_from_time")
    private LocalTime workFromTime;

    @Column(name = "work_to_time")
    private LocalTime workToTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "box")
    @Fetch(FetchMode.SUBSELECT)
    private List<Booking> bookings;

    public Long getId() {
        return id;
    }
}
