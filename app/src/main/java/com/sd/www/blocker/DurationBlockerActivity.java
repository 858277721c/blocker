package com.sd.www.blocker;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.sd.lib.blocker.FDurationBlocker;

public class DurationBlockerActivity extends AppCompatActivity
{
    private Button btn_click;
    private int mClickCount;

    private final FDurationBlocker mBlocker = new FDurationBlocker();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_duration_blocker);
        btn_click = findViewById(R.id.btn_click);

        btn_click.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (mBlocker.block(1000))
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
