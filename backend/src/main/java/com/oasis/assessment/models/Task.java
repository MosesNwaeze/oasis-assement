package com.oasis.assessment.models;


import com.oasis.assessment.enums.CompletionStatusEnum;
import com.oasis.assessment.enums.PriorityEnum;
import com.oasis.assessment.enums.StatusEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.sql.Timestamp;

@Entity
@Table(name = "tasks")
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
@Builder
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "task_id")
    private  Long id;

    @NotBlank(message = "Task title field cannot be empty")
    @Size(max = 255, min = 4,message = "Last name cannot be less than 4 and greater than 255 characters")
    private String title;

    @NotBlank(message = "Description field cannot be empty")
    @Size(max = 300, min = 10,message = "Last name cannot be less than 10 and greater than 300 characters")
    private String description;


    @Enumerated(EnumType.STRING)
    @NotNull(message = "Task priority field cannot be null")
    private PriorityEnum priority;

    @NotNull(message = "Due date cannot be null")
    private Timestamp dueDate;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Completion status field cannot be null")
    private CompletionStatusEnum completionStatus;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Status cannot be null")
    private StatusEnum status;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private AppUser appUserModel;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private TaskCategory taskCategory;

}
