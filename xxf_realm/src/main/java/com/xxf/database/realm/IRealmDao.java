package com.xxf.database.realm;

import java.util.List;

import io.realm.Case;
import io.realm.RealmModel;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * Description
 * Company Beijing icourt
 * author  youxuan  E-mail:xuanyouwu@163.com
 * date createTime：2017/4/6
 * version 1.0.0
 */
public interface IRealmDao<T extends RealmModel> extends AutoCloseable {

    boolean isClosed();

    <E extends RealmModel> List<E> copyFromRealm(Iterable<E> realmObjects) throws Exception;

    <E extends RealmModel> E copyFromRealm(E e) throws Exception;

    T insertFromJson(Class<T> c, String json) throws Exception;

    T insert(T t) throws Exception;

    T insertOrUpdate(T t) throws Exception;

    List<T> insert(Iterable<T> objects) throws Exception;

    void insertAsyn(Iterable<T> objects);

    List<T> insertOrUpdate(Iterable<T> objects) throws Exception;

    void insertOrUpdateAsyn(Iterable<T> objects);

    void delete(T t) throws Exception;

    void delete(Class<T> c, String fieldName, String value);

    void delete(Class<T> c, String fieldName, String[] values);

    void delete(Class<T> c);

    RealmResults<T> queryAll(Class<T> c);

    RealmResults<T> queryAll(Class<T> c, long limit);

    RealmResults<T> queryAll(Class<T> c, String fieldName, Sort sortOrder);

    RealmResults<T> queryAllAsync(Class<T> c);

    RealmResults<T> query(Class<T> c, String fieldName, String value);

    RealmResults<T> contains(Class<T> c, String fieldName, String value);

    RealmResults<T> contains(Class<T> c, String fieldName, String value, Case casing);

    T queryFirst(Class<T> c, String fieldName, String value);

    RealmResults<T> queryAll(RealmQuery<T> query);

    T queryFirst(RealmQuery<T> query);
}
