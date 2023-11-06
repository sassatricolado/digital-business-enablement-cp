package br.com.fiap.taskapi.model;

import br.com.fiap.taskapi.dto.CreateTaskDto;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title;
    private String description;
    private Boolean status;
    private Date date;
    private Boolean isActive;

    public Task(CreateTaskDto data) {
        this.title = data.title();
        this.description = data.description();
        this.dueDate = data.dueDate();
        this.status = false;
        this.isActive = true;
    }

    public Task() {}
}
