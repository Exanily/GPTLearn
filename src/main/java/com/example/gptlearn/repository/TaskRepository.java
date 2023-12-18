package com.example.gptlearn.repository;

import com.example.gptlearn.entity.Task;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    @NonNull Page<Task> findAll(@NonNull Pageable pageable);
}
