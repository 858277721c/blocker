package com.fanwe.library.blocker;

/**
 * 可以根据时间间隔和对象equals()是否相等来拦截事件
 */
public class SDEqualsDurationBlocker extends SDDurationBlocker
{
    private SDEqualsBlocker mEqualsBlocker = new SDEqualsBlocker();
    /**
     * 拦截equals相同对象的时间间隔
     */
    private long mBlockEqualsDuration;

    public SDEqualsDurationBlocker()
    {
        super();
        setAutoSaveLastBlockTime(false);
    }

    @Override
    public synchronized void setAutoSaveLastBlockTime(boolean autoSaveLastBlockTime)
    {
        // 强制设置为false，不自动保存
        super.setAutoSaveLastBlockTime(false);
    }

    /**
     * 设置拦截equals相同对象的时间间隔
     *
     * @param blockEqualsDuration
     */
    public synchronized void setBlockEqualsDuration(long blockEqualsDuration)
    {
        mBlockEqualsDuration = blockEqualsDuration;
    }

    /**
     * 设置最大可以equals的次数
     *
     * @param maxEqualsCount
     */
    public synchronized void setMaxEqualsCount(int maxEqualsCount)
    {
        mEqualsBlocker.setMaxEqualsCount(maxEqualsCount);
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
     * 触发对象拦截
     *
     * @param object
     * @return true-拦截掉
     */
    public synchronized boolean blockObject(Object object)
    {
        if (mEqualsBlocker.blockObject(object))
        {
            if (isInBlockEqualsDuration())
            {
                return true;
            }
        }

        saveLastBlockTime();
        return false;
    }
}
