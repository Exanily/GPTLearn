package com.example.gptlearn.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table
@Getter
@Setter
public class Theme {
    @Id
    @Column(name = "name")
    private String name;

    public Theme(String name) {
        this.name = name;
    }

    public Theme() {

    }
}
