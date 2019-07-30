package com.sd.lib.blocker;

import android.view.View;

/**
 * OnClickListener点击拦截
 */
public class FOnClickBlocker
{
    private static long GLOBAL_BLOCK_DURATION = 500;
    private static final FDurationBlocker GLOBAL_BLOCKER = new FDurationBlocker();

    private final View.OnClickListener mOriginal;
    private final FDurationBlocker mPrivateBlocker;
    private final long mBlockDuration;

    FOnClickBlocker(View.OnClickListener original, long blockDuration)
    {
        mOriginal = original;
        mPrivateBlocker = blockDuration < 0 ? null : new FDurationBlocker();
        mBlockDuration = blockDuration;
    }

    final View.OnClickListener mInternalOnClickListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            if (mPrivateBlocker != null)
            {
                if (mPrivateBlocker.blockDuration(mBlockDuration))
                {
                    //拦截掉
                } else
                {
                    mOriginal.onClick(v);
                }
            } else
            {
                if (GLOBAL_BLOCKER.blockDuration(GLOBAL_BLOCK_DURATION))
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
        GLOBAL_BLOCK_DURATION = blockDuration;
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
     * 当blockDuration小于0：全局拦截当前view（默认500毫秒）
     *
     * @param view
     * @param blockDuration   拦截间隔
     * @param onClickListener
     */
    public static void setOnClickListener(View view, long blockDuration, View.OnClickListener onClickListener)
    {
        if (view == null)
            return;

        if (onClickListener == null)
        {
            view.setOnClickListener(null);
            return;
        }

        final FOnClickBlocker blocker = new FOnClickBlocker(onClickListener, blockDuration);
        view.setOnClickListener(blocker.mInternalOnClickListener);
    }
}
