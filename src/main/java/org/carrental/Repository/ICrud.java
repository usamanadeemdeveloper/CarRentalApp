package org.carrental.Repository;

import java.util.List;

public interface ICrud<T> {
    Long insert(T object);
    List<T> getAll();

    T getById(Long id);
    void update(T obj, Long id);
    void deleteById(String status,Long id);

}
