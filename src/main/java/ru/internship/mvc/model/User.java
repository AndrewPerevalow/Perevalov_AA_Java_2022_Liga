package ru.internship.mvc.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.validation.annotation.Validated;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.util.List;

@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
@Validated
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Name should not be empty")
    @Pattern(regexp = "[A-Za-zА-Яа-я]{3,29}", message = "Wrong name")
    private String name;

    @NotEmpty(message = "Surname should not be empty")
    @Pattern(regexp = "[A-Za-zА-Яа-я]{3,29}", message = "Wrong surname")
    private String surname;

    @NotEmpty(message = "Patronymic should not be empty")
    @Pattern(regexp = "[A-Za-zА-Яа-я]{3,29}", message = "Wrong patronymic")
    private String patronymic;

    @NotEmpty(message = "Login should not be empty")
    @Pattern(regexp = "[A-Za-zА-Яа-я]{3,29}", message = "Wrong login")
    private String login;

    @NotEmpty(message = "Email should not be empty")
    @Email(message = "Wrong email")
    private String email;

    @NotEmpty(message = "Password should not be empty")
    private String password;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<Task> tasks;

    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "users_projects",
            joinColumns = { @JoinColumn(name = "id_user") },
            inverseJoinColumns = { @JoinColumn(name = "id_project") }
    )
    private List<Project> projects;
}
