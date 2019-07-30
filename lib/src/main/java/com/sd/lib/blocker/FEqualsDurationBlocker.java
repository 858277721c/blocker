package com.sd.lib.blocker;

/**
 * 可以根据时间间隔和对象equals()是否相等来拦截事件
 */
public class FEqualsDurationBlocker implements EqualsBlocker, DurationBlocker
{
    private final EqualsBlocker mEqualsBlocker = new FEqualsBlocker();
    private final DurationBlocker mDurationBlocker = new FDurationBlocker();

    public FEqualsDurationBlocker()
    {
        super();
        setAutoSaveLastLegalObject(false);
        setAutoSaveLastLegalTime(false);
    }

    @Override
    public void setMaxEqualsCount(int count)
    {
        mEqualsBlocker.setMaxEqualsCount(count);
    }

    @Override
    public void setAutoSaveLastLegalObject(boolean save)
    {
        mEqualsBlocker.setAutoSaveLastLegalObject(save);
    }

    @Override
    public void saveLastLegalObject(Object object)
    {
        mEqualsBlocker.saveLastLegalObject(object);
    }

    @Override
    public boolean blockEquals(Object object)
    {
        return mEqualsBlocker.blockEquals(object);
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
    public void setAutoSaveLastLegalTime(boolean save)
    {
        mDurationBlocker.setAutoSaveLastLegalTime(save);
    }

    @Override
    public boolean isInBlockDuration(long duration)
    {
        return mDurationBlocker.isInBlockDuration(duration);
    }

    @Override
    public boolean block(long duration)
    {
        return mDurationBlocker.block(duration);
    }
}
