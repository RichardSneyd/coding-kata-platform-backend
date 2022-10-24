package com.bnta.codecompiler.components.converters;

import com.bnta.codecompiler.models.problems.Difficulty;
import org.springframework.core.convert.converter.Converter;

import java.lang.annotation.Annotation;

public class StringToEnumConverter implements Converter<String, Difficulty> {
    @Override
    public Difficulty convert(String source) {
        try {
            return Difficulty.valueOf(source.toUpperCase());
        }
        catch (IllegalArgumentException e) {
            return null;
        }
    }
}