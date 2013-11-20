package com.springapp.mvc.utils;

import com.springapp.mvc.entity.Enterprise;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;

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

    public Sort.Direction getSortDirection(String direction) {
        Sort.Direction direction1 = null;
        try {
            direction1 = Sort.Direction.fromString(direction);
        } catch (Exception e) {
           e.getStackTrace();
        }
        return direction1;
    }

    public String getEnterpriseProperty(String fieldName) {
        String enterpriseProperty = null;
        if (checkEnterpriseFile(fieldName)) {
            enterpriseProperty = fieldName;
        }
        return enterpriseProperty;
    }

    private boolean checkEnterpriseFile(String fieldName) {
        for (Field field : Enterprise.class.getDeclaredFields()) {
            if (field.getName().equals(fieldName)) {
                return true;
            }
        }
        return false;
    }
}
