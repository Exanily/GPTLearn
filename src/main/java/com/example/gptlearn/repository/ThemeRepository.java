package com.example.gptlearn.repository;

import com.example.gptlearn.entity.Theme;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ThemeRepository extends JpaRepository<Theme, String> {
    Optional<Theme> findByName(String name);

}
