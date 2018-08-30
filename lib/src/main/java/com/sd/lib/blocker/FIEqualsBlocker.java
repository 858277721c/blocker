package com.sd.lib.blocker;

/**
 * 可以根据对象equals()的次数拦截事件
 */
public interface FIEqualsBlocker
{
    /**
     * 设置最大可以equals的次数
     *
     * @param maxEqualsCount
     */
    void setMaxEqualsCount(int maxEqualsCount);

    /**
     * 设置是否自动保存最后一次通过拦截的合法对象，默认自动保存
     *
     * @param autoSaveLastLegalObject true-自动保存
     */
    void setAutoSaveLastLegalObject(boolean autoSaveLastLegalObject);

    /**
     * 保存最后一次通过拦截的合法对象
     *
     * @param lastLegalObject
     */
    void saveLastLegalObject(Object lastLegalObject);

    /**
     * 触发equals对象拦截
     *
     * @param object
     * @return true-拦截掉
     */
    boolean blockEquals(Object object);
}
