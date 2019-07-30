package com.sd.lib.blocker;

import android.os.Handler;
import android.os.Looper;

/**
 * 实现带拦截功能的延迟执行某个Runnable<br>
 * 当调用postDelayed方法post一个延迟Runable之后，会有以下3种情况：<br>
 * 1.如果在延迟间隔内没有再次post，则延迟间隔到后执行该Runnable<br>
 * 2.如果在延迟间隔内再次post，并且拦截次数小于最大拦截次数，则取消已经post的延迟Runnable，重新post当前延迟Runnable，拦截次数加一<br>
 * 3.如果在延迟间隔内再次post，并且拦截次数大于最大拦截次数，则立即执行Runnable，重置拦截次数<br>
 * 在界面销毁的时候需要调用onDestroy()
 */
public class FRunnableBlocker
{
    private final Handler mHandler = new Handler(Looper.getMainLooper());

    // 最大拦截次数
    private int mMaxBlockCount = 0;
    // 当前已拦截次数
    private int mBlockCount = 0;
    // 要执行的对象
    private Runnable mRunnable;

    /**
     * 设置最大拦截次数
     *
     * @return
     */
    public synchronized void setMaxBlockCount(int count)
    {
        mMaxBlockCount = count;
    }

    /**
     * 提交要执行的Runnable
     *
     * @param delay 延迟间隔（毫秒）
     * @return true-立即执行，false-延迟执行
     */
    public synchronized boolean postDelayed(Runnable runnable, long delay)
    {
        if (runnable == null)
            throw new IllegalArgumentException("runnable is null");

        mRunnable = runnable;

        if (mMaxBlockCount > 0)
        {
            mBlockCount++;
            if (mBlockCount > mMaxBlockCount)
            {
                // 大于最大拦截次数，立即执行Runnable
                runImmediately();
                return true;
            } else
            {
                runDelayed(delay);
                return false;
            }
        } else
        {
            // 没有设置最大拦截次数，立即执行Runnable
            runImmediately();
            return true;
        }
    }

    private void runImmediately()
    {
        removeDelay();
        mInternalRunnable.run();
    }

    private void runDelayed(long delayMillis)
    {
        removeDelay();
        mHandler.postDelayed(mInternalRunnable, delayMillis);
    }

    private void removeDelay()
    {
        mHandler.removeCallbacks(mInternalRunnable);
    }

    private final Runnable mInternalRunnable = new Runnable()
    {
        @Override
        public void run()
        {
            synchronized (FRunnableBlocker.this)
            {
                mBlockCount = 0;
                mRunnable.run();
            }
        }
    };

    /**
     * 取消延迟任务
     */
    public void cancel()
    {
        removeDelay();
    }
}
