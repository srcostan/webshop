package com.costan.webshop.persistence;

import com.costan.webshop.persistence.annotation.DbColumn;
import com.costan.webshop.persistence.annotation.DbEntity;
import com.costan.webshop.persistence.annotation.DbManyToMany;
import com.costan.webshop.persistence.annotation.Id;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.nio.channels.FileChannel;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Collectors;

public class DatabaseImpl implements Database {

    private static final String DB_DIRECTORY_PATH = "database_tables/";
    private static final String TABLE_SUFFIX = "_table";
    private static final String TABLE_NAME_SEPARATOR = "_";
    private static final String COLUMN_SEPARATOR = "###";

    private final Map<Class, Path> entityClassTablePaths;
    private final Map<JoinTable, Path> joinTablePaths;

    public DatabaseImpl() {
        entityClassTablePaths = new HashMap<>();
        joinTablePaths = new HashMap<>();
    }

    public void registerEntity(Class entityClass) {
        if (!entityClass.isAnnotationPresent(DbEntity.class)) {
            throw new IllegalArgumentException("class is not a db entity");
        }
        try {
            createTableDirectory();
            registerEntityTable(entityClass);
            registerJoinTables(entityClass);
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

    @Override
    public <T> Optional<T> getEntityByPrimaryKey(Class<T> entityClass, Object primaryKey) {
        return getAllRecords(entityClass)
                .stream().filter(entity -> getPrimaryKeyValue(entity).equals(primaryKey))
                .findFirst();
    }

    public <T> void deleteAllRecords(Class<T> entityClass) {
        Path tablePath = entityClassTablePaths.get(entityClass);
        try {
            FileChannel.open(tablePath, StandardOpenOption.WRITE).truncate(0).close();
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    private void registerEntityTable(Class entityClass) throws IOException {
        Path tablePath = getTablePathFromEntityClass(entityClass);
        entityClassTablePaths.put(entityClass, tablePath);
        try {
            Files.createFile(tablePath);
        } catch (FileAlreadyExistsException e) {
            //continue normal execution
        }
    }

    private void registerJoinTables(Class entityClass) throws IOException {
        for (Field field : entityClass.getDeclaredFields()) {
            if (field.isAnnotationPresent(DbManyToMany.class)) {
                ParameterizedType listType = (ParameterizedType) field.getGenericType();
                Class<?> toJoinEntityType = (Class<?>) listType.getActualTypeArguments()[0];
                Path joinTablePath = getJoinTablePathFromEntityClasses(entityClass, toJoinEntityType);
                joinTablePaths.put(new JoinTable(entityClass, toJoinEntityType), joinTablePath);
                try {
                    Files.createFile(joinTablePath);
                } catch (FileAlreadyExistsException e) {
                    //continue normal execution
                }
            }
        }
    }

    private void createTableDirectory() throws IOException {
        if (!Files.exists(Paths.get(DB_DIRECTORY_PATH))) {
            Files.createDirectory(Paths.get(DB_DIRECTORY_PATH));
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
                        String cleanFieldValue = fieldValues[i].replace(System.lineSeparator(), "");
                        Object value = typeConstructor.newInstance(cleanFieldValue);
                        field.setAccessible(true);
                        field.set(entity, value);
                        field.setAccessible(false);
                        i++;
                    } catch (IllegalAccessException e) {
                        throw new IllegalArgumentException(e);
                    }
                } else if (field.isAnnotationPresent(DbManyToMany.class)) {
                    ParameterizedType listType = (ParameterizedType) field.getGenericType();
                    Class<?> toJoinEntityType = (Class<?>) listType.getActualTypeArguments()[0];
                    String entityId = getPrimaryKeyValue(entity).toString();
                    Path joinTablePath = joinTablePaths.get(new JoinTable(entity.getClass(), toJoinEntityType));
                    List<String> joinTablePathsRows = Files.readAllLines(joinTablePath);
                    List<String> toJoinEntityIds = joinTablePathsRows.stream()
                            .filter(r -> r.split(COLUMN_SEPARATOR)[0].equals(entityId))
                            .map(r -> r.split(COLUMN_SEPARATOR)[1]).collect(Collectors.toList());

                    List<?> toJoinEntities = getAllRecords(toJoinEntityType).stream()
                            .filter(ent -> toJoinEntityIds.contains(getPrimaryKeyValue(ent).toString()))
                            .collect(Collectors.toList());
                    field.setAccessible(true);
                    field.set(entity, toJoinEntities);
                    field.setAccessible(false);
                }
            }
            return entity;
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    private Object getPrimaryKeyValue(Object entity) {
        for (Field field : entity.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(Id.class)) {
                try {
                    field.setAccessible(true);
                    Object value = field.get(entity);
                    field.setAccessible(false);
                    return value;
                } catch (IllegalAccessException e) {
                    throw new IllegalArgumentException(e);
                }
            }
        }
        throw new IllegalArgumentException("Primary key not found");
    }

    private String getRowToWrite(Object entity) {
        return Arrays.asList(entity.getClass().getDeclaredFields())
                .stream().filter(f -> f.isAnnotationPresent(DbColumn.class))
                .map(f -> getFieldValue(entity, f))
                .collect(Collectors.joining(COLUMN_SEPARATOR)) + System.lineSeparator();
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

    private Path getJoinTablePathFromEntityClasses(Class firstEntityClass, Class secondEntityClass) {
        return Paths.get(DB_DIRECTORY_PATH + firstEntityClass.getSimpleName()
                + TABLE_NAME_SEPARATOR + secondEntityClass.getSimpleName() + TABLE_SUFFIX);
    }
}
