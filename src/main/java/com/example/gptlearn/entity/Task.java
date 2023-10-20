package com.example.gptlearn.entity;

import com.example.gptlearn.model.dto.Complexity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "task")
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

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "hint")
    private String hint;

    @Column(name = "solution")
    private String solution;

}