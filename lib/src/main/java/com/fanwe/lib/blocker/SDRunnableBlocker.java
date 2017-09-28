/*
 * Copyright (C) 2017 zhengjun, fanwe (http://www.fanwe.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.fanwe.lib.blocker;

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
public class SDRunnableBlocker
{
    /**
     * 最大拦截次数
     */
    private int mMaxBlockCount = 0;
    /**
     * 当前已拦截次数
     */
    private int mBlockCount = 0;
    private InternalRunnable mInternalRunnable;
    private Runnable mTargetRunnable;

    /**
     * 设置最大拦截次数
     *
     * @param maxBlockCount
     * @return
     */
    public synchronized SDRunnableBlocker setMaxBlockCount(int maxBlockCount)
    {
        mMaxBlockCount = maxBlockCount;
        return this;
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
     * 延迟执行Runnable
     *
     * @param delay 延迟间隔（毫秒）
     * @return true-立即执行，false-延迟执行
     */
    public synchronized boolean postDelayed(Runnable runnable, long delay)
    {
        mTargetRunnable = runnable;

        if (mMaxBlockCount > 0)
        {
            mBlockCount++;
            if (mBlockCount > mMaxBlockCount)
            {
                // 大于最大拦截次数，立即执行Runnable
                getInternalRunnable().runImmediately();
                return true;
            } else
            {
                getInternalRunnable().runDelay(delay);
                return false;
            }
        } else
        {
            // 没有设置最大拦截次数，立即执行Runnable
            getInternalRunnable().runImmediately();
            return true;
        }
    }

    private InternalRunnable getInternalRunnable()
    {
        if (mInternalRunnable == null)
        {
            mInternalRunnable = new InternalRunnable();
        }
        return mInternalRunnable;
    }

    private class InternalRunnable implements Runnable
    {
        private Handler mHandler = new Handler(Looper.getMainLooper());

        @Override
        public void run()
        {
            synchronized (SDRunnableBlocker.this)
            {
                resetBlockCount();
                if (mTargetRunnable != null)
                {
                    mTargetRunnable.run();
                }
            }
        }

        public final void runImmediately()
        {
            removeDelay();
            run();
        }

        public final void runDelay(long delay)
        {
            removeDelay();
            mHandler.postDelayed(this, delay);
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
        if (mInternalRunnable != null)
        {
            mInternalRunnable.removeDelay();
            mTargetRunnable = null;
        }
    }
}
