package com.fanwe.www.blocker;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

        mRunnableBlocker.setMaxBlockCount(3); //设置延迟间隔内最大可以拦截3次，超过3次则立即执行
    }

    private ISDLooper mLooper = new SDSimpleLooper();

    public void onClickStart(View view)
    {
        mRunnableBlocker.post(mRunnable, 1000); //延迟1000毫秒后执行Runnable
    }

    public void onClickStart500(View view)
    {
        mLooper.start(500, new Runnable()
        {
            @Override
            public void run()
            {
                //模拟每隔100毫秒触发一次的场景
                mRunnableBlocker.post(mRunnable, 2000); //延迟2000毫秒后执行Runnable

                TextView textView = (TextView) findViewById(R.id.tv_block_msg);
                textView.setText("拦截次数：" + mRunnableBlocker.getBlockCount());
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
