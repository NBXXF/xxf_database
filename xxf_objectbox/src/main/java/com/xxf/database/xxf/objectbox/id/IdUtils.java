package com.xxf.database.xxf.objectbox.id;

/**
 * @Description: 生成id
 * @Author: XGod
 * @CreateDate: 2020/7/18 16:36
 */
public class IdUtils {

    /**
     * 转换id
     *
     * @param id
     * @return
     */
    public static long generateId(String id) {
        return MurmurHash.hash32(id);
    }
}
