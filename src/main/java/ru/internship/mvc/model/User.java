package ru.internship.mvc.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.validation.annotation.Validated;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@Validated
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String surname;
    private String patronymic;
    private String login;
    private String email;
    private String password;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    @Fetch(FetchMode.SUBSELECT)
    private List<Task> tasks;

    @JsonIgnore
    @ManyToMany
    @Fetch(FetchMode.SUBSELECT)
    @JoinTable(
            name = "users_projects",
            joinColumns = { @JoinColumn(name = "id_user") },
            inverseJoinColumns = { @JoinColumn(name = "id_project") }
    )
    private List<Project> projects;

    @JsonIgnore
    @ManyToMany
    @Fetch(FetchMode.SUBSELECT)
    @JoinTable(
            name = "users_roles",
            joinColumns = { @JoinColumn(name = "id_user") },
            inverseJoinColumns = { @JoinColumn(name = "id_role") }
    )
    private List<Role> roles;
}
