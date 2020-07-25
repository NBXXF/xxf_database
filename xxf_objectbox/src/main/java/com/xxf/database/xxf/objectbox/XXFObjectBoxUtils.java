package com.xxf.database.xxf.objectbox;

import androidx.annotation.NonNull;
import androidx.annotation.WorkerThread;

import com.xxf.database.xxf.objectbox.id.MurmurHash;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import io.objectbox.Box;


/**
 * @Description: objectBox 扩展
 * @Author: XGod
 * @CreateDate: 2020/7/25 22:55
 */
public class XXFObjectBoxUtils {

    private XXFObjectBoxUtils() {
    }

    /**
     * 生成id
     *
     * @param id
     * @return
     */
    public static long generateId(String id) {
        return MurmurHash.hash32(id);
    }

    @WorkerThread
    public static <T> void put(@NonNull Box<T> box, @NonNull List<T> insertData, @NonNull ListMergeFunction<T> mergeFunction) throws Exception {
        Objects.requireNonNull(box);
        Objects.requireNonNull(insertData);
        Objects.requireNonNull(mergeFunction);
        List<Long> ids = new ArrayList<>();
        for (T t : insertData) {
            ids.add(box.getId(t));
        }
        Map<Long, T> insertedMap = box.getMap(ids);
        List<T> apply = mergeFunction.apply(insertData, insertedMap);
        box.put(apply);
    }

    @WorkerThread
    public static <T> void put(@NonNull Box<T> box, @NonNull T insertData, @NonNull MergeFunction<T> mergeFunction) throws Exception {
        Objects.requireNonNull(box);
        Objects.requireNonNull(insertData);
        Objects.requireNonNull(mergeFunction);
        T inserted = box.get(box.getId(insertData));
        T apply = mergeFunction.apply(insertData, inserted);
        box.put(apply);
    }
}
