package com.sd.lib.blocker;

/**
 * 可以根据对象equals()的次数拦截事件
 */
public class FEqualsBlocker implements EqualsBlocker
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
    public synchronized void setMaxEqualsCount(int count)
    {
        mMaxEqualsCount = count;
    }

    @Override
    public synchronized void setAutoSaveLastLegalObject(boolean save)
    {
        mAutoSaveLastLegalObject = save;
    }

    @Override
    public synchronized void saveLastLegalObject(Object object)
    {
        if (object == null)
            throw new IllegalArgumentException("object is null");

        mLastLegalObject = object;
    }

    @Override
    public synchronized boolean blockEquals(Object object)
    {
        if (mLastLegalObject.equals(object))
        {
            if ((mEqualsCount + 1) > mMaxEqualsCount)
                return true;

            mEqualsCount++;
        } else
        {
            mEqualsCount = 0;
        }

        if (mAutoSaveLastLegalObject)
            saveLastLegalObject(object);

        return false;
    }
}
