package ru.internship.mvc.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
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
    private String header;
    private String description;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user")
    private User user;

    private Date deadline;
    private String status;

    @JsonIgnore
    @OneToMany(mappedBy = "task")
    @Fetch(FetchMode.SUBSELECT)
    private List<Comment> commentList;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
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
