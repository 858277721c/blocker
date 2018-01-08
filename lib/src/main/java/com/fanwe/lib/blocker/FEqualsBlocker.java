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
 * 可以根据对象equals()的次数拦截事件
 */
public class FEqualsBlocker implements FIEqualsBlocker
{
    /**
     * 最后一次通过拦截的合法对象
     */
    private Object mLastLegalObject = new Object();
    /**
     * 最大可以equals的次数
     */
    private int mMaxEqualsCount;
    /**
     * 当前equals的次数
     */
    private int mEqualsCount;
    /**
     * 是否自动保存最后一次通过拦截的合法对象，默认自动保存
     */
    private boolean mAutoSaveLastLegalObject = true;

    @Override
    public synchronized void setMaxEqualsCount(int maxEqualsCount)
    {
        mMaxEqualsCount = maxEqualsCount;
    }

    @Override
    public synchronized void setAutoSaveLastLegalObject(boolean autoSaveLastLegalObject)
    {
        mAutoSaveLastLegalObject = autoSaveLastLegalObject;
    }

    @Override
    public synchronized void saveLastLegalObject(Object lastLegalObject)
    {
        mLastLegalObject = lastLegalObject;
    }

    @Override
    public synchronized boolean blockEquals(Object object)
    {
        if (mLastLegalObject.equals(object))
        {
            mEqualsCount++;
            if (mEqualsCount > mMaxEqualsCount)
            {
                mEqualsCount--;
                return true;
            }
        } else
        {
            mEqualsCount = 0;
        }

        if (mAutoSaveLastLegalObject)
        {
            saveLastLegalObject(object);
        }
        return false;
    }
}
