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

/**
 * 可以根据时间间隔来拦截事件的类
 */
public class FDurationBlocker implements FIDurationBlocker
{
    /**
     * 拦截间隔
     */
    private long mBlockDuration;
    /**
     * 最后一次通过拦截的合法时间点
     */
    private long mLastLegalTime;
    /**
     * 是否自动保存最后一次通过拦截的合法时间点，默认自动保存
     */
    private boolean mAutoSaveLastLegalTime = true;

    public FDurationBlocker()
    {
        this(DEFAULT_BLOCK_DURATION);
    }

    public FDurationBlocker(long blockDuration)
    {
        super();
        setBlockDuration(blockDuration);
    }

    @Override
    public synchronized void setBlockDuration(long blockDuration)
    {
        if (blockDuration < 0)
        {
            blockDuration = 0;
        }
        mBlockDuration = blockDuration;
    }

    @Override
    public long getBlockDuration()
    {
        return mBlockDuration;
    }

    @Override
    public synchronized void saveLastLegalTime()
    {
        mLastLegalTime = System.currentTimeMillis();
    }

    @Override
    public long getLastLegalTime()
    {
        return mLastLegalTime;
    }

    @Override
    public synchronized void setAutoSaveLastLegalTime(boolean autoSaveLastLegalTime)
    {
        mAutoSaveLastLegalTime = autoSaveLastLegalTime;
    }

    @Override
    public synchronized boolean isInBlockDuration(long blockDuration)
    {
        long duration = System.currentTimeMillis() - mLastLegalTime;
        return duration < blockDuration;
    }

    @Override
    public boolean block()
    {
        return block(mBlockDuration);
    }

    @Override
    public synchronized boolean block(long blockDuration)
    {
        if (isInBlockDuration(blockDuration))
        {
            // 拦截掉
            return true;
        }

        if (mAutoSaveLastLegalTime)
        {
            saveLastLegalTime();
        }
        return false;
    }
}
