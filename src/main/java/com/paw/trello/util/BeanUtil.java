package com.paw.trello.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BeanUtil {

    private static class LazyHolder {

        static final BeanUtil INSTANCE = new BeanUtil();
    }

    public static BeanUtil getInstance() {
        return LazyHolder.INSTANCE;
    }

    public BeanUtil() {
        super();
    }

    public void copyProperties(final Object source, Object target) throws IllegalAccessException {
        Class sourceClass = source.getClass();
        Class targetClass = target.getClass();
        List<Field> sourceFields = new ArrayList<>(Arrays.asList(sourceClass.getDeclaredFields()));
        List<Field> targetFields = new ArrayList<>(Arrays.asList(targetClass.getDeclaredFields()));
        sourceFields.stream().forEach(e -> e.setAccessible(true));
        targetFields.stream().forEach(e -> e.setAccessible(true));
        List<Field> filtered = new ArrayList<>();
        for (Field sourceField : sourceFields) {
            if (sourceField.get(source) != null) {
                filtered.add(sourceField);
            }
        }
        for (Field targetField : targetFields) {
            for (Field field : filtered) {
                if (targetField.getName().equals(field.getName())) {
                    targetField.set(target, field.get(source));
                }
            }
        }
    }
}
