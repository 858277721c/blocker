package com.fanwe.www.blocker;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.fanwe.lib.blocker.SDRunnableBlocker;
import com.fanwe.lib.looper.ISDLooper;
import com.fanwe.lib.looper.impl.SDSimpleLooper;

public class RunnableBlockerActivity extends AppCompatActivity
{
    private TextView tv_block_msg, tv_msg;

    private SDRunnableBlocker mBlocker = new SDRunnableBlocker();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_runnable_blocker);
        tv_block_msg = (TextView) findViewById(R.id.tv_block_msg);
        tv_msg = (TextView) findViewById(R.id.tv_msg);
    }

    private ISDLooper mLooper = new SDSimpleLooper();
    private int mRequestCount; //请求执行次数
    private int mRealCount; // 实际执行次数

    public void onClickStart500(View view)
    {
        mBlocker.setMaxBlockCount(3); //设置延迟间隔内最大可以拦截3次，超过3次则立即执行
        mLooper.start(500, new Runnable() //模拟每隔500毫秒请求执行一次的场景
        {
            @Override
            public void run()
            {
                mBlocker.postDelayed(mTargetRunnable, 3000); //尝试post一个3000毫秒后执行的Runnable

                mRequestCount++;
                tv_block_msg.setText("请求执行次数：" + mRequestCount);
            }
        });
    }

    /**
     * 模拟耗性能Runnable
     */
    private Runnable mTargetRunnable = new Runnable()
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
    }
}
