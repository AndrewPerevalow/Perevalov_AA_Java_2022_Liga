package com.ligainternship.carwash.model.entitiy;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "users")
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class User {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @Column(name = "login")
    private String login;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "is_exist")
    private boolean isExist;

    @ManyToMany
    @Fetch(FetchMode.SUBSELECT)
    @JsonIgnore
    @JoinTable(
            name = "users_roles",
            joinColumns = { @JoinColumn(name = "user_id") },
            inverseJoinColumns = { @JoinColumn(name = "role_id") }
    )
    private List<Role> roles;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    @Fetch(FetchMode.SUBSELECT)
    private List<Box> boxes;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    @Fetch(FetchMode.SUBSELECT)
    private List<Booking> bookings;

}
