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
 * NoRepositoryBean  表示这是一个基类，Spring不会尝试实例化它，而是实例化它的子类
 */
@NoRepositoryBean
public interface LogicDAO<T extends LogicEntity, ID extends Serializable> extends JpaRepository<T, ID> {

    // 覆盖默认的 deleteById，将其转换为 Update 操作
    @Override
    @Transactional
    @Modifying
    @Query("update #{#entityName} e set e.isDeleted = 1, e.deletedTime = current_timestamp where e.id = ?1 and e.isDeleted = 0")
    void deleteById(ID id);

    // 覆盖默认的 delete(Entity)，本质也是调用 deleteById
    @Override
    @Transactional
    default void delete(T entity) {
        deleteById((ID) entity.getId());
    }

    // 覆盖批量删除
    @Override
    @Transactional
    default void deleteAll(Iterable<? extends T> entities) {
        entities.forEach(entity -> deleteById((ID) entity.getId()));
    }

    // 注意：findAll() 等查询方法不需要重写，因为你的 Entity 上已经加了 @Where(clause = "is_deleted=0")
}