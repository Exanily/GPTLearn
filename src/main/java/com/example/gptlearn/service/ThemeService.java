package com.example.gptlearn.service;

import com.example.gptlearn.entity.Theme;
import com.example.gptlearn.exception.ThemeDuplicateException;
import com.example.gptlearn.exception.ThemeNotFoundException;
import com.example.gptlearn.repository.ThemeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ThemeService {

    private final ThemeRepository themeRepository;
    private final static String THEME_NOT_FOUND = "Тема не найдена";
    private final static String THEME_EXISTS = "Тема существует";

    public Theme findByName(String name) {
        return themeRepository.findByName(name).orElseThrow(() -> new ThemeNotFoundException(THEME_NOT_FOUND));
    }

    public List<Theme> findAll() {
        return themeRepository.findAll();
    }

    @Transactional
    public Theme add(String name) {
        checkForDuplicate(name);
        Theme theme = new Theme();
        theme.setName(name);
        return themeRepository.save(theme);
    }

    @Transactional
    public void delete(String name) {
        themeRepository.deleteByName(name);
    }
    @Transactional
    public void delete(Long id) {
        themeRepository.deleteById(id);
    }
    private void checkForDuplicate(String name) {
        try {
            findByName(name);
            throw new ThemeDuplicateException(THEME_EXISTS);
        } catch (ThemeNotFoundException ignored) {
        }
    }

}
