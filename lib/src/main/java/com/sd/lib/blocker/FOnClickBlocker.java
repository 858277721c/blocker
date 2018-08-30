package com.sd.lib.blocker;

import android.view.View;

/**
 * OnClickListener点击拦截
 */
public class FOnClickBlocker
{
    private static final FDurationBlocker GLOBAL_BLOCKER = new FDurationBlocker(500);
    private FDurationBlocker mPrivateBlocker;

    private View.OnClickListener mOriginal;

    FOnClickBlocker(View.OnClickListener original, long blockDuration)
    {
        this.mOriginal = original;
        if (blockDuration < 0)
        {
            //全局拦截
        } else
        {
            mPrivateBlocker = new FDurationBlocker();
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

        FOnClickBlocker blocker = new FOnClickBlocker(onClickListener, blockDuration);
        view.setOnClickListener(blocker.mInternalOnClickListener);
    }

}
