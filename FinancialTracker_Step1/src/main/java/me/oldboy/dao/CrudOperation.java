package me.oldboy.dao;

import java.util.List;
import java.util.Optional;

/**
 * Generic interface for data access objects (DAOs) providing basic CRUD operations.
 *
 * @param <K> the type of the entity's identifier
 * @param <T> the type of the entity
 */
public interface CrudOperation <K, T>{
    List<T> findAll();

    Optional<T> findById(K id);

    boolean delete(K id);

    void update(T entity);

    T save(T entity);
}
