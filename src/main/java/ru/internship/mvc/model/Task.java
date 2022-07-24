package ru.internship.mvc.model;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

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

    @NotBlank(message = "Header should not be empty")
    private String header;

    @NotBlank(message = "Description should not be empty")
    private String description;

    @ManyToOne
    @JoinColumn(name = "id_user")
    private User user;

    @NotBlank(message = "Date should not be empty")
    private Date deadline;

    @NotBlank(message = "Status should not be empty")
    private String status;

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
