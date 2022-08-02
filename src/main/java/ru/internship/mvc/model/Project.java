package ru.internship.mvc.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

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


    private String header;
    private String description;

    @JsonIgnore
    @ManyToMany(mappedBy = "projects")
    private List<User> userList;

    @JsonIgnore
    @OneToMany(mappedBy = "project")
    @Fetch(FetchMode.SUBSELECT)
    private List<Task> tasks;
}
