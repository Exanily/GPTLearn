package com.example.gptlearn.model.response;

import com.example.gptlearn.model.dto.ThemeDto;
import lombok.*;

import java.util.List;
import java.util.Objects;

@Builder
@Getter
public class ThemesResponse {
    private List<ThemeDto> themes;

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        ThemesResponse that = (ThemesResponse) object;
        return Objects.equals(themes, that.themes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(themes);
    }
}
