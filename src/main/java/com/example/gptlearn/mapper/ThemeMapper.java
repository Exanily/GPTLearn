package com.example.gptlearn.mapper;

import com.example.gptlearn.entity.Theme;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ValueMapping;
import org.mapstruct.factory.Mappers;

import java.util.List;


@Mapper
public interface ThemeMapper {
    ThemeMapper INSTANCE = Mappers.getMapper(ThemeMapper.class);

    default String themeToString(Theme theme) {
        return theme.getName();
    }

    List<String> themesToStrings(List<Theme> themes);
}
