package com.sd.lib.blocker;

/**
 * 可以根据时间间隔来拦截事件
 */
public interface FIDurationBlocker
{
    /**
     * 默认拦截间隔
     */
    long DEFAULT_BLOCK_DURATION = 500;

    /**
     * 设置默认的拦截间隔
     *
     * @param blockDuration 拦截间隔（毫秒）
     */
    void setBlockDuration(long blockDuration);

    /**
     * 返回默认的拦截间隔（毫秒）
     *
     * @return
     */
    long getBlockDuration();

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
     * @param autoSaveLastLegalTime true-自动保存
     */
    void setAutoSaveLastLegalTime(boolean autoSaveLastLegalTime);

    /**
     * 当前是否处于拦截的间隔之内
     *
     * @param blockDuration 拦截间隔（毫秒）
     * @return true-是
     */
    boolean isInBlockDuration(long blockDuration);

    /**
     * 触发拦截
     *
     * @return true-拦截掉
     */
    boolean block();

    /**
     * 触发拦截
     *
     * @param blockDuration 拦截间隔（毫秒）
     * @return true-拦截掉
     */
    boolean block(long blockDuration);
}
