package com.fanwe.library.blocker;

/**
 * 可以根据时间间隔和对象equals()是否相等来拦截事件
 */
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
        setAutoSaveLastBlockTime(false);
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
        long duration = System.currentTimeMillis() - getLastBlockTime();
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

        saveLastBlockTime();
        setLastObject(object);
        return false;
    }

}
