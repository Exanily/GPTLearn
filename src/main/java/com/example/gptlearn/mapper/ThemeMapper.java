package com.example.gptlearn.mapper;

import com.example.gptlearn.entity.Theme;

import com.example.gptlearn.model.dto.ThemeDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;


@Mapper
public interface ThemeMapper {
    ThemeMapper INSTANCE = Mappers.getMapper(ThemeMapper.class);

    default String themeToString(Theme theme) {
        return theme.getName();
    }

     ThemeDto themesToDto(Theme theme);

    List<ThemeDto> listThemesToListDto(List<Theme> themes);
}
