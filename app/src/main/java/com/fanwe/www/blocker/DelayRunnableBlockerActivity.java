package com.fanwe.www.blocker;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.fanwe.library.blocker.SDDelayRunnableBlocker;
import com.fanwe.library.looper.ISDLooper;
import com.fanwe.library.looper.impl.SDSimpleLooper;

public class DelayRunnableBlockerActivity extends AppCompatActivity
{
    private static final String TAG = "DelayRunnableBlockerActivity";

    private SDDelayRunnableBlocker mRunnableBlocker = new SDDelayRunnableBlocker();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delay_runnable_blocker);

        mRunnableBlocker.setDelayDuration(1000); //设置Runnable延迟多久执行
        mRunnableBlocker.setMaxBlockCount(9); //设置拦截间隔内最大可以拦截9次，超过9次则立即执行
    }

    private ISDLooper mLooper = new SDSimpleLooper();

    public void onClickStart100(View view)
    {
        mCount = 0;
        mLooper.start(100, new Runnable()
        {
            @Override
            public void run()
            {
                //每隔100毫秒触发一次
                mRunnableBlocker.post(mRunnable);
                Log.i(TAG, "拦截次数：" + mRunnableBlocker.getBlockCount());
            }
        });
    }

    private int mCount;
    /**
     * 模拟耗性能Runnable
     */
    private Runnable mRunnable = new Runnable()
    {
        @Override
        public void run()
        {
            mCount++;
            TextView textView = (TextView) findViewById(R.id.tv_msg);
            textView.setText(String.valueOf(mCount));
        }
    };

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        mLooper.stop();
    }
}
