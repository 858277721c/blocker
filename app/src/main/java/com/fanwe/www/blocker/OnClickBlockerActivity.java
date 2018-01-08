package com.fanwe.www.blocker;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.fanwe.lib.blocker.FOnClickBlocker;

public class OnClickBlockerActivity extends AppCompatActivity
{

    private TextView tv_click_count;
    private int mClickCount;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onclick_blocker);
        tv_click_count = (TextView) findViewById(R.id.tv_click_count);

        //未拦截Button
        findViewById(R.id.btn_normal).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                mClickCount++;
                tv_click_count.setText(String.valueOf(mClickCount)); //更新点击次数
            }
        });

        //全局拦截Button，拦截间隔为1000毫秒
        FOnClickBlocker.setGlobalBlockDuration(1000); //设置全局拦截间隔
        FOnClickBlocker.setOnClickListener(findViewById(R.id.btn_global), new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                mClickCount++;
                tv_click_count.setText(String.valueOf(mClickCount)); //更新点击次数
            }
        });

        //单独拦截Button，拦截间隔为2000毫秒
        FOnClickBlocker.setOnClickListener(findViewById(R.id.btn_private), 2000, new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                mClickCount++;
                tv_click_count.setText(String.valueOf(mClickCount)); //更新点击次数
            }
        });
    }

}
