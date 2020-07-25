package com.xxf.database.demo;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.relation.ToOne;

/**
 * @Description: java描述
 * @Author: XGod
 * @CreateDate: 2020/7/18 17:37
 */
@Entity
public class Child {
    @Id(assignable = true)
    private long _id;
    String name;
    String msg;

    public ToOne<TestBean> test;

    public ToOne<TestBean> getTest() {
        return test;
    }

    public void setTest(ToOne<TestBean> test) {
        this.test = test;
    }

    public Child(long _id, String name, String msg) {
        this._id = _id;
        this.name = name;
        this.msg = msg;
    }

    public long get_id() {
        return _id;
    }

    public void set_id(long _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "Child{" +
                "_id=" + _id +
                ", name='" + name + '\'' +
                ", msg='" + msg + '\'' +
                ", test=" + test +
                '}';
    }
}
