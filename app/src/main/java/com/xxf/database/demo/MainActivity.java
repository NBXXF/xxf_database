package com.xxf.database.demo;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.xxf.database.xxf.objectbox.MergeFunction;
import com.xxf.database.xxf.objectbox.XXFObjectBoxUtils;
import com.xxf.database.xxf.objectbox.id.IdUtils;

import java.io.File;
import java.util.List;

import io.objectbox.BoxStore;
import io.objectbox.android.AndroidScheduler;
import io.objectbox.reactive.DataObserver;
import io.objectbox.reactive.DataSubscriptionList;

public class MainActivity extends AppCompatActivity {

    TextView msg;
    String name = "张三";
    DataSubscriptionList dataSubscriptionList = new DataSubscriptionList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        msg = findViewById(R.id.text);
        Log.d("==========>", "" + IdUtils.generateId("BTC-SWAP"));
        final BoxStore build = MyObjectBox.builder().directory(new File(getCacheDir(), "xxx2")).build();
        findViewById(R.id.insert)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        TestBean testBean = new TestBean(IdUtils.generateId(name), name, "" + System.currentTimeMillis());
                        build.boxFor(TestBean.class)
                                .put(testBean);


                    }
                });
        findViewById(R.id.update)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        TestBean testBean = new TestBean(IdUtils.generateId(name), name, null);
                        try {
                            XXFObjectBoxUtils.put(build.boxFor(TestBean.class), testBean, new MergeFunction<TestBean>() {
                                @Override
                                public TestBean apply(@NonNull TestBean insert, @Nullable TestBean inserted) throws Exception {
                                    if (inserted != null) {
                                        insert.setMsg(inserted.getMsg() + " haha");
                                    }
                                    return insert;
                                }
                            });
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
        build.boxFor(TestBean.class)
                .query()
                .equal(TestBean_.name, name)
                .build()
                .subscribe(dataSubscriptionList)
                .on(AndroidScheduler.mainThread())
                .observer(new DataObserver<List<TestBean>>() {
                    @Override
                    public void onData(List<TestBean> data) {

                        msg.setText("" + data);
                    }
                });

        long l = IdUtils.generateId(null);
        long l2 = IdUtils.generateId("");
        System.out.println("=====================>id:" + l + "  " + l2);
    }
}
