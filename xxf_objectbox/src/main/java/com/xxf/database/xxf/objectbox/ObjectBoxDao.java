package com.xxf.database.xxf.objectbox;

import com.xxf.database.xxf.objectbox.box.Empty;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Callable;

import io.objectbox.BoxStore;
import io.objectbox.Property;
import io.objectbox.query.Query;
import io.objectbox.query.QueryBuilder;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;

/**
 * @Description: Dao
 * @Author: XGod
 * @CreateDate: 2017/4/6
 */
public class ObjectBoxDao<T> implements IObjectBoxDao<T> {

    private BoxStore boxStore;

    public ObjectBoxDao(@NonNull BoxStore boxStore) {
        this.boxStore = Objects.requireNonNull(boxStore);
    }

    @Override
    public boolean isClosed() {
        return boxStore == null || boxStore.isClosed();
    }

    /**
     * 如果子类有多个泛型 需要复写
     *
     * @return
     */
    @Override
    public Class<T> getObjectBoxClass() {
        Class<T> entityClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        return entityClass;
    }

    @Override
    public Observable<Long> insert(final T t) {
        return Observable
                .fromCallable(new Callable<Long>() {
                    @Override
                    public Long call() throws Exception {
                        return boxStore.boxFor(getObjectBoxClass()).put(t);
                    }
                })
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<Empty> insert(final T... t) {
        return Observable
                .fromCallable(new Callable<Empty>() {
                    @Override
                    public Empty call() throws Exception {
                        boxStore.boxFor(getObjectBoxClass()).put(t);
                        return Empty.INSTANCE;
                    }
                })
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<Empty> insert(final Collection<T> t) {
        return Observable
                .fromCallable(new Callable<Empty>() {
                    @Override
                    public Empty call() throws Exception {
                        boxStore.boxFor(getObjectBoxClass()).put(t);
                        return Empty.INSTANCE;
                    }
                })
                .subscribeOn(Schedulers.io());
    }


    @Override
    public Observable<Boolean> delete(final T t) {
        return Observable
                .fromCallable(new Callable<Boolean>() {
                    @Override
                    public Boolean call() throws Exception {
                        return boxStore.boxFor(getObjectBoxClass()).remove(t);
                    }
                })
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<Empty> delete(final T... t) {
        return Observable
                .fromCallable(new Callable<Empty>() {
                    @Override
                    public Empty call() throws Exception {
                        boxStore.boxFor(getObjectBoxClass()).remove(t);
                        return Empty.INSTANCE;
                    }
                })
                .subscribeOn(Schedulers.io());
    }


    @Override
    public Observable<Empty> delete(final Collection<T> t) {
        return Observable
                .fromCallable(new Callable<Empty>() {
                    @Override
                    public Empty call() throws Exception {
                        boxStore.boxFor(getObjectBoxClass()).remove(t);
                        return Empty.INSTANCE;
                    }
                })
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<Empty> clear() {
        return Observable
                .fromCallable(new Callable<Empty>() {
                    @Override
                    public Empty call() throws Exception {
                        boxStore.boxFor(getObjectBoxClass()).removeAll();
                        return Empty.INSTANCE;
                    }
                })
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<Long> count() {
        return Observable
                .fromCallable(new Callable<Long>() {
                    @Override
                    public Long call() throws Exception {
                        return boxStore.boxFor(getObjectBoxClass()).count();
                    }
                })
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<List<T>> query() {
        return Observable
                .fromCallable(new Callable<List<T>>() {
                    @Override
                    public List<T> call() throws Exception {
                        return boxStore.boxFor(getObjectBoxClass()).query().build().find();
                    }
                })
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<List<T>> observable() {
        return RxQuery.observable(boxStore.boxFor(getObjectBoxClass()).query().build());
    }

    @Override
    public Observable<List<T>> observable(Query<T> query) {
        return RxQuery.observable(query);
    }

    @Override
    public Observable<List<T>> query(final long offset, final long limit) {
        return Observable
                .fromCallable(new Callable<List<T>>() {
                    @Override
                    public List<T> call() throws Exception {
                        return boxStore.boxFor(getObjectBoxClass()).query().build().find(offset, limit);
                    }
                })
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<List<T>> query(final Property<T> property, final String... values) {
        return Observable
                .fromCallable(new Callable<List<T>>() {
                    @Override
                    public List<T> call() throws Exception {
                        return boxStore.boxFor(getObjectBoxClass()).query().in(property, values).build().find();
                    }
                })
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<T> queryFirst(final Property<T> property, final String value) {
        return Observable
                .fromCallable(new Callable<T>() {
                    @Override
                    public T call() throws Exception {
                        return boxStore.boxFor(getObjectBoxClass())
                                .query()
                                .equal(property, value)
                                .build()
                                .findFirst();
                    }
                })
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<List<T>> contains(final Property<T> property, final String value) {
        return Observable
                .fromCallable(new Callable<List<T>>() {
                    @Override
                    public List<T> call() throws Exception {
                        return boxStore.boxFor(getObjectBoxClass()).query().contains(property, value).build().find();
                    }
                })
                .subscribeOn(Schedulers.io());
    }

    @Override
    public QueryBuilder<T> buildQuery() {
        return boxStore.boxFor(getObjectBoxClass()).query();
    }

    @Override
    public Observable<T> queryFirst(final QueryBuilder<T> query) {
        return Observable.fromCallable(new Callable<T>() {
            @Override
            public T call() throws Exception {
                return query.build().findFirst();
            }
        }).subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<List<T>> query(final QueryBuilder<T> query) {
        return Observable.fromCallable(new Callable<List<T>>() {
            @Override
            public List<T> call() throws Exception {
                return query.build().find();
            }
        }).subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<List<T>> query(final QueryBuilder<T> query, final long offset, final long limit) {
        return Observable.fromCallable(new Callable<List<T>>() {
            @Override
            public List<T> call() throws Exception {
                return query.build().find(offset, limit);
            }
        }).subscribeOn(Schedulers.io());
    }

    @Override
    public void close() throws IOException {
        if (boxStore != null && !boxStore.isClosed()) {
            boxStore.close();
        }
    }
}
