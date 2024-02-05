package com.oasis.assessment.models;


import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "task_category")
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
@Builder
public class TaskCategory {

    @Id
    @GeneratedValue
    @Column(name = "category_id")

    private Long id;
    @Column(name = "category_name",nullable = false)
    private String cateName;

    @OneToMany(mappedBy = "taskCategory",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @ToString.Exclude
    private List<Task> tasks;

}
