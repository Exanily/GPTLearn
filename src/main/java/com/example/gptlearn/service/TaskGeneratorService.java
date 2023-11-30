package com.example.gptlearn.service;

import com.example.gptlearn.entity.Task;
import com.example.gptlearn.entity.Theme;
import com.example.gptlearn.model.dto.enums.Complexity;
import com.example.gptlearn.model.response.ChatResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
@Log4j2
public class TaskGeneratorService {
    private final ChatGPTService gptService;
    private final TaskService taskService;
    private final ThemeService themeService;


    @Transactional
    public List<Task> generate(String themeString, Complexity complexity, int n) {
        Theme theme = themeService.findByName(themeString);
        return generate(theme, complexity, n);
    }

    @Transactional
    public List<Task> generate(Theme theme, Complexity complexity, int n) {
        String request = patternTask(theme.getName(), complexity);
        ChatResponse response = gptService.getResponse(request, n);

        List<Task> tasks = response.getChoices().stream().map(choice -> choice.getMessage().getContent())
                .map(content -> stringToTask(content, theme, complexity)).toList();

        return taskService.addAll(tasks);
    }

    @Transactional
    public boolean checkAnswer(Long id, String answer) {
        Task task = taskService.getTaskById(id);
        return checkAnswer(task, answer);
    }

    @Transactional
    public boolean checkAnswer(Task task, String answer) {
        String request = patternAnswer(task.getDescription(), answer);
        boolean b = Boolean.parseBoolean(gptService.getResponse(request, 1).getChoices().get(0).getMessage().getContent());
        System.out.println(request);
        return b;
    }

    private Task stringToTask(String taskString, Theme theme, Complexity complexity) {
        TaskBuilder taskBuilder = new TaskBuilder(taskString);

        Task task = new Task();
        task.setComplexity(complexity);
        task.setTheme(theme);
        task.setTitle(taskBuilder.getTitle());
        task.setDescription(taskBuilder.getDescription());
        task.setHint(taskBuilder.getHint());
        task.setSolution(taskBuilder.getSolution());
        return task;
    }

    private String patternTask(String theme, Complexity complexity) {
        return "Придумай задание по теме \"" + theme + "\", сложность \"" + complexity.getValue() + "\", с заголовком," +
                "заданием, подсказкой и ответом";
    }

    private String patternAnswer(String task, String answer) {
        return "Ответь на задание только сообщением \"true\" или \"false\"\n" +
                "Задание: " + task + "\n" +
                "Мой ответ: " + answer;
    }

    private static class TaskBuilder {
        private final String text;
        private final static String STRING_TITLE = "Заголовок:";
        private final static String STRING_DESCRIPTION = "Задание:";
        private final static String STRING_HINT = "Подсказка:";
        private final static String STRING_SOLUTION = "Ответ:";
        private final Map<TaskEnum, String> map = new HashMap<>();

        public TaskBuilder(String text) {
            this.text = text;
            builder();
        }

        private void builder() {
            List<Struct> indexes = Stream.of(
                            new Struct(STRING_TITLE, getIndexOf(STRING_TITLE), TaskEnum.TITLE),
                            new Struct(STRING_DESCRIPTION, getIndexOf(STRING_DESCRIPTION), TaskEnum.DESCRIPTION),
                            new Struct(STRING_HINT, getIndexOf(STRING_HINT), TaskEnum.HINT),
                            new Struct(STRING_SOLUTION, getIndexOf(STRING_SOLUTION), TaskEnum.SOLUTION))
                    .sorted().toList();
            for (int i = 0; i < indexes.size(); i++) {
                Struct struct = indexes.get(i);
                String s;
                if (i != indexes.size() - 1) {
                    s = substr(struct, indexes.get(i + 1));
                } else {
                    s = substr(struct);
                }
                map.put(struct.TaskEnum, s);
            }
        }


        private int getIndexOf(String string) {
            return this.text.indexOf(string);
        }

        private String substr(Struct structFrom, Struct structTo) {
            return this.text.substring(structFrom.index + structFrom.constName.length() + 1, structTo.index)
                    .trim();
        }

        private String substr(Struct structFrom) {
            return this.text.substring(structFrom.index + structFrom.constName.length() + 1)
                    .trim();
        }

        public String getTitle() {
            return getValueToMap(TaskEnum.TITLE);
        }

        public String getDescription() {
            return getValueToMap(TaskEnum.DESCRIPTION);
        }

        public String getHint() {
            return getValueToMap(TaskEnum.HINT);
        }

        public String getSolution() {
            return getValueToMap(TaskEnum.SOLUTION);
        }

        private String getValueToMap(TaskEnum TaskEnum) {
            return map.get(TaskEnum);
        }

        @Getter
        @Setter
        @AllArgsConstructor
        private static class Struct implements Comparable<Struct> {
            private String constName;
            private Integer index;
            private TaskEnum TaskEnum;

            @Override
            public int compareTo(Struct o) {
                return this.index - o.getIndex();
            }
        }

        private enum TaskEnum {
            TITLE,
            DESCRIPTION,
            HINT,
            SOLUTION
        }
    }
}
