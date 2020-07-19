package com.costan.webshop.persistence;

import com.costan.webshop.persistence.annotation.DbColumn;
import com.costan.webshop.persistence.annotation.DbEntity;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

class DatabaseImpl implements Database {

    private static final String DB_DIRECTORY_PATH = "database_tables/";
    private static final String TABLE_SUFFIX = "_table";
    private static final String COLUMN_SEPARATOR = "--^_^--";

    private Map<Class, Path> entityClassTablePaths;

    public DatabaseImpl() {
        entityClassTablePaths = new HashMap<>();
    }

    public void registerEntity(Class entityClass) {
        if (!entityClass.isAnnotationPresent(DbEntity.class)) {
            throw new IllegalArgumentException("class is not a db entity");
        }
        try {
            Path tablePath = getTablePathFromEntityClass(entityClass);
            entityClassTablePaths.put(entityClass, tablePath);
            Files.createDirectory(Paths.get(DB_DIRECTORY_PATH));
            Files.createFile(tablePath);
        } catch (FileAlreadyExistsException existsException) {
            return;
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public void persist(Object entity) {
        if (!entity.getClass().isAnnotationPresent(DbEntity.class)) {
            throw new IllegalArgumentException("object is not a db entity");
        }
        String row = getRowToWrite(entity);
        Path tablePath = entityClassTablePaths.get(entity.getClass());
        try {
            Files.write(tablePath, row.getBytes(), StandardOpenOption.APPEND);
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public <T> List<T> getAllRecords(Class<T> entityClass) {
        Path tablePath = entityClassTablePaths.get(entityClass);
        if (tablePath == null) {
            return new ArrayList<>();
        }
        try {
            List<String> rows = Files.readAllLines(tablePath);
            return rows.stream().map(r -> createEntityFromRow(entityClass, r)).collect(Collectors.toList());
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    private <T> T createEntityFromRow(Class<T> entityClass, String row) {
        String[] fieldValues = row.split(COLUMN_SEPARATOR);
        int i = 0;
        try {
            T entity = entityClass.getConstructor().newInstance();
            for (Field field : entity.getClass().getDeclaredFields()) {
                if (field.isAnnotationPresent(DbColumn.class)) {
                    Class typeClass = field.getType();
                    Constructor typeConstructor = typeClass.getConstructor(String.class);
                    try {
                        Object value = typeConstructor.newInstance(fieldValues[i]);
                        field.setAccessible(true);
                        field.set(entity, value);
                        field.setAccessible(false);
                        i++;
                    } catch (IllegalAccessException e) {
                        throw new IllegalArgumentException(e);
                    }
                }
            }
            return entity;
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    private String getRowToWrite(Object entity) {
        StringBuilder sb = new StringBuilder("");
        List<String> fieldValues = new ArrayList<>();
        for (Field field : entity.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(DbColumn.class)) {
                fieldValues.add(getFieldValue(entity, field));
            }
        }
        return String.join(COLUMN_SEPARATOR, fieldValues) + System.lineSeparator();
    }

    private String getFieldValue(Object entity, Field field) {
        try {
            field.setAccessible(true);
            String fieldValue = String.valueOf(field.get(entity));
            field.setAccessible(false);
            return fieldValue;
        } catch (IllegalAccessException e) {
            throw new IllegalArgumentException(e);
        }
    }

    private Path getTablePathFromEntityClass(Class entityClass) {
        return Paths.get(DB_DIRECTORY_PATH + entityClass.getSimpleName() + TABLE_SUFFIX);
    }
}
