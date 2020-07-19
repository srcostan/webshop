package com.costan.webshop.persistence;

import java.util.List;

public interface Database {

    void persist(Object entity);

    <T> List<T> getAllRecords(Class<T> entityClass);
}
