/*
 * Copyright (C) 2017 zhengjun, fanwe (http://www.fanwe.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.fanwe.library.blocker;

/**
 * 可以根据对象equals()的次数拦截事件
 */
public interface ISDEqualsBlocker
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
