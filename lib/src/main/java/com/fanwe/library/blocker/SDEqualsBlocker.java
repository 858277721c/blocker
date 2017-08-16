package com.fanwe.library.blocker;

/**
 * 可以根据对象equals()的次数拦截事件
 */
public class SDEqualsBlocker
{
    /**
     * 最后一次保存的对象
     */
    private Object mLastObject = new Object();
    /**
     * 最大可以equals的次数
     */
    private int mMaxEqualsCount;
    /**
     * 当前equals的次数
     */
    private int mEqualsCount;

    /**
     * 设置最大可以equals的次数
     *
     * @param maxEqualsCount
     */
    public synchronized void setMaxEqualsCount(int maxEqualsCount)
    {
        this.mMaxEqualsCount = maxEqualsCount;
    }

    private void saveLastObject(Object lastObject)
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
                mEqualsCount--;
                return true;
            }
        } else
        {
            resetEqualsCount();
        }

        saveLastObject(object);
        return false;
    }

}
