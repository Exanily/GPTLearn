package com.example.gptlearn.entity;

import com.example.gptlearn.model.dto.enums.Complexity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "task")
@AllArgsConstructor
@NoArgsConstructor
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @JoinColumn(name = "theme", referencedColumnName = "name")
    @OneToOne(cascade = CascadeType.ALL)
    private Theme theme;

    @Enumerated(EnumType.STRING)
    private Complexity complexity;

    @Column(name = "description")
    private String description;

    @Column(name = "hint")
    private String hint;

    @Column(name = "solution")
    private String solution;

    @Column(name = "title")
    private String title;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "task_id", insertable = false, updatable = false)
    private List<TaskCompletion> taskCompletionList;

}