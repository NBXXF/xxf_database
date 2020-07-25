package com.xxf.database.demo;

import io.objectbox.annotation.Backlink;
import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.relation.ToMany;

/**
 * @Description: java描述
 * @Author: XGod
 * @CreateDate: 2020/7/18 17:37
 */
@Entity
public class TestBean {
    @Id(assignable = true)
    private long _id;
    String name;
    String msg;
    @Backlink(to = "test")
    ToMany<Child> child;

    @Override
    public String toString() {
        return "TestBean{" +
                "_id=" + _id +
                ", name='" + name + '\'' +
                ", msg='" + msg + '\'' +
                ", child=" + child +
                '}';
    }

    public TestBean() {

    }

    public TestBean(long _id,String name, String msg) {
        this._id = _id;
        this.name=name;
        this.msg = msg;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public long get_id() {
        return _id;
    }

    public void set_id(long _id) {
        this._id = _id;
    }

    public ToMany<Child> getChild() {
        return child;
    }

    public void setChild(ToMany<Child> child) {
        this.child = child;
    }
}
