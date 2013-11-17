package com.springapp.mvc.utils;

import org.springframework.stereotype.Component;

@Component
public class UrlParameterValidator {
    public UrlParameterValidator() {
    }

    public boolean getBoolean(String value) {
        return Boolean.parseBoolean(value);
    }

    public int getInt(String value) {
        try {
            return Math.abs(Integer.parseInt(value));
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}
