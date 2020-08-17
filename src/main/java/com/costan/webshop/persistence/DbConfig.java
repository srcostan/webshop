package com.costan.webshop.persistence;

public class DbConfig {

    private String path;
    private String columnSeparator;
    private String tableNameSeparator;
    private String tableNameSuffix;

    public DbConfig(String path, String columnSeparator, String tableNameSeparator,
                    String tableNameSuffix) {
        this.path = path;
        this.columnSeparator = columnSeparator;
        this.tableNameSeparator = tableNameSeparator;
        this.tableNameSuffix = tableNameSuffix;
    }

    public String getPath() {
        return path;
    }

    public String getColumnSeparator() {
        return columnSeparator;
    }

    public String getTableNameSeparator() {
        return tableNameSeparator;
    }

    public String getTableNameSuffix() {
        return tableNameSuffix;
    }
}
