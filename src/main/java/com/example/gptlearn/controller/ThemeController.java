package com.example.gptlearn.controller;

import com.example.gptlearn.service.ThemeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/theme")
@RequiredArgsConstructor
public class ThemeController {

    private final ThemeService themeService;

    @PostMapping
    public ResponseEntity<?> add(@RequestParam String theme) {
        try {
            return ResponseEntity.ok(themeService.add(theme));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> getAll() {
        try {
            return ResponseEntity.ok(themeService.findAll());
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping
    public ResponseEntity<?> delete(@RequestParam String theme) {
        try {
            themeService.delete(theme);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
