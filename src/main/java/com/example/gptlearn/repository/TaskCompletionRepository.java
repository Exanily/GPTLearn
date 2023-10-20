package com.example.gptlearn.repository;

import com.example.gptlearn.entity.TaskCompletion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskCompletionRepository extends JpaRepository<TaskCompletion, Long> {

}
