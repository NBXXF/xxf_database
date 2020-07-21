package com.xxf.database.demo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.xxf.database.xxf.objectbox.id.IdUtils;

import java.io.IOException;
import java.util.List;
import java.util.Random;

import io.objectbox.android.AndroidScheduler;
import io.objectbox.android.ObjectBoxDataSource;
import io.objectbox.reactive.DataObserver;
import io.objectbox.reactive.DataSubscriptionList;
import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {


    private Handler handler = new Handler();
    TextView msg;
    String name = "张三";
    DataSubscriptionList dataSubscriptionList = new DataSubscriptionList();
    Observable<List<TestBean>> listObservable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("============>hash", ("ABC".hashCode()) + "");
        Log.d("============>hash1", "" + IdUtils.generateId("ABC"));
        msg = findViewById(R.id.text);
        findViewById(R.id.insert)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // testDao.insert(new TestBean(IdUtils.generateId(name), name, "time_" + System.currentTimeMillis()))
                  /*      testDao.insert(new TestBean(IdUtils.generateId(String.valueOf(new Random().nextInt(10))), name, "time_" + System.currentTimeMillis()))
                                .subscribe(new io.reactivex.functions.Consumer<Long>() {
                                    @Override
                                    public void accept(Long aLong) throws Exception {
                                        Log.d("======>insert", "" + aLong);
                                    }
                                });*/
                    }
                });
      /*  listObservable =
                testDao.observable();
        listObservable
                .subscribe(new Consumer<List<TestBean>>() {
                    @Override
                    public void accept(List<TestBean> testBeans) throws Exception {
                        Log.d("======>update", "" + testBeans);
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                msg.setText("" + testBeans);
                            }
                        });
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.d("======>update", "t:" + throwable);
                    }
                });*/
    }
}
