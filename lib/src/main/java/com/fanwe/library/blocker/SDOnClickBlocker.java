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

import android.view.View;

/**
 * OnClickListener点击拦截
 */
public class SDOnClickBlocker
{
    private static final SDDurationBlocker GLOBAL_BLOCKER = new SDDurationBlocker(500);
    private SDDurationBlocker mPrivateBlocker;

    private View.OnClickListener mOriginal;

    SDOnClickBlocker(View.OnClickListener original, long blockDuration)
    {
        this.mOriginal = original;
        if (blockDuration < 0)
        {
            //全局拦截
        } else
        {
            mPrivateBlocker = new SDDurationBlocker();
            mPrivateBlocker.setBlockDuration(blockDuration);
        }
    }

    View.OnClickListener mInternalOnClickListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            if (mPrivateBlocker != null)
            {
                if (mPrivateBlocker.block())
                {
                    //拦截掉
                } else
                {
                    mOriginal.onClick(v);
                }
            } else
            {
                if (GLOBAL_BLOCKER.block())
                {
                    //拦截掉
                } else
                {
                    mOriginal.onClick(v);
                }
            }
        }
    };

    /**
     * 设置全局拦截间隔
     *
     * @param blockDuration
     */
    public static void setGlobalBlockDuration(long blockDuration)
    {
        GLOBAL_BLOCKER.setBlockDuration(blockDuration);
    }

    /**
     * 设置拦截view的点击事件，默认拦截间隔为500毫秒
     *
     * @param view
     * @param onClickListener
     */
    public static void setOnClickListener(View view, View.OnClickListener onClickListener)
    {
        setOnClickListener(view, -1, onClickListener);
    }

    /**
     * 设置拦截view的点击事件<br>
     * 当blockDuration大于0：按设置的时间间隔拦截当前view<br>
     * 当blockDuration等于0：不拦截当前view<br>
     * 当blockDuration小于0：按全局设置的间隔拦截当前view（默认500毫秒）
     *
     * @param view
     * @param blockDuration   拦截间隔
     * @param onClickListener
     */
    public static void setOnClickListener(View view, long blockDuration, View.OnClickListener onClickListener)
    {
        if (view == null)
        {
            return;
        }
        if (onClickListener == null)
        {
            view.setOnClickListener(null);
            return;
        }

        SDOnClickBlocker blocker = new SDOnClickBlocker(onClickListener, blockDuration);
        view.setOnClickListener(blocker.mInternalOnClickListener);
    }

}
