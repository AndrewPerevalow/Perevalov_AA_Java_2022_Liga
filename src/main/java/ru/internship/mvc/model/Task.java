package ru.internship.mvc.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "tasks")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Header should not be empty")
    private String header;

    @NotEmpty(message = "Description should not be empty")
    private String description;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "id_user")
    private User user;

    @NotEmpty(message = "Date should not be empty")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date deadline;

    @NotEmpty(message = "Status should not be empty")
    private String status;

    @JsonIgnore
    @OneToMany(mappedBy = "task")
    private List<Comment> commentList;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "id_project")
    private Project project;

    @Override
    public String toString() {
        return "Задание: " +
                "id = " + id +
                "; Заголовок = '" + header + '\'' +
                "; Описание = '" + description + '\'' +
                "; Срок выполения = " + deadline +
                "; Статус = '" + status + '\'' + '.' + '\n';
    }
}
