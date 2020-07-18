package com.xxf.database.demo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.xxf.database.xxf.objectbox.id.IdUtils;

import java.util.List;

import io.objectbox.android.ObjectBoxDataSource;
import io.reactivex.functions.Consumer;

public class MainActivity extends AppCompatActivity {


    TestDao testDao;
    private Handler handler = new Handler();
    TextView msg;
    String name="张三";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("============>hash", ("ABC".hashCode()) + "");
        Log.d("============>hash1", "" + IdUtils.generateId("ABC"));
        msg = findViewById(R.id.text);
        testDao = new TestDao(this);
        testDao.clear();
        findViewById(R.id.insert)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        testDao.insert(new TestBean(IdUtils.generateId(name),name,"time_" + System.currentTimeMillis()))
                                .subscribe(new io.reactivex.functions.Consumer<Long>() {
                                    @Override
                                    public void accept(Long aLong) throws Exception {
                                        Log.d("======>insert", "" + aLong);
                                    }
                                });
                    }
                });
        testDao.observable(testDao.buildQuery().equal(TestBean_.name,name).build())
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
                });
        testDao.query()
                .subscribe(new Consumer<List<TestBean>>() {
                    @Override
                    public void accept(List<TestBean> testBeans) throws Exception {
                        Log.d("=========>query:",""+testBeans);
                    }
                });
    }
}
