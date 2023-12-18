package com.example.gptlearn.controller;

import com.example.gptlearn.mapper.ThemeMapper;
import com.example.gptlearn.model.response.ThemesResponse;
import com.example.gptlearn.service.ThemeService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/theme")
@RequiredArgsConstructor
public class ThemeController {

    private final ThemeService themeService;

    @PostMapping
    @Secured({"ROLE_ADMIN", "ROLE_MODER"})
    public void add(@RequestParam("name") String name) {
        themeService.add(name);
    }

    @GetMapping
    public ThemesResponse getAll() {
        return ThemesResponse.builder()
                .themes(ThemeMapper.INSTANCE.listThemesToListDto(themeService.findAll())).build();
    }

    @DeleteMapping
    @Secured({"ROLE_ADMIN", "ROLE_MODER"})
    public void delete(@RequestParam("name") String name) {
        themeService.delete(name);
    }

    @DeleteMapping("/{id}")
    @Secured({"ROLE_ADMIN", "ROLE_MODER"})
    public void delete(@PathVariable("id") Long id) {
        themeService.delete(id);
    }

}
