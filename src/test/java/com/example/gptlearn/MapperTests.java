package com.example.gptlearn;

import com.example.gptlearn.entity.*;
import com.example.gptlearn.entity.key.TaskCompletionKey;
import com.example.gptlearn.mapper.TaskCompletionMapper;
import com.example.gptlearn.mapper.TaskMapper;
import com.example.gptlearn.mapper.ThemeMapper;
import com.example.gptlearn.mapper.UserMapper;
import com.example.gptlearn.model.dto.TaskCompletionDto;
import com.example.gptlearn.model.dto.TaskDto;
import com.example.gptlearn.model.dto.ThemeDto;
import com.example.gptlearn.model.dto.UserDto;
import com.example.gptlearn.model.dto.enums.Complexity;
import com.example.gptlearn.model.response.ThemesResponse;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MapperTests extends AbstractTest {

    @Test
    void themeMapper() {
        Theme theme = new Theme(1L, "theme");
        ThemeDto expected = new ThemeDto(1L, "theme");

        ThemeDto actual = ThemeMapper.INSTANCE.themesToDto(theme);

        assertEquals(expected, actual);
    }

    @Test
    void themesResponseMapper() {
        Theme theme1 = new Theme(1L, "theme1");
        Theme theme2 = new Theme(2L, "theme2");
        ThemesResponse expected = ThemesResponse.builder()
                .themes(List.of(new ThemeDto(1L, "theme1"), new ThemeDto(2L, "theme2"))).build();
        List<Theme> themes = List.of(theme1, theme2);

        ThemesResponse actual = ThemesResponse.builder()
                .themes(ThemeMapper.INSTANCE.listThemesToListDto(themes)).build();

        assertEquals(expected, actual);
    }

    @Test
    void taskMapper() {
        Task task = createTask();
        TaskDto expected = createTaskDto();

        TaskDto actual = TaskMapper.INSTANCE.taskToDto(task);

        assertEquals(expected, actual);
    }

    @Test
    void taskCompletionMapper() {
        TaskCompletion taskCompletion = createTaskCompletion();
        TaskCompletionDto expected = createTaskCompletionDto();

        TaskCompletionDto actual = TaskCompletionMapper.INSTANCE.taskCompletionToDto(taskCompletion);

        assertEquals(expected, actual);
    }

    @Test
    void userMapper() {
        User user = createUser();
        UserDto expected = createUserDto();

        UserDto actual = UserMapper.INSTANCE.userToDto(user);

        assertEquals(expected, actual);
    }

    private Theme createTheme() {
        return new Theme(1L, "theme");
    }

    private final Long idTask = 1L;
    private final String title = "title";
    private final String solution = "solution";
    private final String hint = "hint";
    private final String description = "description";
    final Complexity complexity = Complexity.NORMAL;

    private Task createTask() {
        return new Task(idTask, createTheme(), Complexity.NORMAL, description, hint, solution, title);
    }

    private TaskDto createTaskDto() {
        return new TaskDto(idTask, ThemeMapper.INSTANCE.themesToDto(createTheme()), complexity.getValue(), description, hint, solution, title);
    }

    final Date date = new Date();
    final Boolean result = true;
    final String answer = "answer";
    private final Long idUser = 3L;

    private TaskCompletion createTaskCompletion() {
        long idTaskCompletion = 2L;
        return new TaskCompletion(new TaskCompletionKey(idUser, idTaskCompletion), date, result, answer, null, createTask());
    }

    private TaskCompletionDto createTaskCompletionDto() {
        return new TaskCompletionDto(date, result, answer, createTaskDto());
    }

    private final String username = "username";

    private final Role role = new Role(1L, "role");

    private User createUser() {
        return new User(idUser, username, null, date, List.of(createTaskCompletion()), List.of(role));
    }

    private UserDto createUserDto() {
        return new UserDto(username, List.of(role.getName()), List.of(createTaskCompletionDto()));
    }
}
