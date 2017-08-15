package com.fanwe.library.blocker;

import android.os.Handler;
import android.os.Looper;

/**
 * 实现带拦截功能的延迟执行某个Runnable<br>
 * 1.如果在延迟间隔内没有再触发该Runnable，则延迟间隔到后执行Runnable<br>
 * 2.如果在延迟间隔内有再触发该Runnable，如果拦截次数小于设置的最大拦截次数，则拦截掉此次触发动作<br>
 * 3.如果在延迟间隔内有再触发该Runnable，如果拦截次数大于设置的最大拦截次数，则立即执行此次触发动作<br>
 * 在界面销毁的时候需要调用onDestroy()
 */
public class SDDelayRunnableBlocker
{
    /**
     * Runnable延迟多久执行
     */
    private long mDelayDuration = 0;
    /**
     * 最大拦截次数
     */
    private int mMaxBlockCount = 0;
    /**
     * 当前已拦截次数
     */
    private int mBlockCount = 0;
    /**
     * 需要拦截的Runnable
     */
    private Runnable mBlockRunnable;

    /**
     * 设置Runnable延迟多久执行
     *
     * @param delayDuration
     * @return
     */
    public synchronized SDDelayRunnableBlocker setDelayDuration(long delayDuration)
    {
        mDelayDuration = delayDuration;
        return this;
    }

    /**
     * 设置最大拦截次数
     *
     * @param maxBlockCount
     * @return
     */
    public synchronized SDDelayRunnableBlocker setMaxBlockCount(int maxBlockCount)
    {
        mMaxBlockCount = maxBlockCount;
        return this;
    }

    /**
     * 返回Runnable延迟多久执行
     *
     * @return
     */
    public synchronized long getDelayDuration()
    {
        return mDelayDuration;
    }

    /**
     * 返回最大拦截次数
     *
     * @return
     */
    public synchronized int getMaxBlockCount()
    {
        return mMaxBlockCount;
    }

    /**
     * 返回已拦截次数
     *
     * @return
     */
    public synchronized int getBlockCount()
    {
        return mBlockCount;
    }

    /**
     * 执行Runnable
     *
     * @return true-立即执行成功，false-拦截掉
     */
    public synchronized boolean post(Runnable runnable)
    {
        mBlockRunnable = runnable;
        if (mBlockRunnable != null)
        {
            if (mMaxBlockCount > 0)
            {
                mBlockCount++;
                if (mBlockCount > mMaxBlockCount)
                {
                    // 大于最大拦截次数，立即执行Runnable
                    mDelayRunnable.runImmediately();
                    return true;
                } else
                {
                    mDelayRunnable.runDelayOrImmediately(mDelayDuration);
                    return false;
                }
            } else
            {
                // 没有设置最大拦截次数，立即执行Runnable
                mDelayRunnable.runImmediately();
                return true;
            }
        } else
        {
            return false;
        }
    }

    private DelayRunnable mDelayRunnable = new DelayRunnable();

    private class DelayRunnable implements Runnable
    {
        private Handler mHandler = new Handler(Looper.getMainLooper());

        @Override
        public void run()
        {
            synchronized (SDDelayRunnableBlocker.this)
            {
                if (mBlockRunnable != null)
                {
                    resetBlockCount();
                    mBlockRunnable.run();
                }
            }
        }

        public final void runDelay(long delay)
        {
            removeDelay();
            mHandler.postDelayed(this, delay);
        }

        public final void runImmediately()
        {
            removeDelay();
            run();
        }

        public final void runDelayOrImmediately(long delay)
        {
            if (delay > 0)
            {
                runDelay(delay);
            } else
            {
                runImmediately();
            }
        }

        public final void removeDelay()
        {
            mHandler.removeCallbacks(this);
        }
    }

    /**
     * 重置拦截次数
     */
    private void resetBlockCount()
    {
        mBlockCount = 0;
    }

    /**
     * 销毁
     */
    public synchronized void onDestroy()
    {
        mDelayRunnable.removeDelay();
        mBlockRunnable = null;
    }
}
