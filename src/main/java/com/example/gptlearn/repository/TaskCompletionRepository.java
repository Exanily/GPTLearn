package com.example.gptlearn.repository;

import com.example.gptlearn.entity.TaskCompletion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskCompletionRepository extends JpaRepository<TaskCompletion, Long> {

    Page<TaskCompletion>  findByKey_UserId(Long userId,
                                           Pageable pageable);
    int countTaskCompletionByKey_UserId(Long userId);
    int countTaskCompletionByKey_UserIdAndResult(Long userId, Boolean result);
}
