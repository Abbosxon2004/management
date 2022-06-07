package uz.pdp.online.management.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Task {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false,length = 3000)
    private String taskDefinition;

    @Column(nullable = false)
    private Date deadline;

    @ManyToOne
    private Employees employee;

    @OneToOne
    private TaskStatus taskStatus;

    @CreatedBy
    private UUID createdBy;
}
