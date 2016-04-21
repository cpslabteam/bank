package cpslab.bank.util.db;

import java.util.List;

public interface GenericDAO<T> {

    T findById(Long id);

    List<T> findAll();

    List<T> findByExample(T exampleInstance, String[] excludeProperty);

    T update(T entity);

    T persist(T entity);

    void delete(T entity);
}
