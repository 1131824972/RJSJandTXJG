package com.example.democlass01.repository;

import com.example.democlass01.entity.LogicEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;

/**
 * 实验3核心：通用逻辑删除DAO
 */
@NoRepositoryBean
public interface LogicDAO<T extends LogicEntity, ID extends Serializable> extends JpaRepository<T, ID> {
    @Override
    @Transactional
    @Modifying
    @Query("update #{#entityName} e set e.isDeleted = 1, e.deletedTime = current_timestamp where e.id = ?1 and e.isDeleted = 0")
    void deleteById(ID id);

    @Override
    @Transactional
    default void delete(T entity) {
        deleteById((ID) entity.getId());
    }

    @Override
    @Transactional
    default void deleteAll(Iterable<? extends T> entities) {
        entities.forEach(entity -> deleteById((ID) entity.getId()));
    }

}