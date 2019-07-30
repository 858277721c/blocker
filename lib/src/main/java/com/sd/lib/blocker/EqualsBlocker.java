package com.sd.lib.blocker;

/**
 * 可以根据对象equals()的次数拦截事件
 */
public interface EqualsBlocker
{
    /**
     * 设置最大可以equals的次数
     *
     * @param count
     */
    void setMaxEqualsCount(int count);

    /**
     * 设置是否自动保存最后一次通过拦截的合法对象，默认自动保存
     *
     * @param save true-自动保存
     */
    void setAutoSaveLastLegalObject(boolean save);

    /**
     * 保存最后一次通过拦截的合法对象
     *
     * @param object
     */
    void saveLastLegalObject(Object object);

    /**
     * 触发equals对象拦截
     *
     * @param object
     * @return true-拦截掉
     */
    boolean blockEquals(Object object);
}
