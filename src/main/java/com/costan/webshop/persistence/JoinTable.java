package com.costan.webshop.persistence;

import java.util.Objects;

class JoinTable {
    private Class first;
    private Class second;

    public JoinTable(Class first, Class second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JoinTable joinTable = (JoinTable) o;
        return Objects.equals(first, joinTable.first) &&
                Objects.equals(second, joinTable.second);
    }

    @Override
    public int hashCode() {
        return Objects.hash(first, second);
    }
}
