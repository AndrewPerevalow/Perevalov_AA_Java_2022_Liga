package ru.internship.mvc.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Entity
@Table(name = "projects")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Header should not be empty")
    private String header;

    @NotEmpty(message = "Description should not be empty")
    private String description;

    @JsonIgnore
    @ManyToMany(mappedBy = "projects")
    private List<User> userList;

    @JsonIgnore
    @OneToMany(mappedBy = "project")
    private List<Task> tasks;
}
