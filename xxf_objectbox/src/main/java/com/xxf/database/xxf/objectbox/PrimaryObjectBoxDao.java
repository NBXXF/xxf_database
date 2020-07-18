package com.xxf.database.xxf.objectbox;

import com.xxf.database.xxf.objectbox.box.Empty;
import com.xxf.database.xxf.objectbox.box.PrimaryObjectBox;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import io.objectbox.BoxStore;
import io.objectbox.Property;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;

/**
 * @Description: 自定义主键更新, ObjextBox id  只能为long
 * @Author: XGod
 * @CreateDate: 2020/7/18 14:30
 */
@Deprecated
public class PrimaryObjectBoxDao<T extends PrimaryObjectBox>
        extends ObjectBoxDao<T> {
    public PrimaryObjectBoxDao(BoxStore boxStore) {
        super(boxStore);
    }

    @Override
    public final Observable<Empty> insert(final T... t) {
        if (t == null || t.length <= 0) {
            return insert(new ArrayList<T>());
        } else {
            return insert(Arrays.asList(t));
        }
    }

    @Override
    public final Observable<Empty> insert(Collection<T> t) {
        if (t == null || t.isEmpty()) {
            return Observable.just(Empty.INSTANCE);
        }
        final Map<String, T> insertMap = new HashMap<>();
        Property<T> primaryKeyProperty = null;
        for (T it : t) {
            insertMap.put(it.getPrimaryKey(), it);
            if (primaryKeyProperty == null) {
                primaryKeyProperty = it.getPrimaryKeyProperty();
            }
        }
        String[] key = new String[insertMap.keySet().size()];
        return query(primaryKeyProperty, insertMap.keySet().toArray(key))
                .onErrorReturnItem(new ArrayList<T>())
                .flatMap(new Function<List<T>, ObservableSource<Empty>>() {
                    @Override
                    public ObservableSource<Empty> apply(List<T> ts) throws Exception {
                        for (T itemInDb : ts) {
                            T insertItem = insertMap.get(itemInDb.getPrimaryKey());
                            if (insertItem != null) {
                                insertItem.setObjectBoxId(itemInDb.getObjectBoxId());
                            }
                        }
                        return PrimaryObjectBoxDao.super.insert(insertMap.values());
                    }
                });

    }

    @Override
    public Observable<Long> insert(final T t) {
        return Observable
                .defer(new Callable<ObservableSource<? extends Long>>() {
                    @Override
                    public ObservableSource<? extends Long> call() throws Exception {
                        Property<T> primaryKeyProperty = t.getPrimaryKeyProperty();
                        try {
                            T t1 = queryFirst(primaryKeyProperty, t.getPrimaryKey()).blockingFirst();
                            t.setObjectBoxId(t1.getObjectBoxId());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return PrimaryObjectBoxDao.super.insert(t);
                    }
                });
    }

}
