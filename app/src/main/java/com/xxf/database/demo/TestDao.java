package com.xxf.database.demo;

import android.content.Context;

import com.xxf.database.xxf.objectbox.ObjectBoxDao;

import java.io.File;

import io.objectbox.BoxStore;

/**
 * @Description: java描述
 * @Author: XGod
 * @CreateDate: 2020/7/18 17:40
 */
public class TestDao extends ObjectBoxDao<TestBean> {
    public TestDao(Context context) {
        // super(MyObjectBox.builder().androidContext(context).build());
        super(MyObjectBox.builder().directory(new File(context.getCacheDir(), "testBox")).build());
    }
}
