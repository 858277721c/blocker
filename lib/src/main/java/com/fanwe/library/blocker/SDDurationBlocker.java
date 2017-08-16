package com.fanwe.library.blocker;

/**
 * 可以根据时间间隔来拦截事件的类
 */
public class SDDurationBlocker
{
    /**
     * 默认拦截间隔
     */
    public static final long DEFAULT_BLOCK_DURATION = 500;

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

    public SDDurationBlocker()
    {
        this(DEFAULT_BLOCK_DURATION);
    }

    public SDDurationBlocker(long blockDuration)
    {
        super();
        setBlockDuration(blockDuration);
    }

    /**
     * 获得拦截间隔（毫秒）
     *
     * @return
     */
    public long getBlockDuration()
    {
        return mBlockDuration;
    }

    /**
     * 返回最后一次通过拦截的合法时间点
     *
     * @return
     */
    public long getLastLegalTime()
    {
        return mLastLegalTime;
    }

    /**
     * 保存最后一次通过拦截的合法时间点
     */
    public synchronized void saveLastLegalTime()
    {
        mLastLegalTime = System.currentTimeMillis();
    }

    /**
     * 设置是否自动保存最后一次触发拦截的时间，默认自动保存
     *
     * @param autoSaveLastLegalTime true-自动保存
     */
    public synchronized void setAutoSaveLastLegalTime(boolean autoSaveLastLegalTime)
    {
        mAutoSaveLastLegalTime = autoSaveLastLegalTime;
    }

    /**
     * 设置拦截间隔
     *
     * @param blockDuration 拦截间隔（毫秒）
     */
    public synchronized void setBlockDuration(long blockDuration)
    {
        if (blockDuration < 0)
        {
            blockDuration = 0;
        }
        mBlockDuration = blockDuration;
    }

    /**
     * 当前是否处于拦截的间隔之内
     *
     * @param blockDuration 拦截间隔（毫秒）
     * @return true-是
     */
    public synchronized boolean isInBlockDuration(long blockDuration)
    {
        long duration = System.currentTimeMillis() - mLastLegalTime;
        return duration < mBlockDuration;
    }

    /**
     * 触发拦截
     *
     * @return true-拦截掉
     */
    public boolean block()
    {
        return block(mBlockDuration);
    }

    /**
     * 触发拦截
     *
     * @param blockDuration 拦截间隔（毫秒）
     * @return true-拦截掉
     */
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
