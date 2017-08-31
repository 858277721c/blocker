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
package com.fanwe.library.blocker;

/**
 * 用{@link SDEqualsDurationBlocker}替代
 */
@Deprecated
public class SDObjectBlocker extends SDDurationBlocker
{
    /**
     * 最后一次保存的equals相同的对象
     */
    private Object mLastObject = new Object();
    /**
     * 拦截equals相同对象的时间间隔
     */
    private long mBlockEqualsDuration;
    /**
     * 最大可以equals的次数
     */
    private int mMaxEqualsCount;
    /**
     * 当前equals的次数
     */
    private int mEqualsCount;

    public SDObjectBlocker()
    {
        super();
        setAutoSaveLastLegalTime(false);
    }

    /**
     * 设置拦截equals相同对象的时间间隔
     *
     * @param blockEqualsDuration
     */
    public synchronized void setBlockEqualsDuration(long blockEqualsDuration)
    {
        this.mBlockEqualsDuration = blockEqualsDuration;
    }

    /**
     * 当前是否处于拦截equals相同对象的间隔之内
     *
     * @return
     */
    public synchronized boolean isInBlockEqualsDuration()
    {
        long duration = System.currentTimeMillis() - getLastLegalTime();
        return duration < mBlockEqualsDuration;
    }

    /**
     * 设置最大可以equals的次数
     *
     * @param maxEqualsCount
     */
    public synchronized void setMaxEqualsCount(int maxEqualsCount)
    {
        this.mMaxEqualsCount = maxEqualsCount;
    }

    private void setLastObject(Object lastObject)
    {
        this.mLastObject = lastObject;
    }

    /**
     * 重置equals相同的次数
     */
    public synchronized void resetEqualsCount()
    {
        mEqualsCount = 0;
    }

    /**
     * 触发对象拦截
     *
     * @param object
     * @return true-拦截掉
     */
    public synchronized boolean blockObject(Object object)
    {
        if (mLastObject.equals(object))
        {
            mEqualsCount++;
            if (mEqualsCount > mMaxEqualsCount)
            {
                if (isInBlockEqualsDuration())
                {
                    mEqualsCount--;
                    return true;
                }
            }
        } else
        {
            resetEqualsCount();
        }

        saveLastLegalTime();
        setLastObject(object);
        return false;
    }

}
