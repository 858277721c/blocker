package com.sd.lib.blocker;

/**
 * 可以根据时间间隔来拦截事件的类
 */
public class FDurationBlocker implements DurationBlocker
{
    /**
     * 最后一次通过拦截的合法时间点
     */
    private long mLastLegalTime;
    /**
     * 是否自动保存最后一次通过拦截的合法时间点，默认自动保存
     */
    private boolean mAutoSaveLastLegalTime = true;

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
    public synchronized void setAutoSaveLastLegalTime(boolean save)
    {
        mAutoSaveLastLegalTime = save;
    }

    @Override
    public synchronized boolean isInBlockDuration(long blockDuration)
    {
        final long duration = System.currentTimeMillis() - mLastLegalTime;
        return duration < blockDuration;
    }

    @Override
    public synchronized boolean block(long duration)
    {
        if (isInBlockDuration(duration))
        {
            // 拦截掉
            return true;
        }

        if (mAutoSaveLastLegalTime)
            saveLastLegalTime();

        return false;
    }
}
