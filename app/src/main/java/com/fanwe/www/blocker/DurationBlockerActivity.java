package com.fanwe.www.blocker;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.fanwe.lib.blocker.FDurationBlocker;

public class DurationBlockerActivity extends AppCompatActivity
{
    private Button btn_click;
    private int mClickCount;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_duration_blocker);
        btn_click = (Button) findViewById(R.id.btn_click);

        final FDurationBlocker blocker = new FDurationBlocker(1000); //设置默认拦截间隔为1000毫秒
        btn_click.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //if (blocker.block(1000)) //动态指定拦截间隔为1000毫秒
                if (blocker.block())
                {
                    //拦截掉
                    return;
                }
                mClickCount++;
                btn_click.setText(String.valueOf(mClickCount)); // 更新点击次数
            }
        });
    }
}
