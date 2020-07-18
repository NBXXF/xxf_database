package com.xxf.database.xxf.objectbox.box;

import io.objectbox.Property;

/**
 * @Description: String 主键
 * @Author: XGod
 * @CreateDate: 2020/7/18 12:48
 */
public interface PrimaryObjectBox extends IdObjectBox {

    String getPrimaryKey();

    Property getPrimaryKeyProperty();
}
