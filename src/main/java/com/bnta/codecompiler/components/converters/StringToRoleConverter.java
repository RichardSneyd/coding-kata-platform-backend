package com.bnta.codecompiler.components.converters;

import com.bnta.codecompiler.models.users.Role;
import org.springframework.core.convert.converter.Converter;

public class StringToRoleConverter implements Converter<String, Role> {
    @Override
    public Role convert(String source) {
        try {
            return Role.valueOf(source.toUpperCase());
        }
        catch (IllegalArgumentException e) {
            return null;
        }
    }
}