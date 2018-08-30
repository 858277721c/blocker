package com.sd.www.blocker;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.sd.lib.blocker.FRunnableBlocker;
import com.sd.lib.looper.impl.FSimpleLooper;

public class RunnableBlockerActivity extends AppCompatActivity
{
    private TextView tv_block_msg, tv_msg;

    private final FRunnableBlocker mBlocker = new FRunnableBlocker();
    private final FSimpleLooper mLooper = new FSimpleLooper();

    private int mRequestCount; //请求执行次数
    private int mRealCount; // 实际执行次数

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_runnable_blocker);
        tv_block_msg = findViewById(R.id.tv_block_msg);
        tv_msg = findViewById(R.id.tv_msg);
    }


    public void onClickStart500(View view)
    {
        // 设置延迟间隔内最大可以拦截3次，超过3次则立即执行
        mBlocker.setMaxBlockCount(3);

        // 模拟每隔500毫秒请求执行一次的场景
        mLooper.setInterval(500);
        mLooper.setLoopRunnable(new Runnable()
        {
            @Override
            public void run()
            {
                mBlocker.postDelayed(mTargetRunnable, 3000); //尝试post一个3000毫秒后执行的Runnable
                mRequestCount++;
                tv_block_msg.setText("请求执行次数：" + mRequestCount);
            }
        });
        mLooper.start();
    }

    /**
     * 模拟耗性能Runnable
     */
    private final Runnable mTargetRunnable = new Runnable()
    {
        @Override
        public void run()
        {
            mRealCount++;
            tv_msg.setText("实际执行次数：" + String.valueOf(mRealCount));
        }
    };

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        mLooper.stop();
        mBlocker.onDestroy();
    }
}
