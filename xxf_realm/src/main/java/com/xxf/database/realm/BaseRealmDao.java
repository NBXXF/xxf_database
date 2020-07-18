package com.xxf.database.realm;


import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import io.realm.Case;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmMigration;
import io.realm.RealmModel;
import io.realm.RealmObject;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * Description
 * author  youxuan  E-mail:xuanyouwu@163.com
 * date createTime：2017/4/6
 * version 1.0.0
 */
public class BaseRealmDao<T extends RealmModel> implements IRealmDao<T> {


    protected Realm realm;

    public BaseRealmDao(Realm realm) {
        this.realm = realm;
    }

    public BaseRealmDao(@NonNull String realmName, @NonNull byte[] encryptionKey, int schemaVersion, @NonNull RealmMigration migration, Object realmModule) {
        this(Realm.getInstance(new RealmConfiguration.Builder()
                .name(realmName)
                .encryptionKey(encryptionKey)
                .schemaVersion(schemaVersion)
                .deleteRealmIfMigrationNeeded()
                .migration(migration)
                .modules(realmModule)
                .build()));
    }

    public BaseRealmDao(@NonNull String realmName, int schemaVersion, @NonNull RealmMigration migration, Object realmModule) {
        this(Realm.getInstance(new RealmConfiguration.Builder()
                .name(realmName)
                .schemaVersion(schemaVersion)
                .deleteRealmIfMigrationNeeded()
                .migration(migration)
                .modules(realmModule)
                .build()));
    }

    @Override
    public boolean isClosed() {
        return realm == null || realm.isClosed();
    }

    @Override
    public <E extends RealmModel> List<E> copyFromRealm(Iterable<E> realmObjects) throws Exception {
        if (isClosed() || realmObjects == null) return new ArrayList<>();
        return realm.copyFromRealm(realmObjects);
    }

    @Override
    public <E extends RealmModel> E copyFromRealm(E e) throws Exception {
        if (isClosed() || e == null) return null;
        return realm.copyFromRealm(e);
    }


    @Override
    public void close() {
        if (!isClosed()) {
            realm.close();
        }
    }

    @Override
    public T insertFromJson(Class<T> c, String json) throws Exception {
        if (isClosed()) return null;
        return realm.createObjectFromJson(c, json);
    }

    @Override
    public final T insert(T t) throws Exception {
        if (isClosed()) return null;
        T retlT = null;
        try {
            realm.beginTransaction();
            retlT = realm.copyToRealm(t);
            realm.commitTransaction();
        } catch (Exception e) {
            e.printStackTrace();
            realm.cancelTransaction();
            throw e;
        }
        return retlT;
    }

    @Override
    public T insertOrUpdate(T t) throws Exception {
        if (isClosed()) return null;
        T retlT = null;
        try {
            realm.beginTransaction();
            retlT = realm.copyToRealmOrUpdate(t);
            realm.commitTransaction();
        } catch (Exception e) {
            e.printStackTrace();
            realm.cancelTransaction();
            throw e;
        }
        return retlT;
    }


    @Override
    public List<T> insert(Iterable<T> objects) throws Exception {
        if (isClosed()) return null;
        List<T> ts = null;
        try {
            realm.beginTransaction();
            ts = realm.copyToRealm(objects);
            realm.commitTransaction();
        } catch (Exception e) {
            e.printStackTrace();
            realm.cancelTransaction();
            throw e;
        }
        return ts;
    }

    @Override
    public void insertAsyn(final Iterable<T> objects) {
        if (isClosed()) return;
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm bgRealm) {
                bgRealm.copyToRealm(objects);
            }
        });
    }

    @Override
    public List<T> insertOrUpdate(Iterable<T> objects) throws Exception {
        if (isClosed()) return null;
        List<T> ts = null;
        try {
            realm.beginTransaction();
            ts = realm.copyToRealmOrUpdate(objects);
            realm.commitTransaction();
        } catch (Exception e) {
            e.printStackTrace();
            realm.cancelTransaction();
            throw e;
        }
        return ts;
    }

    @Override
    public void insertOrUpdateAsyn(final Iterable<T> objects) {
        if (isClosed()) return;
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm bgRealm) {
                try {
                    bgRealm.copyToRealmOrUpdate(objects);
                } catch (io.realm.exceptions.RealmException e) {
                    e.printStackTrace();
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void delete(Class<T> c, String key, String value) {
        if (isClosed()) return;
        final RealmResults<T> all = realm.where(c).equalTo(key, value).findAll();
        try {
            realm.beginTransaction();
            all.deleteAllFromRealm();
            realm.commitTransaction();
        } catch (Exception e) {
            e.printStackTrace();
            realm.cancelTransaction();
        }
    }

    @Override
    public void delete(Class<T> c, String fieldName, String[] values) {
        if (isClosed()) return;
        final RealmResults<T> all = realm.where(c).in(fieldName, values).findAll();
        try {
            realm.beginTransaction();
            all.deleteAllFromRealm();
            realm.commitTransaction();
        } catch (Exception e) {
            e.printStackTrace();
            realm.cancelTransaction();
        }
    }


    @Override
    public void delete(Class<T> c) {
        if (isClosed()) return;
        try {
            realm.beginTransaction();
            realm.delete(c);
            realm.commitTransaction();
        } catch (Exception e) {
            e.printStackTrace();
            realm.cancelTransaction();
        }
    }

    @Override
    public void delete(T t) throws Exception {
        if (isClosed()) return;
        try {
            realm.beginTransaction();
            RealmObject.deleteFromRealm(t);
            realm.commitTransaction();
        } catch (Exception e) {
            e.printStackTrace();
            realm.cancelTransaction();
            throw e;
        }
    }

    @Override
    public RealmResults<T> queryAll(Class<T> c) {
        if (isClosed()) return null;
        return realm.where(c).findAll();
    }


    @Override
    public RealmResults<T> queryAll(Class<T> c, long limit) {
        if (isClosed()) return null;
        return realm.where(c).findAll();
    }

    @Override
    public RealmResults<T> queryAll(Class<T> c, String fieldName, Sort sortOrder) {
        if (isClosed()) return null;
        return realm.where(c).findAllSorted(fieldName, sortOrder);
    }

    @Override
    public RealmResults<T> queryAllAsync(Class<T> c) {
        if (isClosed()) return null;
        return realm.where(c).findAllAsync();
    }

    @Override
    public RealmResults<T> query(Class<T> c, String fieldName, String value) {
        if (isClosed()) return null;
        return realm.where(c).equalTo(fieldName, value).findAll();
    }

    @Override
    public RealmResults<T> contains(Class<T> c, String fieldName, String value) {
        if (isClosed()) return null;
        return realm.where(c).contains(fieldName, value).findAll();
    }

    @Override
    public RealmResults<T> contains(Class<T> c, String fieldName, String value, Case casing) {
        if (isClosed()) return null;
        return realm.where(c).contains(fieldName, value, casing).findAll();
    }

    @Override
    public T queryFirst(Class<T> c, String fieldName, String value) {
        if (isClosed()) return null;
        return realm.where(c).equalTo(fieldName, value).findFirst();
    }

    @Override
    public RealmResults<T> queryAll(RealmQuery<T> query) {
        if (isClosed()) return null;
        return query.findAll();
    }

    @Override
    public T queryFirst(RealmQuery<T> query) {
        if (isClosed()) return null;
        return query.findFirst();
    }


}
