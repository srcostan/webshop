package com.costan.webshop.persistence;

import java.util.List;
import java.util.Optional;

public interface Database {

    void persist(Object entity);

    <T> List<T> getAllRecords(Class<T> entityClass);

    <T> Optional<T> getEntityByPrimaryKey(Class<T> entityClass, Object primaryKey);

    <T> void deleteAllRecords(Class<T> entityClass);
}
