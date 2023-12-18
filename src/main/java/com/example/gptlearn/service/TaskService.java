package com.example.gptlearn.service;

import com.example.gptlearn.entity.Task;
import com.example.gptlearn.exception.TaskNotFoundException;
import com.example.gptlearn.mapper.TaskMapper;
import com.example.gptlearn.model.dto.PageDto;
import com.example.gptlearn.model.response.TasksResponse;
import com.example.gptlearn.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;

    public Task add(Task task) {
        return taskRepository.save(task);
    }

    public void delete(Long id) {
        taskRepository.deleteById(id);
    }

    public Task getTaskById(Long id) {
        return taskRepository.findById(id).orElseThrow(() -> new TaskNotFoundException("Задание не найдено"));
    }

    public TasksResponse getTaskAll(int pageNumber, int pageSize) {
        Sort sort = Sort.by("id").descending();
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Task> page = taskRepository.findAll(pageable);
        return TasksResponse
                .builder()
                .tasks(TaskMapper.INSTANCE.taskListToList(page.toList()))
                .page(new PageDto(
                        page.getTotalPages(),
                        page.getTotalElements(),
                        page.getNumber(),
                        page.isEmpty(),
                        page.isFirst(),
                        page.isLast()
                ))
                .build();
    }

    public List<Task> addAll(List<Task> tasks) {
        return taskRepository.saveAll(tasks);
    }
}
