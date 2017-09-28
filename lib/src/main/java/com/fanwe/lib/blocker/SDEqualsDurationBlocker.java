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
 * 可以根据时间间隔和对象equals()是否相等来拦截事件
 */
public class SDEqualsDurationBlocker implements ISDEqualsBlocker, ISDDurationBlocker
{
    private ISDEqualsBlocker mEqualsBlocker = new SDEqualsBlocker();
    private ISDDurationBlocker mDurationBlocker = new SDDurationBlocker();

    public SDEqualsDurationBlocker()
    {
        super();
        setAutoSaveLastLegalObject(false);
        setAutoSaveLastLegalTime(false);
    }

    @Override
    public void setMaxEqualsCount(int maxEqualsCount)
    {
        mEqualsBlocker.setMaxEqualsCount(maxEqualsCount);
    }

    @Override
    public void setAutoSaveLastLegalObject(boolean autoSaveLastLegalObject)
    {
        mEqualsBlocker.setAutoSaveLastLegalObject(autoSaveLastLegalObject);
    }

    @Override
    public void saveLastLegalObject(Object lastLegalObject)
    {
        mEqualsBlocker.saveLastLegalObject(lastLegalObject);
    }

    @Override
    public boolean blockEquals(Object object)
    {
        return mEqualsBlocker.blockEquals(object);
    }

    @Override
    public void setBlockDuration(long blockDuration)
    {
        mDurationBlocker.setBlockDuration(blockDuration);
    }

    @Override
    public long getBlockDuration()
    {
        return mDurationBlocker.getBlockDuration();
    }

    @Override
    public void saveLastLegalTime()
    {
        mDurationBlocker.saveLastLegalTime();
    }

    @Override
    public long getLastLegalTime()
    {
        return mDurationBlocker.getLastLegalTime();
    }

    @Override
    public void setAutoSaveLastLegalTime(boolean autoSaveLastLegalTime)
    {
        mDurationBlocker.setAutoSaveLastLegalTime(autoSaveLastLegalTime);
    }

    @Override
    public boolean isInBlockDuration(long blockDuration)
    {
        return mDurationBlocker.isInBlockDuration(blockDuration);
    }

    @Override
    public boolean block()
    {
        return mDurationBlocker.block();
    }

    @Override
    public boolean block(long blockDuration)
    {
        return mDurationBlocker.block(blockDuration);
    }
}
