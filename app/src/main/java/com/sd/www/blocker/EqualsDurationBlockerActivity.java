package com.sd.www.blocker;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.sd.lib.blocker.FEqualsDurationBlocker;

public class EqualsDurationBlockerActivity extends AppCompatActivity
{

    private TextView tv_msg;
    private EditText et;
    private Button btn_send_msg;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_equals_duration_blocker);
        tv_msg = (TextView) findViewById(R.id.tv_msg);
        et = (EditText) findViewById(R.id.et);
        btn_send_msg = (Button) findViewById(R.id.btn_send_msg);

        final FEqualsDurationBlocker blocker = new FEqualsDurationBlocker();
        blocker.setMaxEqualsCount(0); //设置允许最大重复的次数0
        btn_send_msg.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String msg = et.getText().toString();
                if (TextUtils.isEmpty(msg))
                {
                    Toast.makeText(EqualsDurationBlockerActivity.this, "请输入消息", 0).show();
                    return;
                }
                if (blocker.blockDuration(2000))
                {
                    //拦截到间隔2000毫秒内的点击
                    Toast.makeText(EqualsDurationBlockerActivity.this, "消息间隔不能小于2秒", 0).show();
                    return;
                }
                if (blocker.blockEquals(msg) && blocker.blockDuration(5000))
                {
                    //拦截到超过最大重复次数，并且间隔5000毫秒内的点击
                    Toast.makeText(EqualsDurationBlockerActivity.this, "重复消息间隔不能小于5秒", 0).show();
                    return;
                }
                blocker.saveLastLegalTime(); //保存通过拦截的合法时间点，下次判断用到
                blocker.saveLastLegalObject(msg); //保存通过拦截的合法对象，下次判断用到

                tv_msg.append("\r\n" + msg);
            }
        });
    }

}
