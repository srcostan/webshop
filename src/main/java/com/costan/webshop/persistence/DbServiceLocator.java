package com.costan.webshop.persistence;

public class DbServiceLocator {

    private static Database database;

    private static final String[] entityClassNames = new String[] {
      "com.costan.webshop.business.domain.Product",
      "com.costan.webshop.business.domain.Category"
    };

    public Database getDatabase() {
        if (database == null) {
            try {
                DatabaseImpl dbImpl = new DatabaseImpl();
                registerEntities(dbImpl);
                database = dbImpl;
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return database;
    }

    private void registerEntities(DatabaseImpl dbImpl) throws ClassNotFoundException {
        for (String className : entityClassNames) {
            Class entityClass = Class.forName(className);
            dbImpl.registerEntity(entityClass);
        }
    }
}
