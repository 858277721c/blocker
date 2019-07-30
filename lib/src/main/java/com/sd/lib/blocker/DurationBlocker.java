package com.sd.lib.blocker;

/**
 * 可以根据时间间隔来拦截事件
 */
public interface DurationBlocker
{
    /**
     * 保存最后一次通过拦截的合法时间点
     */
    void saveLastLegalTime();

    /**
     * 返回最后一次通过拦截的合法时间点
     *
     * @return
     */
    long getLastLegalTime();

    /**
     * 设置是否自动保存最后一次触发拦截的时间，默认自动保存
     *
     * @param save true-自动保存
     */
    void setAutoSaveLastLegalTime(boolean save);

    /**
     * 当前是否处于拦截的间隔之内
     *
     * @param duration 拦截间隔（毫秒）
     * @return true-是
     */
    boolean isInBlockDuration(long duration);

    /**
     * 触发拦截
     *
     * @param duration 拦截间隔（毫秒）
     * @return true-拦截掉
     */
    boolean blockDuration(long duration);
}
