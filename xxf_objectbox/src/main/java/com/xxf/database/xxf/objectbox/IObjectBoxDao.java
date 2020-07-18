package com.xxf.database.xxf.objectbox;


import com.xxf.database.xxf.objectbox.box.Empty;

import java.util.Collection;
import java.util.List;

import io.objectbox.Property;
import io.objectbox.query.Query;
import io.objectbox.query.QueryBuilder;
import io.reactivex.Observable;

/**
 * @Description: dao
 * @Author: XGod
 * @CreateDate: 2020/7/18 12:54
 */
interface IObjectBoxDao<T> {
    /**
     * 获取class对象
     *
     * @return
     */
    Class<T> getObjectBoxClass();

    /**
     * 插入
     * @param t
     * @return
     */
    Observable<Long> insert(T t);

    /**
     * 批量插入
     * @param t
     * @return
     */
    Observable<Empty> insert(T... t);

    /**
     * 批量插入
     * @param t
     * @return
     */
    Observable<Empty> insert(Collection<T> t);

    /**
     * 删除
     * @param t
     * @return
     */
    Observable<Boolean> delete(T t);

    /**
     * 批量删除
     * @param t
     * @return
     */
    Observable<Empty> delete(T... t);

    /**
     * 批量删除
     * @param t
     * @return
     */
    Observable<Empty> delete(Collection<T> t);

    /**
     * 全部删除
     * @return
     */
    Observable<Empty> clear();

    Observable<Long> count();

    Observable<List<T>> query();

    /**
     * 观察数据变化
     *
     * @return
     */
    Observable<List<T>> observable();


    /**
     * 观察数据变化
     *
     * @return
     */
    Observable<List<T>> observable(Query<T> query);


    /**
     * 查询
     * @param offset
     * @param limit
     * @return
     */
    Observable<List<T>> query(long offset, long limit);

    /**
     * 多条件查询
     * @param property
     * @param values
     * @return
     */
    Observable<List<T>> query(Property<T> property, String... values);

    /**
     * equals 查询
     * @param property
     * @param value
     * @return
     */
    Observable<T> queryFirst(Property<T> property, String value);

    /**
     * 模糊查询
     * @param property
     * @param value
     * @return
     */
    Observable<List<T>> contains(Property<T> property, String value);

    /**
     * 构建查询条件
     *
     * @return
     */
    QueryBuilder<T> buildQuery();

    /**
     * 查询第一个
     * @param query
     * @return
     */
    Observable<T> queryFirst(QueryBuilder<T> query);

    /**
     * 查询
     * @param query
     * @return
     */
    Observable<List<T>> query(QueryBuilder<T> query);

    /**
     * 按条件查询
     * @param query
     * @param offset
     * @param limit
     * @return
     */
    Observable<List<T>> query(QueryBuilder<T> query, long offset, long limit);
}
