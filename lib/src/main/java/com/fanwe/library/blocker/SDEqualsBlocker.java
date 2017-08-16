package com.fanwe.library.blocker;

/**
 * 可以根据对象equals()的次数拦截事件
 */
public class SDEqualsBlocker
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

    /**
     * 设置最大可以equals的次数
     *
     * @param maxEqualsCount
     */
    public synchronized void setMaxEqualsCount(int maxEqualsCount)
    {
        this.mMaxEqualsCount = maxEqualsCount;
    }

    /**
     * 设置是否自动保存最后一次通过拦截的合法对象，默认自动保存
     *
     * @param autoSaveLastLegalObject true-自动保存
     */
    public synchronized void setAutoSaveLastLegalObject(boolean autoSaveLastLegalObject)
    {
        mAutoSaveLastLegalObject = autoSaveLastLegalObject;
    }

    /**
     * 触发equals对象拦截
     *
     * @param object
     * @return true-拦截掉
     */
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

    /**
     * 保存最后一次通过拦截的合法对象
     *
     * @param lastLegalObject
     */
    public synchronized void saveLastLegalObject(Object lastLegalObject)
    {
        mLastLegalObject = lastLegalObject;
    }
}
