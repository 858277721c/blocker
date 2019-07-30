package com.sd.www.blocker;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.sd.lib.blocker.FRunnableBlocker;

public class RunnableBlockerActivity extends AppCompatActivity
{
    private TextView tv_block_msg, tv_msg;

    private final FRunnableBlocker mBlocker = new FRunnableBlocker();

    private int mRequestCount; //请求执行次数
    private int mRealCount; // 实际执行次数

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_runnable_blocker);
        tv_block_msg = findViewById(R.id.tv_block_msg);
        tv_msg = findViewById(R.id.tv_msg);

        // 设置延迟间隔内最大可以拦截4次，超过4次则立即执行
        mBlocker.setMaxBlockCount(4);
    }

    public void onClickBtn(View view)
    {
        // 尝试提交一个3000毫秒后执行的Runnable
        mBlocker.postDelayed(mTargetRunnable, 3000);
        mRequestCount++;
        tv_block_msg.setText("请求执行次数：" + mRequestCount);
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
        mBlocker.cancel();
    }
}
