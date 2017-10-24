package com.paw.trello.repositories;

import java.util.List;

public interface Repository<T> {
    List<T> getItems();
    T getItem(Long id);
    void createItem(T item);
    void updateItem(T item);
    boolean existItem(Long id, String tableName);
    void removeItem(Long id, String tableName);
}
